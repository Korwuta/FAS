package com.example.FAS.controller;


import com.example.FAS.dto.request.CurrencyRequest;
import com.example.FAS.dto.response.CurrencyResponse;
import com.example.FAS.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/currency")
public class CurrencyController {
    private final CurrencyService currencyService;
    @GetMapping("/{currencyId}")
    public ResponseEntity<CurrencyResponse> getCurrency(@PathVariable Long currencyId){
        return ResponseEntity.ok(currencyService.getCurrency(currencyId));
    }
    @PostMapping
    public ResponseEntity<CurrencyResponse> editCurrency(@RequestBody CurrencyRequest currencyRequest){
        return ResponseEntity.ok(currencyService.addCurrency(null,currencyRequest));
    }
    @PostMapping("/{currencyId}")
    public ResponseEntity<CurrencyResponse> addCurrency(@PathVariable Long accountTypeId
            , @RequestBody CurrencyRequest currencyRequest){
        return ResponseEntity.ok(currencyService.addCurrency(accountTypeId,currencyRequest));
    }
    @GetMapping
    public ResponseEntity<List<CurrencyResponse>> getCurrencies(){
        return ResponseEntity.ok(currencyService.getCurrencies());
    }
}
