package cn.valuetodays.api2.basic.dao;

import cn.valuetodays.api2.basic.persist.DictTypePO;
import cn.valuetodays.quarkus.commons.base.BaseJpaRepository;
import cn.valuetodays.quarkus.commons.base.QuerySearch;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * @author lei.liu
 * @since 2024-11-13 20:40
 */
@ApplicationScoped
public class DictTypeDAO extends BaseJpaRepository<DictTypePO, Long> {

    protected DictTypeDAO() {
        super(DictTypePO.class);
    }

    public DictTypePO findByCode(String code) {
        return super.findOne(List.of(QuerySearch.eq("code", code)));
    }
}
