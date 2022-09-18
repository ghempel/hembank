package com.hempel.hembank.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hempel.hembank.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Boolean existsByDocumentNumber(String documentNumber);
}
