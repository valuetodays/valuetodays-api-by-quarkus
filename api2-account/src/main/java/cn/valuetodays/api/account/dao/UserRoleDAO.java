package cn.valuetodays.api.account.dao;


import cn.valuetodays.api.account.persist.UserRolePO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * @author lei.liu
 * @since 2020-09-29 11:06
 */
@ApplicationScoped
public class UserRoleDAO implements PanacheRepository<UserRolePO> {

    public UserRolePO findByUserId(Long userId) {
        return find("userId = ?1", userId).firstResult();
    }

    public List<UserRolePO> findAllByUserIdIn(List<Long> userIdList) {
        return find("userId in ?1", userIdList).list();
    }
}
