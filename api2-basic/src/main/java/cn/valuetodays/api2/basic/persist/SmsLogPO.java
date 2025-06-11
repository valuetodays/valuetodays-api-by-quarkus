package cn.valuetodays.api2.basic.persist;

import cn.valuetodays.api2.basic.enums.SmsLogEnums;
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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-04-23
 */
@Schema(name = "短信发送记录")
@EqualsAndHashCode(callSuper = true)
@Cacheable
@Cache(region = "orm.module.SmsLog", usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "ty_sms_log")
@JsonIgnoreProperties(
    ignoreUnknown = true,
    value = {"hibernateLazyInitializer", "handler", "fieldHandler", "$$_hibernate_interceptor"}
)
@Data
public class SmsLogPO extends JpaCrudLongIdBasePersist {
    @Schema(description = "状态")
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SmsLogEnums.StatusEnum status;
    @Schema(description = "手机号")
    @Column(name = "mobile", nullable = false)
    private String mobile;
    @Schema(description = "内容")
    @Column(name = "content", nullable = false)
    private String content;
}
