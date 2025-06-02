package cn.valuetodays.api2.web.repository;

import cn.valuetodays.api2.client.persist.AccessedUrlPersist;
import cn.valuetodays.quarkus.commons.base.BaseJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2025-03-27 16:01
 */
@ApplicationScoped
public class AccessedUrlDAO extends BaseJpaRepository<AccessedUrlPersist, Long> {

    protected AccessedUrlDAO() {
        super(AccessedUrlPersist.class);
    }
}
