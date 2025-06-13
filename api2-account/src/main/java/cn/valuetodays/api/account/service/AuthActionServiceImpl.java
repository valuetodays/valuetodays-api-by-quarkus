package cn.valuetodays.api.account.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.valuetodays.api.account.dao.AuthActionDAO;
import cn.valuetodays.api.account.dao.AuthRoleActionLinkDAO;
import cn.valuetodays.api.account.enums.AuthMenuEnums;
import cn.valuetodays.api.account.persist.AuthActionPO;
import cn.valuetodays.api.account.persist.AuthMenuPO;
import cn.valuetodays.api.account.persist.AuthRoleActionLinkPO;
import cn.valuetodays.api.account.reqresp.AuthActionGroupModel;
import cn.valuetodays.api.account.reqresp.AuthActionTreeVoWithAuthMenu;
import cn.valuetodays.api.account.reqresp.MenuLocationResp;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 动作
 *
 * @author lei.liu
 * @since 2020-09-24 10:14
 */
@ApplicationScoped
public class AuthActionServiceImpl
    extends BaseCrudService<Long, AuthActionPO, AuthActionDAO> {

    @Inject
    AuthRoleActionLinkDAO authRoleActionLinkDAO;
    @Inject
    AuthMenuServiceImpl authMenuService;

    public List<AuthActionGroupModel> groupByMenuId() {
        List<AuthActionPO> list = list();
        Map<String, List<AuthActionPO>> groupBy = list.stream()
            .collect(Collectors.groupingBy(AuthActionPO::getMenuName));
        return groupBy.entrySet().stream().map(e -> {
            String key = e.getKey();
            List<AuthActionPO> value = e.getValue();
            AuthActionGroupModel model = new AuthActionGroupModel();
            model.setMenuName(key);
            model.setActionList(value.stream().sorted(Comparator.comparingInt(AuthActionPO::getOrderNum)).map(e1 -> {
                AuthActionGroupModel.Action action = new AuthActionGroupModel.Action();
                action.setId(e1.getId());
                action.setName(e1.getName());
                action.setDependsString(
                    StringUtils.trimToEmpty(StringUtils.join(e1.getDepends(), ","))
                );
                return action;
            }).collect(Collectors.toList()));
            return model;
        }).collect(Collectors.toList());
    }

    public List<AuthActionPO> listByRoleId(Long roleId) {
        List<AuthRoleActionLinkPO> linkList = authRoleActionLinkDAO.findAllByRoleId(roleId);
        if (CollectionUtils.isEmpty(linkList)) {
            return new ArrayList<>();
        }
        return this.listByIds(linkList.stream().map(AuthRoleActionLinkPO::getActionId).collect(Collectors.toList()));
    }

    public AuthActionTreeVoWithAuthMenu treeWithAuthMenu() {
        AuthActionTreeVoWithAuthMenu authActionCascade = new AuthActionTreeVoWithAuthMenu();

        List<AuthActionPO> authActionPOList = list();

        MenuLocationResp menuLocation = authMenuService.listTree(AuthMenuEnums.Product.ADMIN, 1L, false);
        Map<String, List<AuthMenuPO>> menus = menuLocation.getMenus();
        for (Map.Entry<String, List<AuthMenuPO>> stringListEntry : menus.entrySet()) {
            List<AuthMenuPO> values = stringListEntry.getValue();

            for (AuthMenuPO value : values) {
                AuthActionTreeVoWithAuthMenu.AuthMenu1 authMenu1
                    = AuthActionTreeVoWithAuthMenu.newAuthMenu1();
                authMenu1.setId(value.getId());
                authMenu1.setName(value.getName());
                authMenu1.setChildren(new ArrayList<>());

                List<AuthMenuPO> children = value.getChildren();
                if (CollectionUtils.isNotEmpty(children)) {
                    for (AuthMenuPO child : children) {
                        AuthActionTreeVoWithAuthMenu.AuthMenu2 authMenu2
                            = AuthActionTreeVoWithAuthMenu.newAuthMenu2();
                        authMenu2.setId(child.getId());
                        authMenu2.setName(child.getName());
                        authMenu2.setActionList(new ArrayList<>());
                        authActionPOList.stream()
                            .filter(e -> e.getMenuId().equals(child.getId()))
                            .forEach(e -> authMenu2.getActionList().add(e));
                        authMenu1.getChildren().add(authMenu2);
                    }
                }

                authActionCascade.getMenuAndActionList().add(authMenu1);
            }
        }

        return authActionCascade;
    }

}
