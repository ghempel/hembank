package com.hempel.hembank.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hempel.hembank.enums.OperationType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(of = "id")
public class Transaction {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    // TODO getTransactions by account?
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    private Account account;

    @NotNull
    @Column(name = "operation_type_id")
    private OperationType operationType;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @Column(name = "event_date")
    private LocalDateTime eventDate;

}
