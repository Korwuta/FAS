package com.example.FAS.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCategoryRequest {
    private String name;
    private Long accountTypeId;
}
