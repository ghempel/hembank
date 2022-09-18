package com.hempel.hembank.converter;

import java.util.Optional;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.hempel.hembank.enums.OperationType;

@Converter(autoApply = true)
public class OperationTypeConverter implements AttributeConverter<OperationType, Long> {

    @Override
    public Long convertToDatabaseColumn(OperationType operationType) {
        return Optional.ofNullable(operationType).map(OperationType::getCode).orElse(null);
    }

    @Override
    public OperationType convertToEntityAttribute(Long code) {
        return Optional.ofNullable(code).map(OperationType::fromCode).orElse(null);
    }

}
