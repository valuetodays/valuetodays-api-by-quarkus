package cn.valuetodays.api2.extra.dao;

import cn.valuetodays.api2.extra.persist.WeworkGroupUserPersist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-26
 */
public interface WeworkGroupUserDAO extends JpaRepository<WeworkGroupUserPersist, Long> {

    List<WeworkGroupUserPersist> findByGroupIdIn(List<Long> ids);

    WeworkGroupUserPersist findTop1ByEmailOrderByCreateTimeDesc(String email);

    WeworkGroupUserPersist findTop1ByEmailOrderByCreateTimeAsc(String email);
}
