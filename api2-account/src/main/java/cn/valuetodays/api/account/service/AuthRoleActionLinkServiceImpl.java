package cn.valuetodays.api.account.service;

import java.util.List;

import cn.valuetodays.api.account.dao.AuthRoleActionLinkDAO;
import cn.valuetodays.api.account.persist.AuthRoleActionLinkPO;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

/**
 * 角色-动作
 *
 * @author lei.liu
 * @since 2020-09-24 10:14
 */
@ApplicationScoped
public class AuthRoleActionLinkServiceImpl
    extends BaseCrudService<Long, AuthRoleActionLinkPO, AuthRoleActionLinkDAO> {

    @Transactional
    public void deleteByRoleId(Long roleId) {
        getRepository().deleteByRoleId(roleId);
    }

    public List<AuthRoleActionLinkPO> listByRoleId(Long roleId) {
        return getRepository().findAllByRoleId(roleId);
    }
}
