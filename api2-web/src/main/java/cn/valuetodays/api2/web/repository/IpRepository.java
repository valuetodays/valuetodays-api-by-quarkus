package cn.valuetodays.api2.web.repository;

import cn.valuetodays.api2.client.persist.IpPersist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-29
 */
@ApplicationScoped
public class IpRepository implements PanacheRepository<IpPersist> {

}
