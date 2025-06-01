package cn.valuetodays.module.codegenerator.advanced.generate;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lei.liu
 * @since 2018-09-06 16:56:41
 */
@Setter
@Getter
public abstract class TemplateConf {
    private Map<String, Object> context = new HashMap<>();

}
