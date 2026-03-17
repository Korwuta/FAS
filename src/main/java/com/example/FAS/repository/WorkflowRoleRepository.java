package com.example.FAS.repository;

import com.example.FAS.model.ApprovalStage;
import com.example.FAS.model.UserRole;
import com.example.FAS.model.WorkflowRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowRoleRepository extends JpaRepository<WorkflowRole,Long> {
    boolean existsByUserRoleAndFromStageAndToStage
            (UserRole userRole, ApprovalStage fromStage,ApprovalStage toStage);
}
