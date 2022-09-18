package com.hempel.hembank.service;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.hempel.hembank.domain.Account;
import com.hempel.hembank.domain.Transaction;
import com.hempel.hembank.repository.AccountRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void findById(){

        UUID idAccount = UUID.fromString("5b3c7b7b-6244-4a22-9b50-d021734b6071");

        Account account = Account.of(idAccount);

        when(accountRepository.findById(eq(idAccount))).thenReturn(Optional.of(account));

        Account accountFound = accountService.findById(idAccount);

        assertThat(accountFound).isNotNull();
        assertThat(accountFound.getId()).isEqualTo(idAccount);

        verify(accountRepository, times(1)).findById(eq(idAccount));
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void findByIdNotFound(){

        when(accountRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.findById(UUID.randomUUID()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Account not found!");
    }

    @Test
    public void save(){

        Account account = new Account();
        account.setDocumentNumber("01234567890");

        accountService.save(account);

        verify(accountRepository, times(1)).save(eq(account));
        verify(accountRepository, times(1)).existsByDocumentNumber(eq("01234567890"));
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void saveWhenDocumentNumberAlreadyExists(){

        when(accountRepository.existsByDocumentNumber(eq("01234567890"))).thenReturn(Boolean.TRUE);

        Account account = new Account();
        account.setDocumentNumber("01234567890");

        assertThatThrownBy(() -> accountService.save(account))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("An account already exists with the document number: [01234567890].");
    }

    @Test
    public void saveAccountWithoutCallExists(){

        Account account = Account.of(UUID.fromString("5b3c7b7b-6244-4a22-9b50-d021734b6071"));
        account.setDocumentNumber("01234567890");
        account.getTransactions().add(new Transaction());

        accountService.save(account);

        verify(accountRepository, times(1)).save(eq(account));
        verifyNoMoreInteractions(accountRepository);
    }
}
