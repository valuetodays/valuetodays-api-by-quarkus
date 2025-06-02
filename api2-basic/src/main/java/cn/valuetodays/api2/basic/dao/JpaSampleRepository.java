package cn.valuetodays.api2.basic.dao;

import cn.valuetodays.api2.basic.persist.JpaSamplePersist;
import cn.valuetodays.quarkus.commons.base.BaseJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2025-06-02 07:43
 */
@ApplicationScoped
public class JpaSampleRepository extends BaseJpaRepository<JpaSamplePersist, Long> {

    protected JpaSampleRepository() {
        super(JpaSamplePersist.class);
    }
}

