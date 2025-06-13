package cn.valuetodays.api.account.dao;

import java.util.List;

import cn.valuetodays.api.account.persist.AuthRoleActionLinkPO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

/**
 * @author lei.liu
 * @since 2020-09-24 10:14
 */
@ApplicationScoped
public class AuthRoleActionLinkDAO implements PanacheRepository<AuthRoleActionLinkPO> {

    @Transactional
    public long deleteByRoleId(Long roleId) {
        return delete("where roleId = ?1", roleId);
    }

    public List<AuthRoleActionLinkPO> findAllByRoleId(Long roleId) {
        return find("roleId = ?1", roleId).list();
    }
}
