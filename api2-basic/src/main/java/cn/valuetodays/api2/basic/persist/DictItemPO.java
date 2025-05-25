package cn.valuetodays.api2.basic.persist;

import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import cn.vt.core.TitleCapable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 字典项
 *
 * @author lei.liu
 * @since 2024-11-13 20:40
 */
@Table(name = "ty_dict_item")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true /*, value = {"hibernateLazyInitializer", "handler"}*/)
@EqualsAndHashCode(callSuper = true)
@Data
public class DictItemPO extends JpaCrudLongIdBasePersist {

    @Column(name = "type_id")
    private Long typeId;
    @Column(name = "code")
    private String code;
    @Column(name = "value")
    private String value;
    @Column(name = "title")
    private String title;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "order_num")
    private Integer orderNum;

    @Getter
    public enum Status implements TitleCapable {
        NORMAL("NORMAL"),
        DELETED("DELETED");

        private final String title;

        Status(String title) {
            this.title = title;
        }
    }
}
