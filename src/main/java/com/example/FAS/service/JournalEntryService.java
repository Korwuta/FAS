package com.example.FAS.service;

import com.example.FAS.dto.request.ApprovalRequest;
import com.example.FAS.dto.request.JournalEntryRequest;
import com.example.FAS.dto.request.JournalLinesRequest;
import com.example.FAS.dto.response.JournalEntryResponse;
import com.example.FAS.dto.response.MessageResponse;
import com.example.FAS.exception.DuplicateResourceException;
import com.example.FAS.exception.ResourceNotFoundException;
import com.example.FAS.mapper.JournalEntryMapper;
import com.example.FAS.model.*;
import com.example.FAS.repository.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class JournalEntryService {
    private final JournalEntryRepository journalEntryRepository;
    private final JournalLinesRepository journalLinesRepository;
    private final JournalEntryMapper journalEntryMapper;
    private final AccountRepository accountRepository;
    private final EntityStatusRepository entityStatusRepository;
    private final ApprovalStageRepository approvalStageRepository;
    private final WorkflowRoleRepository workflowRoleRepository;
    private final JournalEntryApprovalHistoryRepository journalEntryApprovalHistoryRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    @Transactional
    public JournalEntryResponse addJournalEntry(Long id, JournalEntryRequest journalEntryRequest){
        JournalEntry journalEntry = (id != null) ? journalEntryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Journal entry not found!"))
                : new JournalEntry();
        if(journalEntryRepository.existsByReferenceAndIdNot(journalEntryRequest.getReference(),id)){
            throw new DuplicateResourceException("Journal entry already exists");
        }
        journalEntry.setReference(journalEntry.getReference());
        journalEntry.setDescription(journalEntry.getDescription());
        journalEntry.getJournalLines().clear();
        BigDecimal debitAmount = BigDecimal.ZERO;
        BigDecimal creditAmount = BigDecimal.ZERO;
        for (JournalLinesRequest journalLinesRequest: journalEntryRequest.getJournalLines()){
            Account account = accountRepository
                    .findById(journalLinesRequest.getAccountId())
                    .orElseThrow(()-> new ResourceNotFoundException("Account not found!"));
            journalEntry.getJournalLines().add(JournalLines.builder()
                            .account(account)
                            .creditAmount(journalLinesRequest.getCreditAmount())
                            .debitAmount(journalLinesRequest.getDebitAmount())
                            .journalEntry(journalEntry)
                    .build());
            debitAmount = debitAmount.add(journalLinesRequest.getDebitAmount());
            creditAmount = creditAmount.add(journalLinesRequest.getCreditAmount());
        }
        if (!debitAmount.equals(creditAmount)){
            throw new RuntimeException("Debit and credit must be equal!");
        }
        journalEntry.setStatus(entityStatusRepository.findByName("IN_PROGRESS")
                .orElseThrow(()->new RuntimeException("Status does not exist")));
        journalEntry.setApprovalStage(approvalStageRepository.findByName("JOURNAL_ENTRY_CREATION_INITIATED")
                .orElseThrow(()->new RuntimeException("Approval Stage not found!")));
        journalEntryRepository.save(journalEntry);
        return journalEntryMapper.toJournalEntryResponse(journalEntry);
    }
    public MessageResponse approvalProcess(Long journalEntryId, ApprovalRequest approvalRequest, User user){
        UserRole userRole = user.getUserRole();
        JournalEntry journalEntry = journalEntryRepository.findById(journalEntryId)
                .orElseThrow(()-> new ResourceNotFoundException("Account not found!"));
        ApprovalStage fromStage = journalEntry.getApprovalStage();
        ApprovalStage toStage = approvalStageRepository
                .findById(approvalRequest.getApprovalStageId())
                .orElseThrow(()->new ResourceNotFoundException("Approval stage not found!"));
        if(!workflowRoleRepository
                .existsByUserRoleAndFromStageAndToStage(userRole,fromStage,toStage)){
            throw new RuntimeException("You don't have permission for this action");
        }
        journalEntry.setApprovalStage(toStage);
        if (toStage.getName().equalsIgnoreCase("JOURNAL_ENTRY_APPROVED_FINANCIAL_ADVISOR")){
            journalEntry.setStatus(entityStatusRepository
                    .findByName("ACTIVE")
                    .orElseThrow(()-> new ResourceNotFoundException("Status doesn't exist")));
        }
        journalEntry = journalEntryRepository.save(journalEntry);
        JournalEntryApprovalHistory journalEntryApprovalHistory = JournalEntryApprovalHistory.builder()
                .journalEntry(journalEntry)
                .approvalStage(toStage)
                .approvedBy(user)
                .comment("")
                .build();
        journalEntryApprovalHistoryRepository.save(journalEntryApprovalHistory);
        if (journalEntry.getStatus().getName().equalsIgnoreCase("ACTIVE")){
            createLedgerEntry(journalEntry);
        }
        return new MessageResponse("Approved");
    }
    @Transactional
    public void createLedgerEntry(@NotNull JournalEntry journalEntry){
        if(!journalEntry.getStatus().getName()
                .equalsIgnoreCase("ACTIVE")){
            throw new RuntimeException("Journal has not been approved by financial advisor");
        }
        for (JournalLines journalLines: journalEntry.getJournalLines()){
            LedgerEntry ledgerEntry = LedgerEntry.builder()
                    .journalEntry(journalEntry)
                    .account(journalLines.getAccount())
                    .debitAmount(journalLines.getDebitAmount())
                    .creditAmount(journalLines.getCreditAmount())
                    .balanceAfter(journalLines.getDebitAmount().subtract(journalLines.getCreditAmount()))
                    .build();
            ledgerEntryRepository.save(ledgerEntry);
        }
    }
}
