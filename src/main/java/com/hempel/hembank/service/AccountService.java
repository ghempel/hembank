package com.hempel.hembank.service;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hempel.hembank.domain.Account;
import com.hempel.hembank.repository.AccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public Account findById(UUID id) {

       return accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Account not found!"));
    }

    @Transactional
    public void save(Account account) {

        Account accountFound = accountRepository.findByDocumentNumber(account.getDocumentNumber());

        if(Objects.nonNull(accountFound) && !accountFound.getId().equals(account.getId())) {

            throw new IllegalArgumentException(String.format(
                    "An account already exists with the document number: [%s].", account.getDocumentNumber()));
        }

        accountRepository.save(account);
    }
}
