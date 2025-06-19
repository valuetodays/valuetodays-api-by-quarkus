package cn.valuetodays.api2.extra.service;

import java.util.List;

import cn.valuetodays.api2.extra.dao.WeiboUserDAO;
import cn.valuetodays.api2.extra.persist.WeiboUserPO;
import cn.valuetodays.api2.web.common.CommonEnums;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2024-04-28 14:07
 */
@ApplicationScoped
public class WeiboUserServiceImpl
    extends BaseCrudService<Long, WeiboUserPO, WeiboUserDAO> {

    public List<WeiboUserPO> findAllMaintaining() {
        return getRepository().findAllByMaintain(CommonEnums.YNEnum.Y);
    }
}
