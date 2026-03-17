package com.example.FAS.mapper;

import com.example.FAS.dto.response.AccountCategoryResponse;
import com.example.FAS.model.AccountCategory;
import com.example.FAS.model.AccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountCategoryMapper {
    private final AccountTypeMapper accountTypeMapper;
    public AccountCategoryResponse toAccountCategoryResponse(AccountCategory accountCategory){
        if (accountCategory == null) return null;
        return AccountCategoryResponse.builder()
                .accountType(accountTypeMapper.toAccountTypeResponse(accountCategory.getAccountType()))
                .id(accountCategory.getId())
                .name(accountCategory.getName())
                .build();
    }
}
