package cn.valuetodays.api.account.controller;

import cn.valuetodays.api.account.reqresp.LoginBO;
import cn.valuetodays.api.account.reqresp.LoginReq;
import cn.valuetodays.api.account.reqresp.TokenInfoVO;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.HttpHeaders;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-08
 */
@Path("/account")
public class AccountController {

    @Path("/login.do")
    @POST
    public TokenInfoVO login(@Valid LoginReq loginReq, HttpHeaders httpHeaders) {
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

//        AccountBO loginAccount = userService.login(loginBO);
//        AssertUtils.assertNotNull(loginAccount, "用户名或密码错误");
//        AssertUtils.assertFalse(
//            UserEnums.Status.FORBIDDEN == loginAccount.getStatus(),
//            "账号被禁用"
//        );

        TokenInfoVO tokenInfoVO = new TokenInfoVO();
//        refreshTokenAndAddToResponse(request, response, loginAccount, tokenInfoVO);
        return tokenInfoVO;
    }
}
