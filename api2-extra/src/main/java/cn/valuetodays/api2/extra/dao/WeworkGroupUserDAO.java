package cn.valuetodays.api2.extra.dao;

import cn.valuetodays.api2.extra.persist.WeworkGroupUserPersist;
import cn.valuetodays.quarkus.commons.base.BaseJpaRepository;
import cn.valuetodays.quarkus.commons.base.Operator;
import cn.valuetodays.quarkus.commons.base.QuerySearch;
import cn.valuetodays.quarkus.commons.base.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-26
 */
@ApplicationScoped
public class WeworkGroupUserDAO extends BaseJpaRepository<WeworkGroupUserPersist, Long> {

    protected WeworkGroupUserDAO() {
        super(WeworkGroupUserPersist.class);
    }

    public List<WeworkGroupUserPersist> findByGroupIdIn(List<Long> ids) {
        return super.findAll(List.of(QuerySearch.of("groupId", StringUtils.join(ids, ","), Operator.IN)));
//        return list("groupId in ?1", ids);
    }

    public WeworkGroupUserPersist findTop1ByEmailOrderByCreateTimeDesc(String email) {
        List<QuerySearch> qs = List.of(QuerySearch.eq("email", email));
        return super.findOne(qs, new Sort[]{Sort.ofDesc("createTime")}, 1);
//        return find("email=?1", Sort.descending("createTime"), email).page(Page.of(0, 1)).stream()
//            .findFirst().orElse(null);
    }

    public WeworkGroupUserPersist findTop1ByEmailOrderByCreateTimeAsc(String email) {
        List<QuerySearch> qs = List.of(QuerySearch.eq("email", email));
        return super.findOne(qs, new Sort[]{Sort.ofAsc("createTime")}, 1);
//        return find("email=?1", Sort.ascending("createTime"), email).page(Page.of(0, 1)).stream()
//            .findFirst().orElse(null);
    }
}
