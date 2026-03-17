package com.example.FAS.service;

import com.example.FAS.dto.request.LoginRequest;
import com.example.FAS.dto.request.RefreshTokenRequest;
import com.example.FAS.dto.request.RegisterRequest;
import com.example.FAS.dto.response.JwtResponse;
import com.example.FAS.dto.response.MessageResponse;
import com.example.FAS.exception.DuplicateResourceException;
import com.example.FAS.exception.ResourceNotFoundException;
import com.example.FAS.exception.TokenRefreshException;
import com.example.FAS.exception.UnAuthenticatedException;
import com.example.FAS.model.*;
import com.example.FAS.repository.*;
import com.example.FAS.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserRoleRepository userRoleRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final BranchRepository branchRepository;
    private final EmailService emailService;

    private UserRole getUserRole(Long id){
        return userRoleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User role not found"));
    }


    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        User userDetails = (User) authentication.getPrincipal();

        if (!userDetails.isEnabled()) {
            throw new UnAuthenticatedException("User account is disabled");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtTokenProvider.createAccessToken(authentication);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .firstName(userDetails.getEmployee().getFirstName())
                .lastName(userDetails.getEmployee().getLastName())
                .build();
    }


    @Transactional
    public JwtResponse refreshToken(RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtTokenProvider.createAccessTokenFromUsername(user.getUsername());

                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(requestRefreshToken)
                            .tokenType("Bearer")
                            .id(user.getId())
                            .email(user.getUsername())
                            .firstName(user.getEmployee().getFirstName())
                            .lastName(user.getEmployee().getLastName())
                            .build();
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not found in database!"));
    }

    @Transactional
    public MessageResponse register(RegisterRequest registerRequest, MultipartFile image) {
        try {
            if (employeeRepository.existsByCorporateMail(registerRequest.getCorporateMail())) {
                throw new DuplicateResourceException("Email is already in use!");
            }
            UserRole userRole = userRoleRepository
                    .findById(registerRequest.getRoleId())
                    .orElseThrow(()->new ResourceNotFoundException("Role not found!"));
            Branch branch = branchRepository
                    .findById(registerRequest.getBranchId())
                    .orElseThrow(()->new ResourceNotFoundException("Branch not found!"));
            String temporaryPassword = UUID.randomUUID().toString();
            Employee employee = Employee.builder()
                    .firstName(registerRequest.getFirstName())
                    .lastName(registerRequest.getLastName())
                    .phoneNumber(registerRequest.getPhone())
                    .corporateMail(registerRequest.getCorporateMail())
                    .build();
            User user = User.builder()
                    .email(registerRequest.getCorporateMail())
                    .hashedPassword(passwordEncoder.encode(temporaryPassword))
                    .userRole(userRole)
                    .branch(branch)
                    .employee(employee)
                    .enabled(false)
                    .build();
            user = userRepository.saveAndFlush(user);
            String resetToken = UUID.randomUUID().toString();
            passwordResetTokenRepository.deleteByUser(user);
            PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                    .token(resetToken)
                    .user(user)
                    .expiryDate(LocalDateTime.now().plusDays(7))
                    .build();
            passwordResetTokenRepository.save(passwordResetToken);
            emailService.sendAccountSetupMail(
                    user.getEmail()
                    , String.format(user.getEmployee().getFullName())
                    , resetToken);
            return new MessageResponse("User registered successfully! An email has been sent to set up their password.");
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Registration failed due to data constraints: " + e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }

    @Transactional
    public MessageResponse logout(RefreshTokenRequest request) {
        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(token -> {
                    refreshTokenService.revokeAllUserTokens(token.getUser());
                    return new MessageResponse("Logout successful!");
                })
                .orElseThrow(() -> new TokenRefreshException(request.getRefreshToken(),
                        "Refresh token is not found in database!"));
    }
}