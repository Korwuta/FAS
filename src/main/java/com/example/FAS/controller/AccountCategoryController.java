package com.example.FAS.controller;

import com.example.FAS.dto.request.AccountCategoryRequest;
import com.example.FAS.dto.response.AccountCategoryResponse;
import com.example.FAS.service.AccountCategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/account-category")
public class AccountCategoryController {
    private final AccountCategoryService accountCategoryService;
    @GetMapping("/{accountCategoryId}")
    public ResponseEntity<AccountCategoryResponse> getAccountCategory(@PathVariable Long accountCategoryId){
        return ResponseEntity.ok(accountCategoryService.getAccountCategory(accountCategoryId));
    }
    @PostMapping
    public ResponseEntity<AccountCategoryResponse> editAccountCategory(@RequestBody AccountCategoryRequest accountCategoryRequest){
        return ResponseEntity.ok(accountCategoryService.addAccountCategory(null,accountCategoryRequest));
    }
    @PostMapping("/{accountCategoryId}")
    public ResponseEntity<AccountCategoryResponse> addAccountCategory(@PathVariable Long accountCategoryId
            , @RequestBody AccountCategoryRequest accountCategoryRequest){
        return ResponseEntity.ok(accountCategoryService.addAccountCategory(accountCategoryId,accountCategoryRequest));
    }
    @GetMapping("/account-type/{accountTypeId}")
    public ResponseEntity<List<AccountCategoryResponse>> getAccountCategories(@PathVariable Long accountTypeId){
        return ResponseEntity.ok(accountCategoryService.getAccountCategories(accountTypeId));
    }
}
