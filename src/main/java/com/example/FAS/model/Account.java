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
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String accountCode;
    private String accountName;
    @JoinColumn(name = "account_type_id")
    @ManyToOne
    private AccountType accountType;
    @ManyToOne
    @JoinColumn(name = "parent_account_id")
    private Account parentAccount;
    @Builder.Default
    private boolean isPostingAccount = false;
    @JoinColumn(name = "account_category_id")
    @ManyToOne
    private AccountCategory accountCategory;
    @JoinColumn(name = "currency_id")
    @ManyToOne
    private Currency currency;
    private boolean allowManualEntry;
    private boolean isSystemAccount;
    private boolean reconciliationEnabled;
    private boolean taxApplicable;
    //private String defaultTaxId;
    @JoinColumn(name = "account_status_id")
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
    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }
}
