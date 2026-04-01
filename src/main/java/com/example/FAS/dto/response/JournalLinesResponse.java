package com.example.FAS.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JournalLinesResponse {
    private Long id;
    private AccountResponse accountResponse;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
}
