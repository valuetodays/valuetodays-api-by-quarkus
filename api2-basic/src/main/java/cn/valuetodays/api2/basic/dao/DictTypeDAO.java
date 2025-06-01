package cn.valuetodays.api2.basic.dao;

import cn.valuetodays.api2.basic.persist.DictTypePO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2024-11-13 20:40
 */
@ApplicationScoped
public class DictTypeDAO implements PanacheRepository<DictTypePO> {

    public DictTypePO findByCode(String code) {
        return find("code=?1", code).stream().findFirst().orElse(null);
    }
}
