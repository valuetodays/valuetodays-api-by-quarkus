package cn.valuetodays.api2.basic.jpa.converter;

import cn.valuetodays.api2.basic.persist.JpaSamplePersist;
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
public class JsonSampleListJsonAttributeConverter implements AttributeConverter<List<JpaSamplePersist.JsonSample>, String> {
    @Override
    public String convertToDatabaseColumn(List<JpaSamplePersist.JsonSample> attribute) {
        return JsonUtils.toJsonString(attribute);
    }

    @Override
    public List<JpaSamplePersist.JsonSample> convertToEntityAttribute(String dbData) {
        return JsonUtils.fromJson(dbData, new TypeReference<>() {
        });
    }
}
