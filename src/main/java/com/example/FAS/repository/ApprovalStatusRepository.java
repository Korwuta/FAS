package com.example.FAS.repository;

import com.example.FAS.model.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalStatusRepository extends JpaRepository<EntityStatus,Long> {
}
