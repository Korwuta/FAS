package com.example.FAS.repository;

import com.example.FAS.model.Branch;
import com.example.FAS.model.User;
import com.example.FAS.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    boolean existsByNameAndBranch(String name,Branch branch);
    Optional<UserRole> findByNameAndBranch(String name,Branch branch);
}
