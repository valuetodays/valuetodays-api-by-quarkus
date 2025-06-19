package cn.valuetodays.api2.extra.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.valuetodays.api2.extra.MyLinkConstant;
import cn.valuetodays.api2.extra.dao.MyLinkDAO;
import cn.valuetodays.api2.extra.persist.MyLinkPO;
import cn.valuetodays.api2.extra.vo.MyLinkTreeVo;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import cn.valuetodays.quarkus.commons.base.Operator;
import cn.valuetodays.quarkus.commons.base.QuerySearch;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.collections4.CollectionUtils;

/**
 * 我的链接
 *
 * @author lei.liu
 * @since 2020-09-07 17:56
 */
@ApplicationScoped
public class MyLinkServiceImpl
    extends BaseCrudService<Long, MyLinkPO, MyLinkDAO> {

    public static QuerySearch eqUserId(Long userId) {
        return eq("userId", userId);
    }

    public static QuerySearch eq(String fieldName, Object fieldValue) {
        String fieldValue0 = Objects.nonNull(fieldValue) ? Objects.toString(fieldValue) : null;
        return QuerySearch.of(fieldName, fieldValue0, Operator.EQ);
    }

    public MyLinkTreeVo listTreeByUserId(Long currentUserId) {
        List<QuerySearch> qsList = new ArrayList<>();
        qsList.add(eqUserId(currentUserId));
        qsList.add(eq("useStatus", MyLinkConstant.USE_STATUS_YES));
        final List<MyLinkPO> myLinkPOList = listBy(qsList);
        List<MyLinkPO> rootList = myLinkPOList.stream()
            .filter(e -> MyLinkConstant.ROOT_PARENT_ID.equals(e.getParentId()))
            .toList();
        MyLinkTreeVo treeVo = new MyLinkTreeVo();
        fillTree(rootList, myLinkPOList, treeVo);
        return treeVo;
    }

    private void fillTree(List<MyLinkPO> rootList, List<MyLinkPO> myLinkPOList, MyLinkTreeVo treeVo) {
        for (MyLinkPO myLinkPO : rootList) {
            Long id = myLinkPO.getId();
            List<MyLinkPO> children = myLinkPOList.stream()
                .filter(e -> e.getParentId().equals(id))
                .toList();
            MyLinkTreeVo.Item item = new MyLinkTreeVo.Item();
            item.setId(id);
            item.setParentId(myLinkPO.getParentId());
            item.setUrl(myLinkPO.getUrl());
            item.setTitle(myLinkPO.getTitle());
            item.setFavIcon(myLinkPO.getFavIcon());
            item.setChildren(myLinkListToItemList(children));
            treeVo.getItemList().add(item);
        }
    }

    private List<MyLinkTreeVo.Item> myLinkListToItemList(List<MyLinkPO> children) {
        if (CollectionUtils.isEmpty(children)) {
            return null;
        }
        return children.stream().map(e -> {
            MyLinkTreeVo.Item item = new MyLinkTreeVo.Item();
            item.setId(e.getId());
            item.setUrl(e.getUrl());
            item.setTitle(e.getTitle());
            item.setFavIcon(e.getFavIcon());
            return item;
        }).toList();
    }

    public List<MyLinkPO> findAllByParentId(Long parentId) {
        return getRepository().findAllByParentId(parentId);
    }
}
