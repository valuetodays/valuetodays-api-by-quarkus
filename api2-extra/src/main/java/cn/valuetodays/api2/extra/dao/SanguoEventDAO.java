package cn.valuetodays.api2.extra.dao;

import cn.valuetodays.api2.extra.persist.SanguoEventPO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-03-04
 */
@ApplicationScoped
public class SanguoEventDAO implements PanacheRepository<SanguoEventPO> {
}
