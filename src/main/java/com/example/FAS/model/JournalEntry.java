package com.example.FAS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank
    private String reference;
    private String description;
    @JoinColumn(name = "journal_entry_status_id")
    @ManyToOne
    private EntityStatus status;
    @JoinColumn(name = "created_by")
    @ManyToOne
    private User createdBy;
    @JoinColumn(name = "approval_stage_id")
    @ManyToOne
    private ApprovalStage approvalStage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "journalEntryId",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<JournalLines> journalLines;
    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }
}
