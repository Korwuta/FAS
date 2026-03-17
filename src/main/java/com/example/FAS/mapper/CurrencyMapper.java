package com.example.FAS.mapper;

import com.example.FAS.dto.response.CurrencyResponse;
import com.example.FAS.model.Currency;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper {
    public CurrencyResponse toCurrencyResponse(Currency currency){
        if (currency == null) return null;
        return CurrencyResponse.builder()
                .id(currency.getId())
                .name(currency.getName())
                .build();
    }
}
