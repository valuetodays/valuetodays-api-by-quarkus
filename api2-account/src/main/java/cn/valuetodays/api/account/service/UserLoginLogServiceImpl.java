package cn.valuetodays.api.account.service;

import cn.valuetodays.api.account.dao.UserLoginLogDAO;
import cn.valuetodays.api.account.persist.UserLoginLogPO;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * 用户登录日志表
 *
 * @author lei.liu
 * @since 2020-07-22 13:32
 */
@ApplicationScoped
public class UserLoginLogServiceImpl
    extends BaseCrudService<Long, UserLoginLogPO, UserLoginLogDAO> {

    public UserLoginLogPO findTopByCreateUserIdOrderByCreateTimeDesc(Long userId) {
        return getRepository().findTopByCreateUserIdOrderByCreateTimeDesc(userId);
    }

}
