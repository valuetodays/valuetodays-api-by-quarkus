package cn.valuetodays.api2.basic.dao;

import cn.valuetodays.api2.basic.persist.DictItemPO;
import cn.valuetodays.quarkus.commons.base.BaseJpaRepository;
import cn.valuetodays.quarkus.commons.base.QuerySearch;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * @author lei.liu
 * @since 2024-11-13 20:40
 */
@ApplicationScoped
public class DictItemDAO extends BaseJpaRepository<DictItemPO, Long> {

    protected DictItemDAO() {
        super(DictItemPO.class);
    }

    public List<DictItemPO> findAllByTypeId(Long typeId) {
        return super.findAll(List.of(QuerySearch.eq("typeId", String.valueOf(typeId))));
    }
}
