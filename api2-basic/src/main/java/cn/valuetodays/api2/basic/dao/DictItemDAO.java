package cn.valuetodays.api2.basic.dao;

import cn.valuetodays.api2.basic.persist.DictItemPO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * @author lei.liu
 * @since 2024-11-13 20:40
 */
@ApplicationScoped
public class DictItemDAO implements PanacheRepository<DictItemPO> {

    public List<DictItemPO> findAllByTypeId(Long typeId) {
        return find("typeId =?1", typeId).list();
    }
}
