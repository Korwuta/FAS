package com.example.FAS.controller;

import com.example.FAS.dto.request.AccountTypeRequest;
import com.example.FAS.dto.response.AccountTypeResponse;
import com.example.FAS.model.AccountType;
import com.example.FAS.service.AccountTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/account-type")
public class AccountTypeController {
    private final AccountTypeService accountTypeService;
    @GetMapping("/{accountTypeId}")
    public ResponseEntity<AccountTypeResponse> getAccountType(@PathVariable Long accountTypeId){
        return ResponseEntity.ok(accountTypeService.getAccountType(accountTypeId));
    }
    @PostMapping
    public ResponseEntity<AccountTypeResponse> editAccountType(@RequestBody AccountTypeRequest accountTypeRequest){
        return ResponseEntity.ok(accountTypeService.addAccountType(null,accountTypeRequest));
    }
    @PostMapping("/{accountTypeId}")
    public ResponseEntity<AccountTypeResponse> addAccountType(@PathVariable Long accountTypeId
            , @RequestBody AccountTypeRequest accountTypeRequest){
        return ResponseEntity.ok(accountTypeService.addAccountType(accountTypeId,accountTypeRequest));
    }
    @GetMapping
    public ResponseEntity<List<AccountTypeResponse>> getAccountTypes(){
        return ResponseEntity.ok(accountTypeService.getAccounts());
    }
}
