package com.example.FAS.controller;

import com.example.FAS.dto.request.AccountApprovalRequest;
import com.example.FAS.dto.request.AccountRequest;
import com.example.FAS.dto.request.AccountTypeRequest;
import com.example.FAS.dto.response.AccountResponse;
import com.example.FAS.dto.response.AccountTypeResponse;
import com.example.FAS.dto.response.MessageResponse;
import com.example.FAS.model.User;
import com.example.FAS.service.AccountService;
import com.example.FAS.service.AccountTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/account")
public class AccountController {
    private final AccountService accountService;
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long accountId){
        return ResponseEntity.ok(accountService.getAccount(accountId));
    }
    @PostMapping
    public ResponseEntity<AccountResponse> editAccount(@RequestBody AccountRequest accountRequest
            , @AuthenticationPrincipal User user){
        return ResponseEntity.ok(accountService.addAccount(null,accountRequest,user));
    }
    @PostMapping("/{accountId}")
    public ResponseEntity<AccountResponse> addAccountType(@PathVariable Long accountId
            , @RequestBody AccountRequest accountRequest, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(accountService.addAccount(null,accountRequest,user));
    }
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccounts(){
        return ResponseEntity.ok(accountService.getAccounts());
    }
    @PostMapping("/{accountId}/approve")
    public ResponseEntity<MessageResponse> approveAccount(@PathVariable Long accountId
            , @RequestBody AccountApprovalRequest accountApprovalRequest, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(accountService.approvalAccountProcess(accountId
                ,accountApprovalRequest,user));
    }
}
