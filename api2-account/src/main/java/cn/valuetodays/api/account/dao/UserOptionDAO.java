package cn.valuetodays.api.account.dao;


import cn.valuetodays.api.account.persist.UserOptionPO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2019-10-22 16:23
 */
@ApplicationScoped
public class UserOptionDAO implements PanacheRepository<UserOptionPO> {
}
