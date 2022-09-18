package com.hempel.hembank.transaction;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.hempel.hembank.domain.Transaction;
import com.hempel.hembank.dto.TransactionDTO;
import com.hempel.hembank.enums.OperationType;

@Component
public class TransactionSaque implements TransactionHandler {

    @Override
    public OperationType getOperationType() {
        return OperationType.SAQUE;
    }

    @Override
    public Transaction handle(TransactionDTO transaction) {

        Transaction newTransaction = new Transaction();
        newTransaction.setOperationType(OperationType.SAQUE);
        newTransaction.setEventDate(LocalDateTime.now());
        newTransaction.setAmount(transaction.getAmount().negate());

        return newTransaction;
    }
}
