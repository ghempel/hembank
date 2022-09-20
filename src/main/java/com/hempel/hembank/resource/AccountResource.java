package com.hempel.hembank.resource;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hempel.hembank.domain.Account;
import com.hempel.hembank.repository.AccountRepository;
import com.hempel.hembank.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts")
public class AccountResource {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @GetMapping("/list")
    @Operation(summary = "Foi criado apenas para identificar as Accounts criadas. findAll é ruim!")
    public ResponseEntity<List<Account>> findAll(){

        return ResponseEntity.ok(accountRepository.findAll());
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> findById(@PathVariable(name = "accountId") UUID accountId){

        return ResponseEntity.ok(accountService.findById(accountId));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid Account account){

        accountService.save(account);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
