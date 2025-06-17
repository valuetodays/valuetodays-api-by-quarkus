package cn.valuetodays.api2.extra.persist;

import cn.valuetodays.api2.web.common.CommonEnums;
import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 页面监控日志
 *
 * @author lei.liu
 * @since 2023-02-08 10:44
 */
@Table(name = "extra_page_monitor_log")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class PageMonitorLogPO extends JpaCrudLongIdBasePersist {
    @Column(name = "plan_id")
    private Long planId;
    @Column(name = "schedule_status")
    @Enumerated(EnumType.STRING)
    private CommonEnums.YNEnum scheduleStatus;
    @Column(name = "changed_page_source")
    private String changedPageSource;
}
