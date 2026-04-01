package com.example.FAS.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class JournalLinesRequest {
    private Long journalEntryId;
    private Long accountId;
    private BigDecimal creditAmount;
    private BigDecimal debitAmount;
}
