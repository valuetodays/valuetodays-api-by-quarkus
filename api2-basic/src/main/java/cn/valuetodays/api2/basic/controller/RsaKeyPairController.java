package cn.valuetodays.api2.basic.controller;

import cn.valuetodays.api2.basic.persist.RsaKeyPairPersist;
import cn.valuetodays.api2.basic.service.RsaKeyPairService;
import cn.valuetodays.api2.basic.vo.GetPublicKeyResp;
import cn.valuetodays.quarkus.commons.base.BaseCrudController;
import cn.vt.R;
import cn.vt.util.ConvertUtils2;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 * rsa公钥私钥对服务
 *
 * @author lei.liu
 * @since 2025-06-09 19:57
 */
@Path("/rsaKeyPair")
public class RsaKeyPairController
    extends BaseCrudController<
    Long,
    RsaKeyPairPersist,
    RsaKeyPairService
    > {

    @Path("/public/getPublicKey")
    @POST
    public R<GetPublicKeyResp> getPublicKey() {
        RsaKeyPairPersist randomOne = service.randomOne();
        GetPublicKeyResp copied = ConvertUtils2.convertObj(randomOne, GetPublicKeyResp.class);
        return R.success(copied);
    }

}
