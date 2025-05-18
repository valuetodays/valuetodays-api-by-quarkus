package cn.valuetodays.api2.web.service;

import cn.valuetodays.api2.client.persist.IpPersist;
import cn.valuetodays.api2.web.repository.IpRepository;
import cn.valuetodays.quarkus.commons.base.BaseService;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-29
 */
@ApplicationScoped
public class IpService extends BaseService<Long, IpPersist, IpRepository> {

}
