package cn.valuetodays.api2.extra.dao;

import java.util.List;

import cn.valuetodays.api2.extra.persist.MyLinkPO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


/**
 * @author lei.liu
 * @since 2020-09-07 17:56
 */
@ApplicationScoped
public class MyLinkDAO implements PanacheRepository<MyLinkPO> {

    public List<MyLinkPO> findAllByParentId(Long parentId) {
        return find("parentId = ?1", parentId).list();
    }
}
