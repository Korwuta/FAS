package com.example.FAS.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JournalEntryApprovalHistory {
    private Long id;
    @JoinColumn(name = "journal_entry_id")
    @ManyToOne
    private JournalEntry journalEntry;
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
