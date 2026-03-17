package com.example.FAS.service;

import com.example.FAS.dto.request.CurrencyRequest;
import com.example.FAS.dto.response.CurrencyResponse;
import com.example.FAS.exception.ResourceNotFoundException;
import com.example.FAS.mapper.CurrencyMapper;
import com.example.FAS.model.Account;
import com.example.FAS.model.Currency;
import com.example.FAS.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;
    public CurrencyResponse addCurrency(Long id,CurrencyRequest currencyRequest){
        Currency currency = (id != null) ? currencyRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Currency not found!"))
                : new Currency();
        currency.setName(currency.getName());
        currency = currencyRepository.save(currency);
        return currencyMapper.toCurrencyResponse(currency);
    }
    public CurrencyResponse getCurrency(Long id){
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Currency not found!"));
        return currencyMapper.toCurrencyResponse(currency);
    }
    public List<CurrencyResponse> getCurrencies(){
        return currencyRepository.findAll()
                .stream()
                .map(currencyMapper::toCurrencyResponse)
                .toList();
    }
}
