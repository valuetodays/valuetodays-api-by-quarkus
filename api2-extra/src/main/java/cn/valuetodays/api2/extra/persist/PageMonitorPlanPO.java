package cn.valuetodays.api2.extra.persist;

import java.time.LocalDateTime;

import cn.valuetodays.api2.extra.enums.PageMonitorPlanType;
import cn.valuetodays.api2.extra.persist.converter.PageMonitorPlanTypeConfJsonAttributeConverter;
import cn.valuetodays.api2.web.common.CommonEnums;
import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 页面监控计划
 *
 * @author lei.liu
 * @since 2023-02-08 10:44
 */
@Cacheable
@Table(name = "extra_page_monitor_plan")
@Cache(region = "orm.PageMonitorPlan", usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class PageMonitorPlanPO extends JpaCrudLongIdBasePersist {

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "title")
    private String title;
    @Column(name = "url")
    private String url;
    @Column(name = "executor_bean_name")
    private String executorBeanName;
    @Column(name = "conf")
//    @Type(JsonType.class)
    @Convert(converter = PageMonitorPlanTypeConfJsonAttributeConverter.class)
    private PageMonitorPlanType.Conf conf;
    @Column(name = "enable_status")
    @Enumerated(EnumType.STRING)
    private CommonEnums.YNEnum enableStatus;
    @Column(name = "unique_text")
    private String uniqueText;
    @Column(name = "last_schedule_time")
    private LocalDateTime lastScheduleTime;
    @Column(name = "last_schedule_status")
    @Enumerated(EnumType.STRING)
    private CommonEnums.YNEnum lastScheduleStatus;
    @Column(name = "last_schedule_remark")
    private String lastScheduleRemark;

}
