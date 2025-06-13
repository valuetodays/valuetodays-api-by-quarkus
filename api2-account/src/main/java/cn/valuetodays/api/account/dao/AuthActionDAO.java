package cn.valuetodays.api.account.dao;

import cn.valuetodays.api.account.persist.AuthActionPO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2020-09-24 10:14
 */
@ApplicationScoped
public class AuthActionDAO implements PanacheRepository<AuthActionPO> {


}
