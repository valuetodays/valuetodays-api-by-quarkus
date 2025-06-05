package cn.valuetodays.api2.basic.persist;

import cn.valuetodays.api2.basic.enums.JpaSampleEnums;
import cn.valuetodays.api2.basic.jpa.converter.JsonSampleListJsonAttributeConverter;
import cn.valuetodays.api2.client.bases.jpa.ListLongJsonAttributeConverter;
import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * jpa查询示例
 *
 * @author lei.liu
 * @since 2025-06-02 07:43
 */
@Table(name = "jpa_sample")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true /*, value = {"hibernateLazyInitializer", "handler"}*/)
@EqualsAndHashCode(callSuper = true)
@Data
public class JpaSamplePersist extends JpaCrudLongIdBasePersist {

    @Column(name = "name_cn")
    private String nameCn;
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private JpaSampleEnums.Type type;
    @Column(name = "status")
    private String status;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "birthday2")
    private LocalDate birthday2;
    @Column(name = "birthtime")
    private LocalDateTime birthtime;
    // todo          // https://stackoverflow.com/questions/62246050/how-to-update-a-jsonb-column-in-postgresql-using-a-spring-data-jpa-query
    @Column(name = "json_list")
//    @Type(JsonType.class)
    @Convert(converter = JsonSampleListJsonAttributeConverter.class)
    private List<JsonSample> jsonList;
    @Column(name = "json2_list")
//    @Type(JsonType.class)
    @Convert(converter = JsonSampleListJsonAttributeConverter.class)
    private List<JsonSample> json2List;
    @Column(name = "id_list")
    @Convert(converter = ListLongJsonAttributeConverter.class)
    private List<Long> idList;

    @Data
    public static class JsonSample implements Serializable {
        private String type;
    }
}
