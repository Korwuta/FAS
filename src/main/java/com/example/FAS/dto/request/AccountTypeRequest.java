package com.example.FAS.dto.request;

import com.example.FAS.model.FinancialStatement;
import com.example.FAS.model.NormalBalance;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTypeRequest {
    private String name;
    private NormalBalance normalBalance;
    private FinancialStatement financialStatement;
    private String Description;
}
