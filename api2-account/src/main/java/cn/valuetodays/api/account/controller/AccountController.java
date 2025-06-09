package cn.valuetodays.api.account.controller;

import cn.valuetodays.api.account.persist.UserPO;
import cn.valuetodays.api.account.reqresp.AccountBO;
import cn.valuetodays.api.account.service.UserServiceImpl;
import cn.valuetodays.quarkus.commons.base.BaseAuthorizationController;
import cn.vt.R;
import cn.vt.auth.AuthUserHolder;
import cn.vt.util.ConvertUtils;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.extern.slf4j.Slf4j;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-08
 */
@Path("/account")
@Slf4j
public class AccountController extends BaseAuthorizationController {
    @Inject
    UserServiceImpl userService;

    @Path("logout")
    @POST
    public R<String> logout(@Context RoutingContext ctx) {
//        super.removeLoginAccount();
        ctx.response().putHeader(AuthUserHolder.AUTH_HEADER_KEY, "");
        return R.success("ok");
    }

    @POST
    @Path("/current")
    public R<AccountBO> current() {
        UserPO userPO = userService.findById(getCurrentAccountId());
        AccountBO accountBO = ConvertUtils.convertObj(userPO, AccountBO.class);
        return R.success(accountBO);
    }

}
