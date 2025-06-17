package cn.valuetodays.api2.extra.persist;

import cn.valuetodays.api2.extra.enums.SanguoEventEnums;
import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "extra_sanguo_event")
@JsonIgnoreProperties(
    ignoreUnknown = true,
    value = {"hibernateLazyInitializer", "handler", "fieldHandler", "$$_hibernate_interceptor"}
)
@Schema(name = "三国事件")
public class SanguoEventPO extends JpaCrudLongIdBasePersist {
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SanguoEventEnums.TypeEnum type;
    @Schema(name = "中文姓名", examples = "曹操")
    @Column(name = "cname")
    private String cname;
    @Schema(name = "所属势力", examples = "WEI")
    @Column(name = "belongs")
    @Enumerated(EnumType.STRING)
    private SanguoEventEnums.BelongsEnum belongs;
    @Schema(name = "中文-字", examples = "孟德")
    @Column(name = "calias")
    private String calias;
    @Schema(name = "日文姓名", examples = "曹操")
    @Column(name = "jname")
    private String jname;
    @Schema(name = "日文-字", examples = "ソウソウ")
    @Column(name = "jalias")
    private String jalias;
    @Schema(name = "日文姓名的罗马读音", examples = "曹操")
    @Column(name = "roman_name")
    private String romanName;
    @Schema(name = "日文-字的罗马读音", examples = "ソウソウ")
    @Column(name = "roman_alias")
    private String romanAlias;
    @Schema(name = "出生年份", examples = "155")
    @Column(name = "birth_year")
    private int birthYear;
    @Schema(name = "死亡年份", examples = "220")
    @Column(name = "die_year")
    private int dieYear;
    @Schema(name = "死因")
    @Column(name = "die_reason")
    private String dieReason;
    @Schema(name = "事件", examples = "治世之能臣，乱世之奸雄")
    @Column(name = "events")
    private String events;
}

