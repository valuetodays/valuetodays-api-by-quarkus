package cn.valuetodays.api2.extra.dao;


import cn.valuetodays.api2.extra.persist.WeiboPO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2024-04-28 14:07
 */
@ApplicationScoped
public class WeiboDAO implements PanacheRepository<WeiboPO> {

}
