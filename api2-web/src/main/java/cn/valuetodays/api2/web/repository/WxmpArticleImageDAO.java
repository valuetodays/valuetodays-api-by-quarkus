package cn.valuetodays.api2.web.repository;


import cn.valuetodays.api2.client.enums.WxmpArticleImageEnums;
import cn.valuetodays.api2.client.persist.WxmpArticleImagePersist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lei.liu
 * @since 2025-04-08 00:08
 */
public interface WxmpArticleImageDAO extends JpaRepository<WxmpArticleImagePersist, Long> {

    List<WxmpArticleImagePersist> findTop6ByStatusOrderByIdDesc(WxmpArticleImageEnums.Status status);

}
