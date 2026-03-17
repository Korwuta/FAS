package com.example.FAS.dto.response;

import com.example.FAS.model.AccountType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountCategoryResponse {
    private Long id;
    private String name;
    private AccountTypeResponse accountType;
}
