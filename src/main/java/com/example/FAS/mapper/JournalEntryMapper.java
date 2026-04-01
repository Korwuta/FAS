package com.example.FAS.mapper;

import com.example.FAS.dto.response.JournalEntryResponse;
import com.example.FAS.model.JournalEntry;
import com.example.FAS.model.JournalLines;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JournalEntryMapper {
    private final JournalLinesMapper journalLinesMapper;
    public JournalEntryResponse toJournalEntryResponse(JournalEntry journalEntry){
        return JournalEntryResponse.builder()
                .id(journalEntry.getId())
                .reference(journalEntry.getReference())
                .description(journalEntry.getDescription())
                .journalLines(journalEntry
                        .getJournalLines()
                        .stream()
                        .map(journalLinesMapper::toJournalLinesResponse)
                        .toList())
                .build();
    }
}
