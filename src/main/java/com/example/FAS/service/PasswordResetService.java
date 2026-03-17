package com.example.FAS.service;

import com.example.FAS.dto.request.ForgotPasswordRequest;
import com.example.FAS.dto.request.ResetPasswordRequest;
import com.example.FAS.dto.response.MessageResponse;
import com.example.FAS.exception.ResourceNotFoundException;
import com.example.FAS.exception.UnAuthenticatedException;
import com.example.FAS.model.PasswordResetToken;
import com.example.FAS.model.User;
import com.example.FAS.repository.PasswordResetTokenRepository;
import com.example.FAS.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional
    public MessageResponse requestPasswordReset(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        String token = UUID.randomUUID().toString();;

        passwordResetTokenRepository.deleteByUser(user);

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusHours(1))
                .build();

        passwordResetTokenRepository.save(resetToken);
        emailService.sendPasswordResetEmail(user.getEmail()
                , String.format(user.getEmployee().getFullName())
                , token);
        return new MessageResponse("Password reset instructions have been sent to your email.");
    }

    @Transactional
    public MessageResponse resetPassword(ResetPasswordRequest request) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new UnAuthenticatedException("Invalid password reset token"));

        if (resetToken.isExpired()) {
            throw new UnAuthenticatedException("Password reset token is expired");
        }

        User user = resetToken.getUser();
        user.setHashedPassword(passwordEncoder.encode(request.getNewPassword()));

        if (!user.isEnabled()) {
            user.setEnabled(true);
            log.info("Enabling user account for: {}", user.getEmail());
        }

        userRepository.save(user);

        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);

        return new MessageResponse("Password has been set successfully. You can now log in.");
    }
}