package com.hempel.hembank.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.hempel.hembank.domain.Account;
import com.hempel.hembank.domain.Transaction;
import com.hempel.hembank.enums.OperationType;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(statements= {
        "insert into account (id, document_number) values ('9e31f5d7-6964-4fe5-a22c-9254db0e178c', '12345678900');",

        "insert into transaction (id, account_id, operation_type_id, amount, event_date) "
                + "values ('1b338673-4eb0-435e-8ae9-fa391abb96ad', '9e31f5d7-6964-4fe5-a22c-9254db0e178c', 1, -100.0, '2022-09-17 11:57:59');"
})
@RunWith(SpringRunner.class)
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void findAll() {

        List<Transaction> transactions = transactionRepository.findAll();

        assertThat(transactions).isNotEmpty();
        assertThat(transactions).hasSize(1);
        assertThat(transactions).extracting("id").contains(UUID.fromString("1b338673-4eb0-435e-8ae9-fa391abb96ad"));

    }

    @Test
    public void save() {

        Transaction transaction = new Transaction();
        transaction.setAccount(Account.of(UUID.fromString("9e31f5d7-6964-4fe5-a22c-9254db0e178c")));
        transaction.setOperationType(OperationType.PAGAMENTO);
        transaction.setAmount(BigDecimal.TEN);
        transaction.setEventDate(LocalDateTime.now());

        Transaction transactionSaved = transactionRepository.saveAndFlush(transaction);

        assertThat(transactionSaved).isNotNull();
        assertThat(transactionSaved.getId()).isEqualTo(transaction.getId());

    }
}
