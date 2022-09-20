package com.hempel.hembank.dto;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionDTO {

    @NotNull
    private UUID accountId;

    @NotNull
    private Long operationTypeId;

    @NotNull
    @Positive
    private BigDecimal amount;
}
