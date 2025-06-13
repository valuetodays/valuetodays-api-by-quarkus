package cn.valuetodays.api.account.controller;

import cn.valuetodays.api.account.persist.AuthMenuPO;
import cn.valuetodays.api.account.service.AuthMenuServiceImpl;
import cn.valuetodays.quarkus.commons.base.BaseCrudController;
import jakarta.ws.rs.Path;

/**
 * .
 *
 * @author lei.liu
 * @since 2021-02-01 09:32
 */
@Path("/authMenu")
public class AuthMenuController
    extends BaseCrudController<Long, AuthMenuPO, AuthMenuServiceImpl> {

    @Override
    protected void beforeSave(AuthMenuPO authMenuPO) {
        super.beforeSave(authMenuPO);
        Long currentAccountId = getCurrentAccountId();
        authMenuPO.setCreateUserId(currentAccountId);
        authMenuPO.setUpdateUserId(currentAccountId);
    }

}
