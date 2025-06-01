package cn.valuetodays.module.codegenerator.po;

import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码生成 - 模板.
 *
 * @author lei.liu
 * @since 2022-06-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cg_template")
@JsonIgnoreProperties(
    ignoreUnknown = true,
    value = {"hibernateLazyInitializer", "handler", "fieldHandler", "$$_hibernate_interceptor"}
)
public class CgTemplatePO extends JpaCrudLongIdBasePersist {
    @Column(name = "title")
    private String title;
    @Column(name = "code")
    private String code;
    @Column(name = "dst_dir")
    private String dstDir;
    @Column(name = "file_name")
    private String fileName;
}
