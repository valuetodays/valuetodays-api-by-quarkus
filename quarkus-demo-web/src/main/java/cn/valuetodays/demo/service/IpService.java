package cn.valuetodays.demo.service;

import cn.valuetodays.demo.persist.IpPersist;
import cn.valuetodays.demo.repository.IpRepository;
import jakarta.inject.Singleton;
import valuetodays.demo.commons.base.BaseService;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-29
 */
@Singleton
public class IpService extends BaseService<Long, IpPersist, IpRepository> {

}
