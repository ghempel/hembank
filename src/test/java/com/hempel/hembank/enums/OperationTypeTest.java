package com.hempel.hembank.enums;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OperationTypeTest {

    @Test
    public void fromCode() {

        assertThat(OperationType.fromCode(1L)).isEqualTo(OperationType.COMPRA_A_VISTA);
        assertThat(OperationType.fromCode(2L)).isEqualTo(OperationType.COMPRA_PARCELADA);
        assertThat(OperationType.fromCode(3L)).isEqualTo(OperationType.SAQUE);
        assertThat(OperationType.fromCode(4L)).isEqualTo(OperationType.PAGAMENTO);

    }

    @Test
    public void fromCodeNotFound() {

       assertThatThrownBy(() -> OperationType.fromCode(77L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("OperationType not found with code: [77]");

    }
}
