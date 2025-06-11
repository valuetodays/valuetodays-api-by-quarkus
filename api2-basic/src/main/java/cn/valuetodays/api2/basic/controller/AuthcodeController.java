package cn.valuetodays.api2.basic.controller;

import cn.valuetodays.api2.basic.service.AuthcodeServiceImpl;
import cn.valuetodays.api2.basic.vo.CreateAuthcodeResp;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 * 生成验证码
 * <p>访问路径是<code>/common/authcode/create.do<code></p>
 *
 * @author liulei
 */
@Path("/common/authcode")
public class AuthcodeController {
    @Inject
    AuthcodeServiceImpl authcodeService;

    @Path("create")
    @POST
    public CreateAuthcodeResp create() {
        return authcodeService.create();
    }

}
