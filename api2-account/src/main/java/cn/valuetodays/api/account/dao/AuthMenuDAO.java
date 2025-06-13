package cn.valuetodays.api.account.dao;

import java.util.List;

import cn.valuetodays.api.account.enums.AuthMenuEnums;
import cn.valuetodays.api.account.persist.AuthMenuPO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;


/**
 *
 */
@ApplicationScoped
public class AuthMenuDAO implements PanacheRepository<AuthMenuPO> {

    //    @Query("select a from AuthMenuPO a where status='NORMAL' and a.parentId=?1 order by a.orderNum asc")
    public List<AuthMenuPO> listAllByParentId(Long parentId) {
        return find("status='NORMAL' and a.parentId=?1", Sort.ascending("orderNum"), parentId).list();
    }

    //    @Query(" select preMenu from AuthMenuPO preMenu "
//            + " where preMenu.status='NORMAL' "
//            + " and preMenu.parentId=?2 and preMenu.orderNum < ?3 and preMenu.id <> ?1 "
//            + " order by preMenu.orderNum desc"
//    )
    public List<AuthMenuPO> findPreviousMenu(long id, long parentId, int orderNum) {
        return find("status='NORMAL' and parentId = ?2 and orderNum < ?3 and id <> ?1",
            Sort.descending("orderNum"),
            id, parentId, orderNum
        ).list();
    }

    public List<AuthMenuPO> findAllByProduct(AuthMenuEnums.Product product) {
        return find("product = ?1", product).list();
    }
}
