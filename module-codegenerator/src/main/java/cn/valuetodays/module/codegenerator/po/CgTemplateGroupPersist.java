package cn.valuetodays.module.codegenerator.po;

import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import java.util.List;

/**
 * 代码生成-模板组
 *
 * @author lei.liu
 * @since 2024-05-01 02:02
 */
@Table(name = "cg_template_group")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class CgTemplateGroupPersist extends JpaCrudLongIdBasePersist {
    @Column(name = "title")
    private String title;
    @Column(name = "template_ids")
    @Type(JsonType.class)
//    @Convert(converter = ListLongJsonAttributeConverter.class)
    private List<Long> templateIds;
}
