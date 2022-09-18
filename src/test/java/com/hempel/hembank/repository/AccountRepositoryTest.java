package com.hempel.hembank.repository;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.hempel.hembank.domain.Account;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(statements= {
        "insert into account (id, document_number) values ('9e31f5d7-6964-4fe5-a22c-9254db0e178c', '12345678900');"
})
@RunWith(SpringRunner.class)
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void findAll() {

        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts).isNotEmpty();
        assertThat(accounts).hasSize(1);
        assertThat(accounts).extracting("id").contains(UUID.fromString("9e31f5d7-6964-4fe5-a22c-9254db0e178c"));

    }

    @Test
    public void save() {

        Account account = new Account();
        account.setDocumentNumber("14391116774");

        Account accountSaved = accountRepository.saveAndFlush(account);

        assertThat(accountSaved).isNotNull();
        assertThat(accountSaved.getId()).isEqualTo(account.getId());
        assertThat(accountSaved.getDocumentNumber()).isEqualTo("14391116774");

    }

    @Test
    public void existsByDocumentNumber() {

        assertThat(accountRepository.existsByDocumentNumber("12345678900")).isTrue();
        assertThat(accountRepository.existsByDocumentNumber("12345678977")).isFalse();

    }
}
