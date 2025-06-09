package cn.valuetodays.api2.basic.controller;

import cn.valuetodays.api2.basic.persist.RsaKeyPairPersist;
import cn.valuetodays.api2.basic.service.RsaKeyPairService;
import cn.valuetodays.quarkus.commons.base.BaseController;
import jakarta.ws.rs.Path;

/**
 * rsa公钥私钥对服务
 *
 * @author lei.liu
 * @since 2025-06-09 19:57
 */
@Path("/rsaKeyPair")
public class RsaKeyPairController
    extends BaseController<
    Long,
    RsaKeyPairPersist,
    RsaKeyPairService
    > {

}
