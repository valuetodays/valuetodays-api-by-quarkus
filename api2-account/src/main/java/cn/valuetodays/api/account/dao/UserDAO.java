package cn.valuetodays.api.account.dao;

import cn.valuetodays.api.account.persist.UserPO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lei.liu
 * @since 2019-10-22 16:23
 */
@ApplicationScoped
public class UserDAO implements PanacheRepository<UserPO> {

    public UserPO findByMobile(String mobile) {
        return find("mobile = ?1", mobile).firstResult();
    }

    public UserPO findByEmail(String email) {
        return find("email = ?1", email).firstResult();
    }

    public UserPO findByAccount(String account) {
        if (StringUtils.contains(account, "@")) {
            return findByEmail(account);
        } else {
            return findByMobile(account);
        }
    }

//    @Query("select u from UserPO u where bitand(siteScope, ?1) = ?1 ")
//    List<UserPO> findAllByScope(long scope);

    public UserPO findByWxOpenid(String openid) {
        return find("wxOpenid = ?1", openid).firstResult();
    }

}
