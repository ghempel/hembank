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
public class TransactionPagamentoTest {

    @InjectMocks
    private TransactionPagamento transactionPagamento;

    @Test
    public void getOperationType(){

        var operationType = transactionPagamento.getOperationType();

        assertThat(operationType).isNotNull();
        assertThat(operationType).isEqualTo(OperationType.PAGAMENTO);

    }

    @Test
    public void handleTransaction(){

        var transactionDTO = new TransactionDTO();
        transactionDTO.setOperationTypeId(4L);
        transactionDTO.setAmount(BigDecimal.TEN);

        Transaction transaction = transactionPagamento.handle(transactionDTO);

        assertThat(transaction).isNotNull();
        assertThat(transaction.getOperationType()).isEqualTo(OperationType.PAGAMENTO);
        assertThat(transaction.getAmount()).isEqualTo(BigDecimal.TEN);

    }

}
