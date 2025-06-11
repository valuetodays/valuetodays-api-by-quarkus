package cn.valuetodays.api.account.controller;

import cn.valuetodays.api.account.persist.UserBrowserFingerprintPersist;
import cn.valuetodays.api.account.service.UserBrowserFingerprintServiceImpl;
import cn.valuetodays.quarkus.commons.base.BaseCrudController;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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

    @Path("listByCurrentAccount.do")
    @POST
    public List<UserBrowserFingerprintPersist> listByCurrentAccount() {
        Long currentAccountId = getCurrentAccountId();
        return service.findTop20ByAccountId(currentAccountId);
    }

}
