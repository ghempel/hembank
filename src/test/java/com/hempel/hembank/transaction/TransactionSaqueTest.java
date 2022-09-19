package com.hempel.hembank.transaction;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.hempel.hembank.domain.Transaction;
import com.hempel.hembank.dto.TransactionDTO;
import com.hempel.hembank.enums.OperationType;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TransactionSaqueTest {

    @InjectMocks
    private TransactionSaque transactionSaque;

    @Test
    public void getOperationType(){

        var operationType = transactionSaque.getOperationType();

        assertThat(operationType).isNotNull();
        assertThat(operationType).isEqualTo(OperationType.SAQUE);

    }

    @Test
    public void handleTransaction(){

        var transactionDTO = new TransactionDTO();
        transactionDTO.setOperationTypeId(3L);
        transactionDTO.setAmount(BigDecimal.TEN);

        Transaction transaction = transactionSaque.handle(transactionDTO);

        assertThat(transaction).isNotNull();
        assertThat(transaction.getOperationType()).isEqualTo(OperationType.SAQUE);
        assertThat(transaction.getAmount()).isEqualTo(BigDecimal.TEN.negate());

    }

}
