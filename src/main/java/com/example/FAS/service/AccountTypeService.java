package com.example.FAS.service;

import com.example.FAS.dto.request.AccountTypeRequest;
import com.example.FAS.dto.response.AccountTypeResponse;
import com.example.FAS.exception.ResourceNotFoundException;
import com.example.FAS.mapper.AccountTypeMapper;
import com.example.FAS.model.AccountType;
import com.example.FAS.model.Currency;
import com.example.FAS.repository.AccountTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTypeService {
    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;
    public AccountTypeResponse addAccountType(Long id, AccountTypeRequest accountTypeRequest){
        AccountType accountType = (id != null) ? accountTypeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account type not found!"))
                : new AccountType();
        accountType.setName(accountType.getName());
        accountType.setDescription(accountType.getDescription());
        accountType.setNormalBalance(accountType.getNormalBalance());
        accountType.setFinancialStatement(accountType.getFinancialStatement());
        accountType = accountTypeRepository.save(accountType);
        return accountTypeMapper.toAccountTypeResponse(accountType);
    }
    public AccountTypeResponse getAccountType(Long id){
        AccountType accountType = accountTypeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account type not found!"));
        return accountTypeMapper.toAccountTypeResponse(accountType);
    }
    public List<AccountTypeResponse> getAccounts(){
        return accountTypeRepository.findAll().stream()
                .map(accountTypeMapper::toAccountTypeResponse)
                .toList();
    }
}
