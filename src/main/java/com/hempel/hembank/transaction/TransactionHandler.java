package com.hempel.hembank.transaction;

import com.hempel.hembank.domain.Transaction;
import com.hempel.hembank.dto.TransactionDTO;
import com.hempel.hembank.enums.OperationType;

public interface TransactionHandler {

    OperationType getOperationType();

    Transaction handle(TransactionDTO transaction);
}
