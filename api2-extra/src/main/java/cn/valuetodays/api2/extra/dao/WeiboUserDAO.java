package cn.valuetodays.api2.extra.dao;

import java.util.List;

import cn.valuetodays.api2.extra.persist.WeiboUserPO;
import cn.valuetodays.api2.web.common.CommonEnums;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


/**
 * @author lei.liu
 * @since 2024-04-28 14:07
 */
@ApplicationScoped
public class WeiboUserDAO implements PanacheRepository<WeiboUserPO> {

    public List<WeiboUserPO> findAllByMaintain(CommonEnums.YNEnum yn) {
        return find("maintain = ?1", yn).list();
    }
}
