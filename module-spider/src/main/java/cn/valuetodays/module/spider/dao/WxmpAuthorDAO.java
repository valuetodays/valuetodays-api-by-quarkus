package cn.valuetodays.module.spider.dao;

import cn.valuetodays.module.spider.client.persist.WxmpAuthorPO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-08-03
 */
@ApplicationScoped
public class WxmpAuthorDAO implements PanacheRepository<WxmpAuthorPO> {

    public WxmpAuthorPO findByWxAuthorId(String wxAuthorId) {
        return find(" wxAuthorId = ?1", wxAuthorId).firstResult();
    }
}
