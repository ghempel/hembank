package com.hempel.hembank.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.hempel.hembank.domain.Account;
import com.hempel.hembank.domain.Transaction;
import com.hempel.hembank.dto.TransactionDTO;
import com.hempel.hembank.enums.OperationType;
import com.hempel.hembank.transaction.TransactionPagamento;
import com.hempel.hembank.transaction.TransactionSaque;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Spy
    private TransactionSaque transactionSaque;

    @Spy
    private TransactionPagamento transactionPagamento;

    @Mock
    private AccountService accountService;

    @Captor
    private ArgumentCaptor<Account> accountCaptor;

    private TransactionService transactionService;

    @Before
    public void setup() {

        transactionService = new TransactionService(accountService, List.of(transactionSaque, transactionPagamento));

    }

    @Test
    public void saveTransactionTypeSaque(){

        UUID idAccount = UUID.fromString("5b3c7b7b-6244-4a22-9b50-d021734b6071");

        Account account = Account.of(idAccount);
        account.setDocumentNumber("01234567890");

        when(accountService.findById(eq(idAccount))).thenReturn(account);

        TransactionDTO dto = new TransactionDTO();
        dto.setAccountId(idAccount);
        dto.setAmount(BigDecimal.TEN);
        dto.setOperationTypeId(3L);

        transactionService.createTransaction(dto);

        verify(accountService, times(1)).save(accountCaptor.capture());
        verify(accountService, times(1)).findById(eq(idAccount));
        verifyNoMoreInteractions(accountService);

        Account accountSaved = accountCaptor.getValue();

        assertThat(accountSaved).isNotNull();
        assertThat(accountSaved.getId()).isEqualTo(idAccount);
        assertThat(accountSaved.getDocumentNumber()).isEqualTo("01234567890");

        assertThat(accountSaved.getTransactions()).isNotEmpty();
        assertThat(accountSaved.getTransactions()).hasSize(1);

        Transaction transactionSaved = accountSaved.getTransactions().get(0);

        assertThat(transactionSaved).isNotNull();
        assertThat(transactionSaved.getOperationType()).isEqualTo(OperationType.SAQUE);
        assertThat(transactionSaved.getAmount()).isEqualTo(BigDecimal.TEN.negate());

    }

    @Test
    public void saveTransactionTypePagamento(){

        UUID idAccount = UUID.fromString("5b3c7b7b-6244-4a22-9b50-d021734b6071");

        Account account = Account.of(idAccount);
        account.setDocumentNumber("01234567890");

        when(accountService.findById(eq(idAccount))).thenReturn(account);

        TransactionDTO dto = new TransactionDTO();
        dto.setAccountId(idAccount);
        dto.setAmount(BigDecimal.TEN);
        dto.setOperationTypeId(4L);

        transactionService.createTransaction(dto);

        verify(accountService, times(1)).save(accountCaptor.capture());
        verify(accountService, times(1)).findById(eq(idAccount));
        verifyNoMoreInteractions(accountService);

        Account accountSaved = accountCaptor.getValue();

        assertThat(accountSaved).isNotNull();
        assertThat(accountSaved.getId()).isEqualTo(idAccount);
        assertThat(accountSaved.getDocumentNumber()).isEqualTo("01234567890");

        assertThat(accountSaved.getTransactions()).isNotEmpty();
        assertThat(accountSaved.getTransactions()).hasSize(1);

        Transaction transactionSaved = accountSaved.getTransactions().get(0);

        assertThat(transactionSaved).isNotNull();
        assertThat(transactionSaved.getOperationType()).isEqualTo(OperationType.PAGAMENTO);
        assertThat(transactionSaved.getAmount()).isEqualTo(BigDecimal.TEN);

    }

}
