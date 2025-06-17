package cn.valuetodays.api2.extra.persist.converter;

import cn.valuetodays.api2.extra.enums.PageMonitorPlanType;
import cn.vt.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-01
 */
@Converter
public class PageMonitorPlanTypeConfJsonAttributeConverter implements AttributeConverter<PageMonitorPlanType.Conf, String> {
    @Override
    public String convertToDatabaseColumn(PageMonitorPlanType.Conf attribute) {
        return JsonUtils.toJsonString(attribute);
    }

    @Override
    public PageMonitorPlanType.Conf convertToEntityAttribute(String dbData) {
        return JsonUtils.fromJson(dbData, new TypeReference<>() {
        });
    }
}
