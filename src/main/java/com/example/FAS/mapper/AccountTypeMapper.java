package com.example.FAS.mapper;

import com.example.FAS.dto.response.AccountTypeResponse;
import com.example.FAS.model.AccountType;
import org.springframework.stereotype.Component;

@Component
public class AccountTypeMapper {
    public AccountTypeResponse toAccountTypeResponse(AccountType accountType){
        if (accountType == null) return null;
        return AccountTypeResponse.builder()
                .id(accountType.getId())
                .name(accountType.getName())
                .build();
    }
}
