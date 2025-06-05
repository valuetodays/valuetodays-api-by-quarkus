package cn.valuetodays.module.spider.dao;

import cn.valuetodays.module.spider.client.persist.WxmpArticlePO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-08-03
 */
@ApplicationScoped
public class WxmpArticleDAO implements PanacheRepository<WxmpArticlePO> {

    public WxmpArticlePO findByMid(String mid) {
        return find("mid = ?1", mid).firstResult();
    }
}
