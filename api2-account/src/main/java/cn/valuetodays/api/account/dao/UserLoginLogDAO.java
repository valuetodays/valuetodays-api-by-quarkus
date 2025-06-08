package cn.valuetodays.api.account.dao;


import cn.valuetodays.api.account.persist.UserLoginLogPO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2020-07-22 13:32
 */
@ApplicationScoped
public class UserLoginLogDAO implements PanacheRepository<UserLoginLogPO> {

    public UserLoginLogPO findTopByCreateUserIdOrderByCreateTimeDesc(Long userId) {
        return find("createUserId = ?1", Sort.descending("createTime"), userId).firstResult();
    }
}
