package cn.valuetodays.api2.basic.dao;

import cn.valuetodays.api2.basic.persist.JpaSamplePersist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2025-06-02 07:43
 */
@ApplicationScoped
public class JpaSampleRepository implements PanacheRepository<JpaSamplePersist> {

}

