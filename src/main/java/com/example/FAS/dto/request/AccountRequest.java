package com.example.FAS.dto.request;

import com.example.FAS.model.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountRequest {
    @NotBlank(message = "Account code must be provided!")
    private String accountCode;
    @NotBlank(message = "Account name must be provided!")
    private String accountName;
    @NotNull(message = "Account type must be provided")
//    private Long accountTypeId;
    private Long parentAccountId;
    private boolean isPostingAccount;
    private Long accountCategoryId;
    private Long currencyId;
    private boolean allowManualEntry;
    private boolean isSystemAccount;
    private boolean reconciliationEnabled;
    private boolean taxApplicable;
}
