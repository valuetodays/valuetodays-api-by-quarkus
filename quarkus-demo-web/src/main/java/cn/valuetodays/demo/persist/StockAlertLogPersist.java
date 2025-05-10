package cn.valuetodays.demo.persist;

import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 股票告警记录表
 *
 * @author lei.liu
 * @since 2025-04-16 08:40
 */
@Table(name = "fortune_stock_alert_log")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true /*, value = {"hibernateLazyInitializer", "handler"}*/)
@EqualsAndHashCode(callSuper = true)
@Data
public class StockAlertLogPersist extends JpaCrudLongIdBasePersist {


    @Column(name = "alert_id")
    private Long alertId;
    @Column(name = "config_point")
    private BigDecimal configPoint;
    @Column(name = "target_point")
    private BigDecimal targetPoint;
    @Column(name = "target_ptg")
    private BigDecimal targetPtg;
}
