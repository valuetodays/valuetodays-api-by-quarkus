package cn.valuetodays.api2.basic.dao;

import cn.valuetodays.api2.basic.persist.DictItemPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lei.liu
 * @since 2024-11-13 20:40
 */
public interface DictItemDAO extends JpaRepository<DictItemPO, Long> {

    List<DictItemPO> findAllByTypeId(Long typeId);
}
