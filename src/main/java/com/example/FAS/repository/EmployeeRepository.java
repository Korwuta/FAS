package com.example.FAS.repository;

import com.example.FAS.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
    boolean existsByCorporateMail(String corporateMail);
    Optional<Employee> findByCorporateMail(String corporateMail);
}
