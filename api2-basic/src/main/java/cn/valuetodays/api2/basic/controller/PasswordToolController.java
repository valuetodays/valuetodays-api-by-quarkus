package cn.valuetodays.api2.basic.controller;

import cn.valuetodays.api2.basic.vo.EncryptPasswordReq;
import cn.valuetodays.api2.basic.vo.EncryptPasswordResp;
import cn.vt.R;
import cn.vt.encrypt.BCryptUtils;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-10
 */
@Path("/passwordTool")
public class PasswordToolController {

    @Path("/encryptPassword")
    @POST
    public R<EncryptPasswordResp> encryptPassword(EncryptPasswordReq encryptPasswordReq) {
        String hashpwed = BCryptUtils.hashpw(encryptPasswordReq.getRawPassword());
        EncryptPasswordResp resp = new EncryptPasswordResp();
        resp.setEncryptedPassword(hashpwed);
        return R.success(resp);
    }
}
