package cn.valuetodays.api2.extra.dao;

import cn.valuetodays.api2.extra.persist.WeworkGroupUserPersist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-26
 */
@ApplicationScoped
public class WeworkGroupUserDAO implements PanacheRepository<WeworkGroupUserPersist> {

    public List<WeworkGroupUserPersist> findByGroupIdIn(List<Long> ids) {
        return list("groupId in ?1", ids);
    }

    public WeworkGroupUserPersist findTop1ByEmailOrderByCreateTimeDesc(String email) {
        return find("email=?1", Sort.descending("createTime"), email).page(Page.of(0, 1)).stream()
            .findFirst().orElse(null);
    }

    public WeworkGroupUserPersist findTop1ByEmailOrderByCreateTimeAsc(String email) {
        return find("email=?1", Sort.ascending("createTime"), email).page(Page.of(0, 1)).stream()
            .findFirst().orElse(null);
    }
}
