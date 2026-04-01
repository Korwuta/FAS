package com.example.FAS.repository;

import com.example.FAS.model.AccountApprovalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalHistoryRepository extends JpaRepository<AccountApprovalHistory,Long> {
}
