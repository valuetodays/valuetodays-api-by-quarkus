package cn.valuetodays.api2.client.bases.jpa;

import cn.vt.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-01
 */
@Converter
public class ListLongJsonAttributeConverter implements AttributeConverter<List<Long>, String> {
    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        return JsonUtils.toJsonString(attribute);
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        return JsonUtils.fromJson(dbData, new TypeReference<>() {
        });
    }
}
