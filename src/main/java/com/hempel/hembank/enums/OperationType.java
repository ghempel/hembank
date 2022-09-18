package com.hempel.hembank.enums;

import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OperationType {

    COMPRA_A_VISTA(1L, "Compra a Vista"),
    COMPRA_PARCELADA(2L, "Compra Parcelada"),
    SAQUE(3L, "Saque"),
    PAGAMENTO(4L, "Pagamento");

    private final Long id;
    private final String descricao;

    public Long getCode(){
        return id;
    }

    public static OperationType fromCode(Long code) {

        return Stream.of(OperationType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("OperationType not found with code: [%d]", code)));
    }
}
