package com.hempel.hembank.dto;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotNull;

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
    private BigDecimal amount;
}
