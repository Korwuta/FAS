package com.example.FAS.repository;

import com.example.FAS.model.ApprovalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalHistoryRepository extends JpaRepository<ApprovalHistory,Long> {
}
