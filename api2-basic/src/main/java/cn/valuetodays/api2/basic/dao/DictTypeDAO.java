package cn.valuetodays.api2.basic.dao;

import cn.valuetodays.api2.basic.persist.DictTypePO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lei.liu
 * @since 2024-11-13 20:40
 */
public interface DictTypeDAO extends JpaRepository<DictTypePO, Long> {

    DictTypePO findByCode(String code);
}
