package com.example.FAS.mapper;

import com.example.FAS.dto.response.AccountResponse;
import com.example.FAS.dto.response.AccountTypeResponse;
import com.example.FAS.dto.response.CurrencyResponse;
import com.example.FAS.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final AccountTypeMapper accountTypeMapper;
    private final AccountCategoryMapper accountCategoryMapper;
    private final CurrencyMapper currencyMapper;
    public AccountResponse toAccountResponse(Account account){
        if (account == null) return null;
        return AccountResponse.builder()
                .id(account.getId())
                .accountCode(account.getAccountCode())
                .accountName(account.getAccountName())
                .accountType(accountTypeMapper.toAccountTypeResponse(account.getAccountType()))
                .accountCategory(accountCategoryMapper.toAccountCategoryResponse(account.getAccountCategory()))
                .isPostingAccount(account.isPostingAccount())
                .parentAccount(toAccountResponse(account.getParentAccount()))
                .isSystemAccount(account.isSystemAccount())
                .allowManualEntry(account.isAllowManualEntry())
                .currency(currencyMapper.toCurrencyResponse(account.getCurrency()))
                .createdAt(account.getCreatedAt())
                .createdById(account.getCreatedBy().getId())
                .reconciliationEnabled(account.isReconciliationEnabled())
                .taxApplicable(account.isTaxApplicable())
                .statusName(account.getStatus().getName())
                .build();
    }
}
