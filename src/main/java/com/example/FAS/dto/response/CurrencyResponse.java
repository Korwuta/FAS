package com.example.FAS.dto.response;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyResponse {
    private Long id;
    private String name;
}
