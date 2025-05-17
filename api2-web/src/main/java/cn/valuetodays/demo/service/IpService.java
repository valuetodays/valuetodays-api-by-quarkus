package cn.valuetodays.demo.service;

import cn.valuetodays.api2.persist.IpPersist;
import cn.valuetodays.demo.repository.IpRepository;
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
