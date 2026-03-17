package com.example.FAS.service;

import com.example.FAS.dto.request.AccountCategoryRequest;
import com.example.FAS.dto.response.AccountCategoryResponse;
import com.example.FAS.exception.ResourceNotFoundException;
import com.example.FAS.mapper.AccountCategoryMapper;
import com.example.FAS.model.AccountCategory;
import com.example.FAS.model.AccountType;
import com.example.FAS.repository.AccountCategoryRepository;
import com.example.FAS.repository.AccountTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountCategoryService {
    private final AccountTypeRepository accountTypeRepository;
    private final AccountCategoryRepository accountCategoryRepository;
    private final AccountCategoryMapper accountCategoryMapper;
    public AccountCategoryResponse addAccountCategory(Long id, AccountCategoryRequest accountCategoryRequest){
        AccountCategory accountCategory = (id != null) ? accountCategoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account category not found!"))
                : new AccountCategory();
        accountCategory.setName(accountCategoryRequest.getName());
        accountCategory.setAccountType(
                accountTypeRepository.findById(accountCategoryRequest.getAccountTypeId())
                        .orElseThrow(()-> new ResourceNotFoundException("Account type not found!")));
        accountCategory = accountCategoryRepository.save(accountCategory);
        return accountCategoryMapper.toAccountCategoryResponse(accountCategory);
    }
    public AccountCategoryResponse getAccountCategory(Long id){
        AccountCategory accountCategory = accountCategoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account category not found!"));
        return accountCategoryMapper.toAccountCategoryResponse(accountCategory);
    }
    public List<AccountCategoryResponse> getAccountCategories(Long accountTypeId){
        AccountType accountType = accountTypeRepository
                .findById(accountTypeId)
                .orElseThrow(()-> new ResourceNotFoundException("Account type not found!"));
        return accountCategoryRepository.findAllByAccountType(accountType)
                .stream()
                .map(accountCategoryMapper::toAccountCategoryResponse)
                .toList();
    }
}
