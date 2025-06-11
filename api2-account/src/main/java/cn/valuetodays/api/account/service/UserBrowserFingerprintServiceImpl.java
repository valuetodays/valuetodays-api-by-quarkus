package cn.valuetodays.api.account.service;

import cn.valuetodays.api.account.dao.UserBrowserFingerprintDAO;
import cn.valuetodays.api.account.persist.UserBrowserFingerprintPersist;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 用户-浏览器指纹表
 *
 * @author lei.liu
 * @since 2024-12-04 18:38
 */
@ApplicationScoped
@Slf4j
public class UserBrowserFingerprintServiceImpl
    extends BaseCrudService<Long, UserBrowserFingerprintPersist, UserBrowserFingerprintDAO> {

    public UserBrowserFingerprintPersist findByFingerprint(String browserFingerprint) {
        return getRepository().findByFingerprint(browserFingerprint);
    }

    public List<UserBrowserFingerprintPersist> findTop20ByAccountId(Long currentAccountId) {
        return getRepository().findTop20ByAccountIdOrderByIdDesc(currentAccountId);
    }

}
