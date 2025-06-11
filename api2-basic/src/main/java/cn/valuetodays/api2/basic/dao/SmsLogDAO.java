package cn.valuetodays.api2.basic.dao;

import cn.valuetodays.api2.basic.persist.SmsLogPO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-04-23
 */
@ApplicationScoped
public class SmsLogDAO implements PanacheRepository<SmsLogPO> {
}
