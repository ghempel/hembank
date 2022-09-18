package com.hempel.hembank.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hempel.hembank.domain.Account;
import com.hempel.hembank.domain.Transaction;
import com.hempel.hembank.dto.TransactionDTO;
import com.hempel.hembank.enums.OperationType;
import com.hempel.hembank.transaction.TransactionHandler;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {

    private final AccountService accountService;
    private final List<TransactionHandler> transactionHandlers;

    @Transactional
    public void createTransaction(TransactionDTO transactionDTO) {

        Account account = accountService.findById(transactionDTO.getAccountId());

        OperationType operationType = OperationType.fromCode(transactionDTO.getOperationTypeId());

        TransactionHandler transcationHandler = transactionHandlers.stream()
                .filter(t -> operationType.equals(t.getOperationType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("TranscationHandler not found"));

        Transaction newTransaction = transcationHandler.handle(transactionDTO);

        account.getTransactions().add(newTransaction);

        accountService.save(account);
    }
}
