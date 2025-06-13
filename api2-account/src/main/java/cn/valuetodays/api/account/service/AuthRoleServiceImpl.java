package cn.valuetodays.api.account.service;

import java.util.List;
import java.util.stream.Collectors;

import cn.valuetodays.api.account.dao.AuthRoleDAO;
import cn.valuetodays.api.account.persist.AuthRoleActionLinkPO;
import cn.valuetodays.api.account.persist.AuthRolePO;
import cn.valuetodays.api.account.reqresp.AssignActionReq;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * 角色
 *
 * @author lei.liu
 * @since 2020-09-24 10:14
 */
@ApplicationScoped
public class AuthRoleServiceImpl
    extends BaseCrudService<Long, AuthRolePO, AuthRoleDAO> {

    @Inject
    AuthRoleActionLinkServiceImpl authRoleActionLinkService;

    @Transactional
    public boolean assignAction(AssignActionReq req) {
        Long roleId = req.getRoleId();
        List<Long> actionIdList = req.getActionIdList();

        authRoleActionLinkService.deleteByRoleId(roleId);
        List<AuthRoleActionLinkPO> list = actionIdList.stream().map(e -> {
            AuthRoleActionLinkPO authRoleActionLinkPO = new AuthRoleActionLinkPO();
            authRoleActionLinkPO.setRoleId(roleId);
            authRoleActionLinkPO.setActionId(e);
            authRoleActionLinkPO.setCreateUserId(req.getLoginId());
            authRoleActionLinkPO.setUpdateUserId(req.getLoginId());
            return authRoleActionLinkPO;
        }).collect(Collectors.toList());
        authRoleActionLinkService.save(list);
        return true;
    }

}
