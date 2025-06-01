package cn.valuetodays.api2.web.repository;


import cn.valuetodays.api2.client.enums.WxmpArticleImageEnums;
import cn.valuetodays.api2.client.persist.WxmpArticleImagePersist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * @author lei.liu
 * @since 2025-04-08 00:08
 */
@ApplicationScoped
public class WxmpArticleImageDAO implements PanacheRepository<WxmpArticleImagePersist> {

    public List<WxmpArticleImagePersist> findTop6ByStatusOrderByIdDesc(WxmpArticleImageEnums.Status status) {
        return null; //todo
    }

}
