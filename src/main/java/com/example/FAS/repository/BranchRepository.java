package com.example.FAS.repository;

import com.example.FAS.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch,Long> {
    boolean existsByName(String name);
    Optional<Branch> findByName(String name);
}
