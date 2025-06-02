package cn.valuetodays.api2.web.repository;


import cn.valuetodays.api2.client.enums.WxmpArticleImageEnums;
import cn.valuetodays.api2.client.persist.WxmpArticleImagePersist;
import cn.valuetodays.quarkus.commons.base.BaseJpaRepository;
import cn.valuetodays.quarkus.commons.base.QuerySearch;
import cn.valuetodays.quarkus.commons.base.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;


/**
 * @author lei.liu
 * @since 2025-04-08 00:08
 */
@ApplicationScoped
public class WxmpArticleImageDAO extends BaseJpaRepository<WxmpArticleImagePersist, Long> {

    protected WxmpArticleImageDAO() {
        super(WxmpArticleImagePersist.class);
    }

    public List<WxmpArticleImagePersist> findTop6ByStatusOrderByIdDesc(WxmpArticleImageEnums.Status status) {
        List<QuerySearch> qs = List.of(QuerySearch.eq("status", status.name()));
        return findAll(qs, new Sort[]{Sort.ofDesc("id")}, 6);
//        return find("status = ?1", Sort.descending("id"), status).page(0, 6).list();
    }

}
