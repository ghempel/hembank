package com.hempel.hembank.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hempel.hembank.domain.Account;
import com.hempel.hembank.domain.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
