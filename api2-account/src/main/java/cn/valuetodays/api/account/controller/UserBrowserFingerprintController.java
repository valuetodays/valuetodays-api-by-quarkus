package cn.valuetodays.api.account.controller;

import java.util.List;

import cn.valuetodays.api.account.persist.UserBrowserFingerprintPersist;
import cn.valuetodays.api.account.service.UserBrowserFingerprintServiceImpl;
import cn.valuetodays.quarkus.commons.base.BaseCrudController;
import cn.vt.R;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.extern.slf4j.Slf4j;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-12-05
 */
@Path("/userBrowserFingerprint")
@Slf4j
public class UserBrowserFingerprintController
    extends BaseCrudController<Long,
        UserBrowserFingerprintPersist,
        UserBrowserFingerprintServiceImpl> {

    @Path("/listByCurrentAccount")
    @POST
    public R<List<UserBrowserFingerprintPersist>> listByCurrentAccount() {
        Long currentAccountId = getCurrentAccountId();
        return R.success(service.findTop20ByAccountId(currentAccountId));
    }

}
