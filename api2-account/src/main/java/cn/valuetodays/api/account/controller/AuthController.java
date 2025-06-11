package cn.valuetodays.api.account.controller;

import cn.valuetodays.api.account.enums.UserEnums;
import cn.valuetodays.api.account.persist.UserBrowserFingerprintPersist;
import cn.valuetodays.api.account.persist.UserPO;
import cn.valuetodays.api.account.reqresp.AccountBO;
import cn.valuetodays.api.account.reqresp.LoginBO;
import cn.valuetodays.api.account.reqresp.LoginByBrowserFingerprintReq;
import cn.valuetodays.api.account.reqresp.LoginReq;
import cn.valuetodays.api.account.reqresp.TokenInfoVO;
import cn.valuetodays.api.account.service.UserBrowserFingerprintServiceImpl;
import cn.valuetodays.api.account.service.UserServiceImpl;
import cn.valuetodays.quarkus.commons.base.BaseAuthorizationController;
import cn.vt.R;
import cn.vt.auth.AuthUser;
import cn.vt.auth.AuthUserHolder;
import cn.vt.exception.AssertUtils;
import cn.vt.util.ConvertUtils2;
import cn.vt.util.TokenUtils;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-08
 */
@Path("/auth")
@Slf4j
public class AuthController extends BaseAuthorizationController {
    @Inject
    UserServiceImpl userService;
    @Inject
    UserBrowserFingerprintServiceImpl userBrowserFingerprintService;

    @Path("/public/login")
    @POST
    public R<TokenInfoVO> login(@Valid LoginReq loginReq, HttpHeaders httpHeaders, @Context RoutingContext ctx) {
//        StringBuilder sb = new StringBuilder();
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            String value = request.getHeader(headerName);
//            sb.append("> ").append(headerName).append("=").append(value).append("\n");
//        }
//        log.debug("header: {}", sb.toString());

        String username = loginReq.getUsername();
        String password = loginReq.getPassword();
        String userAgent = httpHeaders.getHeaderString("User-Agent");
        LoginBO loginBO = LoginBO.builder().mobile(username).password(password).userAgent(userAgent).build();

        AccountBO loginAccount = userService.login(loginBO);
        AssertUtils.assertNotNull(loginAccount, "用户名或密码错误");
        AssertUtils.assertFalse(
            UserEnums.Status.FORBIDDEN == loginAccount.getStatus(),
            "账号被禁用"
        );

        TokenInfoVO tokenInfoVO = new TokenInfoVO();
        refreshTokenAndAddToResponse(ctx, loginAccount, tokenInfoVO);
        return R.success(tokenInfoVO);
    }


    @Path("/public/autologin")
    @POST
    public R<AuthUser> autologin(@Context RoutingContext ctx) {
        autoLoginByAccountId(ctx, getCurrentAccountId());
        return R.success(getCurrentAccount());
    }


    @Path("/public/loginByBrowserFingerprint")
    @POST
    public R<TokenInfoVO> loginByBrowserFingerprint(@Valid LoginByBrowserFingerprintReq req,
                                                    HttpHeaders httpHeaders,
                                                    @Context RoutingContext ctx) {
        String browserFingerprint = req.getBrowserFingerprint();
        log.info("browserFingerprint={}", browserFingerprint);
        UserBrowserFingerprintPersist exist = userBrowserFingerprintService.findByFingerprint(browserFingerprint);
        if (Objects.isNull(exist)) {
            return R.fail("no record");
        }
        Pair<TokenInfoVO, UserPO> tokenInfoVOUserPOPair = autoLoginByAccountId(ctx, exist.getAccountId());
        TokenInfoVO tokenInfoVO = tokenInfoVOUserPOPair.getLeft();
        UserPO userInAttribute = tokenInfoVOUserPOPair.getRight();
        String userAgent = httpHeaders.getHeaderString("User-Agent");
        userService.loginByPO(userInAttribute,
            userAgent, userInAttribute.getEmail(), "loginByBrowserFingerprint", false);
        return R.success(tokenInfoVO);
    }


    private Pair<TokenInfoVO, UserPO> autoLoginByAccountId(RoutingContext ctx, Long accountId) {
        UserPO userPO = userService.findById(accountId);
        AccountBO accountBO = ConvertUtils2.convertObj(userPO, AccountBO.class);
        TokenInfoVO tokenInfoVO = new TokenInfoVO();
        refreshTokenAndAddToResponse(ctx, accountBO, tokenInfoVO);
        return Pair.of(tokenInfoVO, userPO);
    }


    private void refreshTokenAndAddToResponse(
        RoutingContext ctx, AccountBO loginAccount,
        TokenInfoVO tokenInfoVO) {
        String token = TokenUtils.generate();
        tokenInfoVO.setAccessToken(token);
        AuthUser authUser = new AuthUser();
        authUser.setUserId(String.valueOf(loginAccount.getId()));
        authUser.setEmail(loginAccount.getEmail());
        authUser.setLoginToken(token);
        super.putLoginAccount(authUser);
        ctx.response().putHeader(AuthUserHolder.AUTH_HEADER_KEY, token);
    }

}
