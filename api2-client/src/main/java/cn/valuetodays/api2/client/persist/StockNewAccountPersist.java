package cn.valuetodays.api2.client.persist;

import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "fortune_stock_new_account")
public class StockNewAccountPersist extends JpaCrudLongIdBasePersist {

    @Column(name = "year_month_val")
    private Integer yearMonthVal;
    @Column(name = "a_account")
    private BigDecimal aAccount;
    @Column(name = "b_account")
    private BigDecimal bAccount;
    @Column(name = "fund_account")
    private BigDecimal fundAccount;

}
