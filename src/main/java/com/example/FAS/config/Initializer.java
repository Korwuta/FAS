package com.example.FAS.config;

import com.example.FAS.model.Branch;
import com.example.FAS.model.Employee;
import com.example.FAS.model.User;
import com.example.FAS.model.UserRole;
import com.example.FAS.repository.BranchRepository;
import com.example.FAS.repository.UserRepository;
import com.example.FAS.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {
    private final BranchRepository branchRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Override
    public void run(String... args) throws Exception {
        createSystemAdminAccount();
    }
    public void createSystemAdminAccount(){
        Branch branch = Branch.builder()
                .name("Headquarters")
                .build();
        branchRepository.save(branch);
        UserRole role = UserRole.builder()
                .name("System Developer")
                .enabled(true)
                .branch(branch)
                .build();
        userRoleRepository.save(role);
        Employee employee = Employee.builder()
                .firstName("FAS")
                .lastName("Corp")
                .phoneNumber("+233551382498")
                .corporateMail("korwutacollins@gmail.com")
                .build();
        User user = User.builder()
                .email("korwutacollins@gmail.com")
                .hashedPassword(passwordEncoder.encode("Mike729@123"))
                .userRole(role)
                .branch(branch)
                .employee(employee)
                .enabled(true)
                .build();
        userRepository.save(user);
    }
}
