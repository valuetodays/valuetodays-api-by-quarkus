package cn.valuetodays.api2.module.fortune.dao;

import java.math.BigDecimal;
import java.util.Optional;

import cn.valuetodays.api2.module.fortune.persist.StockTradeInterestPO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


/**
 * .
 *
 * @author lei.liu
 * @since 2023-10-17
 */
@ApplicationScoped
public class StockTradeInterestDAO implements PanacheRepository<StockTradeInterestPO> {
    public BigDecimal sumAllInterest() {
        StockTradeInterestPO o = find("select sum(o.amountFee) as amountFee from StockTradeInterestPO o").firstResult();
        return Optional.ofNullable(o).map(StockTradeInterestPO::getAmountFee).orElse(BigDecimal.ZERO);
    }
}
