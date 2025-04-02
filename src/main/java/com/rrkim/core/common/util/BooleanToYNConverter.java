package com.rrkim.core.common.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {
    public final String TRUE_VALUE = "Y";
    public final String FALSE_VALUE = "N";

    @Override
    public String convertToDatabaseColumn(Boolean attribute){
        return (attribute != null && attribute) ? TRUE_VALUE : FALSE_VALUE;
    }

    @Override
    public Boolean convertToEntityAttribute(String data) {
        return TRUE_VALUE.equals(data);
    }
}
