package cn.valuetodays.api.account.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import cn.valuetodays.api.account.dao.AuthMenuDAO;
import cn.valuetodays.api.account.dao.UserRoleDAO;
import cn.valuetodays.api.account.enums.AuthMenuEnums;
import cn.valuetodays.api.account.persist.AuthActionPO;
import cn.valuetodays.api.account.persist.AuthMenuPO;
import cn.valuetodays.api.account.persist.AuthRolePO;
import cn.valuetodays.api.account.persist.UserRolePO;
import cn.valuetodays.api.account.reqresp.MenuLocationResp;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import cn.valuetodays.quarkus.commons.base.jpa.JpaIdBasePersist;
import cn.vt.exception.AssertUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lei.liu
 * @since 2018-09-07 10:40:04
 */
@ApplicationScoped
public class AuthMenuServiceImpl
    extends BaseCrudService<Long, AuthMenuPO, AuthMenuDAO> {

    @Inject
    UserRoleDAO userRoleDAO;
    @Inject
    AuthRoleServiceImpl authRoleService;
    @Inject
    AuthActionServiceImpl authActionService;

    @Transactional
    public AuthMenuPO moveMenuUp(long menuId) {
        AuthMenuPO menu = getRepository().findById(menuId);
        AssertUtils.assertNotNull(menu, "菜单不存在：" + menuId);

        List<AuthMenuPO> preMenus =
            getRepository().findPreviousMenu(
                menu.getId(), menu.getParentId(),
                menu.getOrderNum()
            );

        if (CollectionUtils.isNotEmpty(preMenus)) {
            AuthMenuPO preMenu = preMenus.get(0);
            int orderNumPre = preMenu.getOrderNum();
            int orderNumCur = menu.getOrderNum();
            menu.setOrderNum(orderNumPre);
            preMenu.setOrderNum(orderNumCur);
            getRepository().persist(menu);
            getRepository().persist(preMenu);
            return menu;
        } else {
            AssertUtils.fail("无需移动");
        }

        return null;
    }

    @Transactional
    public AuthMenuPO appendLevel1(AuthMenuPO menu) {
        // 一级菜单的parentId为0
        menu.setParentId(0L);
        return saveWithLevelAndOrderNum(menu);
    }

    public AuthMenuPO appendLevel2(AuthMenuPO menu) {
        return saveWithLevelAndOrderNum(menu);
    }

    public AuthMenuPO appendLevel3(AuthMenuPO menu) {
        return saveWithLevelAndOrderNum(menu);
    }

    private AuthMenuPO saveWithLevelAndOrderNum(AuthMenuPO menu) {
        long parentId = menu.getParentId();
        List<AuthMenuPO> brotherMenus = getRepository().listAllByParentId(parentId);
        if (CollectionUtils.isEmpty(brotherMenus)) {
            menu.setOrderNum(1);
        } else {
            menu.setOrderNum(brotherMenus.get(brotherMenus.size() - 1).getOrderNum() + 10);
        }
        menu.setStatus(AuthMenuEnums.Status.NORMAL);
        getRepository().persist(menu);
        return menu;
    }


    @Transactional
    public AuthMenuPO update(AuthMenuPO menu) {
        Long id = menu.getId();
        AuthMenuPO old = getRepository().findById(id);

        if (null != menu.getProduct()) {
            old.setProduct(menu.getProduct());
        }
        if (StringUtils.isNotEmpty(menu.getIcon())) {
            old.setIcon(menu.getIcon());
        }
        if (StringUtils.isNotEmpty(menu.getName())) {
            old.setName(menu.getName());
        }
        if (menu.getOrderNum() != 0) {
            old.setOrderNum(menu.getOrderNum());
        }
        if (menu.getStatus() != null) {
            old.setStatus(menu.getStatus());
        }
        if (menu.getParentId() != -1) {
            old.setParentId(menu.getParentId());
        }
        if (StringUtils.isNotBlank(menu.getAppCode())) {
            old.setAppCode(menu.getAppCode());
        }
        if (StringUtils.isNotBlank(menu.getRoute())) {
            old.setRoute(menu.getRoute());
        }

        getRepository().persist(old);
        return old;
    }

    private List<AuthMenuPO> fillDomain(List<AuthMenuPO> menuList, String channel) {
        return menuList;
    }

    public List<AuthMenuPO> listAllByParentId(Long parentId, String channel) {
        return fillDomain(getRepository().listAllByParentId(parentId), channel);
    }

    public MenuLocationResp listTree(AuthMenuEnums.Product product,
                                     Long userId,
                                     boolean onlyQueryNormalStatus) {
        Long adminRoleId = null;
        if (Long.valueOf(1L).equals(userId)) {
            adminRoleId = -1L;
        } else {
            UserRolePO userRolePO = userRoleDAO.findByUserId(userId);
            AssertUtils.assertNotNull(userRolePO, "用户还没有角色，请联系管理员");
            adminRoleId = userRolePO.getAdminRoleId();
        }
        final List<AuthMenuPO> allMenuList = getRepository().findAllByProduct(product);
        List<AuthMenuPO> allNormalMenuList = allMenuList.stream()
            .filter(e -> !onlyQueryNormalStatus || e.getStatus() == AuthMenuEnums.Status.NORMAL)
            .sorted(Comparator.comparingInt(AuthMenuPO::getOrderNum))
            .collect(Collectors.toList());
        final List<Long> allMenuIdListInRole = obtainMenuIdListForRoleId(product, adminRoleId, allNormalMenuList);

        Map<AuthMenuEnums.Location, List<AuthMenuPO>> menuSplitByLocation = allNormalMenuList.stream()
            .filter(e -> allMenuIdListInRole.contains(e.getId()))
            .collect(Collectors.groupingBy(AuthMenuPO::getLocation));
        MenuLocationResp menuLocationVo = new MenuLocationResp();
        Set<Map.Entry<AuthMenuEnums.Location, List<AuthMenuPO>>> entries = menuSplitByLocation.entrySet();
        for (Map.Entry<AuthMenuEnums.Location, List<AuthMenuPO>> entry : entries) {
            AuthMenuEnums.Location key = entry.getKey();
            List<AuthMenuPO> menuList = entry.getValue();
            menuLocationVo.getMenus().put(key.name().toLowerCase(), cascadeAsTree(product, menuList));
        }
        return menuLocationVo;
    }

    private List<Long> obtainMenuIdListForRoleId(AuthMenuEnums.Product product,
                                                 Long roleId, List<AuthMenuPO> allNormalMenuList) {
        if (-1 == roleId && AuthMenuEnums.Product.ADMIN == product) {
            return allNormalMenuList.stream().map(JpaIdBasePersist::getId).collect(Collectors.toList());
        }
        AuthRolePO authRolePO = authRoleService.findById(roleId);
        AssertUtils.assertNotNull(authRolePO, "用户绑定的角色不存在，请联系管理员");
        List<AuthActionPO> actionList = authActionService.listByRoleId(roleId);
        final List<Long> menuIdListInRole = actionList.stream()
            .map(AuthActionPO::getMenuId).distinct()
            .collect(Collectors.toList()); // 二级菜单的id
        AssertUtils.assertCollectionNotEmpty(menuIdListInRole, "用户绑定的角色没有分配菜单，请联系管理员");

        Map<Long, AuthMenuPO> menuIdAndObjectMap = allNormalMenuList.stream()
            .collect(Collectors.toMap(JpaIdBasePersist::getId, e -> e));
        // 找出二级菜单的父菜单id
        List<Long> level1MenuIdList = menuIdListInRole.stream()
            .map(menuIdAndObjectMap::get)
            .filter(Objects::nonNull)
            .map(AuthMenuPO::getParentId)
            .collect(Collectors.toList());

        final List<Long> allMenuIdListInRole = new ArrayList<>(512);
        allMenuIdListInRole.addAll(menuIdListInRole);
        allMenuIdListInRole.addAll(level1MenuIdList);

        return allMenuIdListInRole;
    }

    private List<AuthMenuPO> cascadeAsTree(AuthMenuEnums.Product product, final List<AuthMenuPO> allMenuList) {
        List<AuthMenuPO> filteredVoListByParentId = allMenuList.stream()
            .filter(e -> e.getParentId() == (product == AuthMenuEnums.Product.ADMIN ? 1 : 0))
            .collect(Collectors.toList());

        cascade(allMenuList, filteredVoListByParentId);

        return new ArrayList<>(filteredVoListByParentId);
    }

    private void cascade(final List<AuthMenuPO> allAuthMenuVoList, List<AuthMenuPO> filteredVoListByParentId) {
        for (AuthMenuPO authMenuVo : filteredVoListByParentId) {
            final long id = authMenuVo.getId();
            List<AuthMenuPO> children = allAuthMenuVoList.stream()
                .filter(e -> id == e.getParentId())
                .sorted(Comparator.comparingLong(AuthMenuPO::getOrderNum))
                .collect(Collectors.toList());
            cascade(allAuthMenuVoList, children);
            if (CollectionUtils.isNotEmpty(children)) {
                authMenuVo.setChildren(children);
            } else {
                authMenuVo.setChildren(null);
            }
        }
    }

    public List<AuthMenuPO> listAllCascadeByParentId(Long parentId) {
        List<AuthMenuPO> menuLevel0List = listAllByParentId(parentId, null);
        for (AuthMenuPO menuLevel1 : menuLevel0List) {
            List<AuthMenuPO> menuLevel2List = listAllByParentId(menuLevel1.getId(), null);
            for (AuthMenuPO menuLevel3 : menuLevel2List) {
                List<AuthMenuPO> menuLevel4List = listAllByParentId(menuLevel3.getId(), null);
                menuLevel3.setChildren(menuLevel4List);
            }
            menuLevel1.setChildren(menuLevel2List);
        }
        return menuLevel0List;
    }

    public List<AuthMenuPO> findByIds(List<Long> ids, String channel) {
        return fillDomain(ids.stream()
                .map(e -> getRepository().findById(e))
                .collect(Collectors.toList()),
            channel
        );
    }

}
