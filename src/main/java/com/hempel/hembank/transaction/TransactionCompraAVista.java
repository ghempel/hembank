package com.hempel.hembank.transaction;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.hempel.hembank.domain.Transaction;
import com.hempel.hembank.dto.TransactionDTO;
import com.hempel.hembank.enums.OperationType;

@Component
public class TransactionCompraAVista implements TransactionHandler {

    @Override
    public OperationType getOperationType() {
        return OperationType.COMPRA_A_VISTA;
    }

    @Override
    public Transaction handle(TransactionDTO transaction) {

        Transaction newTransaction = new Transaction();
        newTransaction.setOperationType(OperationType.COMPRA_A_VISTA);
        newTransaction.setEventDate(LocalDateTime.now());
        newTransaction.setAmount(transaction.getAmount().negate());

        return newTransaction;
    }
}
