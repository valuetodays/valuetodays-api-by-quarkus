package cn.valuetodays.api2.web.repository;

import cn.valuetodays.api2.client.persist.IpPersist;
import cn.valuetodays.quarkus.commons.base.BaseJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-29
 */
@ApplicationScoped
public class IpRepository extends BaseJpaRepository<IpPersist, Long> {

    protected IpRepository() {
        super(IpPersist.class);
    }
}
