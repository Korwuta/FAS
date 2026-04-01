package com.example.FAS.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JournalEntryRequest {
    private String reference;
    private String description;
    private List<JournalLinesRequest> journalLines;
}
