package com.example.FAS.service;

import com.example.FAS.dto.request.AccountApprovalRequest;
import com.example.FAS.dto.request.AccountRequest;
import com.example.FAS.dto.response.AccountResponse;
import com.example.FAS.dto.response.MessageResponse;
import com.example.FAS.exception.DuplicateResourceException;
import com.example.FAS.exception.ResourceNotFoundException;
import com.example.FAS.mapper.AccountMapper;
import com.example.FAS.model.*;
import com.example.FAS.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountCategoryRepository accountCategoryRepository;
    private final CurrencyRepository currencyRepository;
    private final EntityStatusRepository entityStatusRepository;
    private final ApprovalStageRepository approvalStageRepository;
    private final WorkflowRoleRepository workflowRoleRepository;
    private final ApprovalHistoryRepository approvalHistoryRepository;
    private final AccountMapper accountMapper;
    public AccountResponse addAccount(Long id,AccountRequest accountRequest, User user){
        Account account = (id != null) ? accountRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Account not found!"))
                : new Account();
        if(accountRepository.existsByAccountCodeAndIdNot(accountRequest.getAccountCode(),id)){
            throw new DuplicateResourceException("Account already exists");
        }
//        AccountType accountType = accountTypeRepository
//                .findById(accountRequest.getAccountTypeId())
//                .orElseThrow(()-> new ResourceNotFoundException("Account type not found!"));
        AccountCategory accountCategory = accountCategoryRepository
                .findById(accountRequest.getAccountCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Account category not found!"));
        Currency currency = currencyRepository
                .findById(accountRequest.getCurrencyId())
                .orElseThrow(()->new ResourceNotFoundException("Currency not found!"));
        Account parentAccount = accountRequest.getParentAccountId() != null ? accountRepository
                .findById(accountRequest.getParentAccountId())
                .orElseThrow(()-> new ResourceNotFoundException("Parent account not found!"))
                : null;
        account.setAccountCode(accountRequest.getAccountCode());
        account.setAccountName(accountRequest.getAccountName());
        account.setAccountCategory(accountCategory);
        account.setAccountType(accountCategory.getAccountType());
        account.setParentAccount(parentAccount);
        account.setCurrency(currency);
        account.setPostingAccount(accountRequest.isPostingAccount());
        account.setSystemAccount(accountRequest.isSystemAccount());
        account.setAllowManualEntry(accountRequest.isAllowManualEntry());
        account.setStatus(entityStatusRepository.findByName("IN_PROGRESS")
                        .orElseThrow(()->new RuntimeException("Status does not exist")));
        account.setApprovalStage(approvalStageRepository.findByName("ACCOUNT_CREATION_INITIATED")
                        .orElseThrow(()->new RuntimeException("Approval Stage not found!")));
        account = accountRepository.save(account);
        return accountMapper.toAccountResponse(account);
    }
    public AccountResponse getAccount(Long accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()-> new ResourceNotFoundException("Account not found!"));
        return accountMapper.toAccountResponse(account);
    }
    public MessageResponse approvalAccountProcess(Long accountId, AccountApprovalRequest accountApprovalRequest, User user){
        UserRole userRole = user.getUserRole();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()-> new ResourceNotFoundException("Account not found!"));
        ApprovalStage fromStage = account.getApprovalStage();
        ApprovalStage toStage = approvalStageRepository
                .findById(accountApprovalRequest.getApprovalStageId())
                .orElseThrow(()->new ResourceNotFoundException("Approval stage not found!"));
        if(!workflowRoleRepository
                .existsByUserRoleAndFromStageAndToStage(userRole,fromStage,toStage)){
            throw new RuntimeException("You don't have permission for this action");
        }
        account.setApprovalStage(toStage);
        if (toStage.getName().equalsIgnoreCase("ACCOUNT_CREATED_APPROVED_FINANCIAL_ADVISOR")){
            account.setStatus(entityStatusRepository
                    .findByName("ACTIVE")
                    .orElseThrow(()-> new ResourceNotFoundException("Status doesn't exist")));
        }
        accountRepository.save(account);
        ApprovalHistory approvalHistory = ApprovalHistory.builder()
                .account(account)
                .approvalStage(toStage)
                .approvedBy(user)
                .comment("")
                .build();
        approvalHistoryRepository.save(approvalHistory);
        return new MessageResponse("Approved");
    }
    public List<AccountResponse> getAccounts(){
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toAccountResponse)
                .toList();
    }
}
