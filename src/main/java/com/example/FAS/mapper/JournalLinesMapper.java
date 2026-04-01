package com.example.FAS.mapper;

import com.example.FAS.dto.response.AccountResponse;
import com.example.FAS.dto.response.JournalLinesResponse;
import com.example.FAS.model.JournalLines;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JournalLinesMapper {
    private final AccountMapper accountMapper;
    public JournalLinesResponse toJournalLinesResponse(JournalLines journalLines){
        return JournalLinesResponse.builder()
                .id(journalLines.getId())
                .accountResponse(accountMapper.toAccountResponse(journalLines.getAccount()))
                .creditAmount(journalLines.getCreditAmount())
                .debitAmount(journalLines.getDebitAmount())
                .build();
    }
}
