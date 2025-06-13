package cn.valuetodays.api2.client.bases.jpa;

import java.util.Set;

import cn.vt.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-13
 */
@Converter
public class SetLongJsonAttributeConverter implements AttributeConverter<Set<Long>, String> {
    @Override
    public String convertToDatabaseColumn(Set<Long> attribute) {
        return JsonUtils.toJsonString(attribute);
    }

    @Override
    public Set<Long> convertToEntityAttribute(String dbData) {
        return JsonUtils.fromJson(dbData, new TypeReference<>() {
        });
    }
}

