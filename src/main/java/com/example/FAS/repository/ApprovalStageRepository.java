package com.example.FAS.repository;

import com.example.FAS.model.ApprovalStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalStageRepository extends JpaRepository<ApprovalStage,Long> {
    Optional<ApprovalStage> findByName(String name);
}
