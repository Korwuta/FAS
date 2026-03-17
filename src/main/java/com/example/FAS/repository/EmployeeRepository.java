package com.example.FAS.repository;

import com.example.FAS.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
    boolean existsByCorporateMail(String corporateMail);
}
