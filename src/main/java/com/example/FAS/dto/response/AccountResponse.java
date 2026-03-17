package com.example.FAS.dto.response;

import com.example.FAS.model.*;
import com.example.FAS.model.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AccountResponse {
    private Long id;
    private String accountCode;
    private String accountName;
    private AccountTypeResponse accountType;
    private AccountResponse parentAccount;
    private boolean isPostingAccount;
    private AccountCategoryResponse accountCategory;
    private CurrencyResponse currency;
    private boolean allowManualEntry;
    private boolean isSystemAccount;
    private boolean reconciliationEnabled;
    private boolean taxApplicable;
    private String statusName;
    private String createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
