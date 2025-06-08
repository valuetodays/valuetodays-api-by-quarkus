package cn.valuetodays.api.account.dao;


import cn.valuetodays.api.account.persist.UserBrowserFingerprintPersist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * @author lei.liu
 * @since 2024-12-04 18:38
 */
@ApplicationScoped
public class UserBrowserFingerprintDAO implements PanacheRepository<UserBrowserFingerprintPersist> {

    public UserBrowserFingerprintPersist findByFingerprint(String browserFingerprint) {
        return find("fingerprint = ?1", browserFingerprint).firstResult();
    }

    public List<UserBrowserFingerprintPersist> findTop20ByAccountIdOrderByIdDesc(Long currentAccountId) {
        return find("accountId= ?1", Sort.descending("id"), currentAccountId).page(0, 20).list();
    }
}
