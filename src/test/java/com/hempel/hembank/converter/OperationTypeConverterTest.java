package com.hempel.hembank.converter;

import org.junit.Test;

import com.hempel.hembank.enums.OperationType;

import static org.assertj.core.api.Assertions.assertThat;

public class OperationTypeConverterTest {

    private OperationTypeConverter converter = new OperationTypeConverter();

    @Test
    public void convertToLong() {
        assertThat(converter.convertToDatabaseColumn(OperationType.COMPRA_A_VISTA)).isEqualTo(1L);
        assertThat(converter.convertToDatabaseColumn(OperationType.COMPRA_PARCELADA)).isEqualTo(2L);
        assertThat(converter.convertToDatabaseColumn(OperationType.SAQUE)).isEqualTo(3L);
        assertThat(converter.convertToDatabaseColumn(OperationType.PAGAMENTO)).isEqualTo(4L);
    }

    @Test
    public void convertFromLong() {
        assertThat(converter.convertToEntityAttribute(1L)).isEqualTo(OperationType.COMPRA_A_VISTA);
        assertThat(converter.convertToEntityAttribute(2L)).isEqualTo(OperationType.COMPRA_PARCELADA);
        assertThat(converter.convertToEntityAttribute(3L)).isEqualTo(OperationType.SAQUE);
        assertThat(converter.convertToEntityAttribute(4L)).isEqualTo(OperationType.PAGAMENTO);
    }

    @Test
    public void convertNull() {
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
    }


}
