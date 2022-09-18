package com.hempel.hembank.resource;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hempel.hembank.dto.TransactionDTO;
import com.hempel.hembank.service.TransactionService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionResource {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid TransactionDTO transactionDTO){

        transactionService.createTransaction(transactionDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
