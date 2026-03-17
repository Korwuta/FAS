package com.example.FAS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApprovalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JoinColumn(name = "account_id")
    @ManyToOne
    private Account account;
    @JoinColumn(name = "approval_stage_id")
    @ManyToOne
    private ApprovalStage approvalStage;
    @JoinColumn(name = "approved_by_id")
    @ManyToOne
    private User approvedBy;
    private String comment;
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
