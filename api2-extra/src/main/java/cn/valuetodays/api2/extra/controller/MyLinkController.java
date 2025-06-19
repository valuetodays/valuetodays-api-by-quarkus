package cn.valuetodays.api2.extra.controller;

import java.util.List;
import java.util.Objects;

import cn.valuetodays.api2.extra.MyLinkConstant;
import cn.valuetodays.api2.extra.persist.MyLinkPO;
import cn.valuetodays.api2.extra.service.MyLinkServiceImpl;
import cn.valuetodays.api2.extra.vo.MyLinkTreeVo;
import cn.valuetodays.api2.web.common.UserIdVo;
import cn.valuetodays.quarkus.commons.base.BaseCrudController;
import cn.vt.R;
import cn.vt.exception.AssertUtils;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;


/**
 * 我的链接服务
 *
 * @author lei.liu
 * @since 2020-09-07 17:56
 */
@Path("/myLink")
public class MyLinkController
    extends BaseCrudController<Long, MyLinkPO, MyLinkServiceImpl> {

    @Override
    protected void beforeSave(MyLinkPO myLinkPO) {
        super.beforeSave(myLinkPO);
        Long currentAccountId = getCurrentAccountId();
        Long parentId = myLinkPO.getParentId();
        if (!Objects.equals(MyLinkConstant.ROOT_PARENT_ID, parentId)) {
            MyLinkPO parent = service.findById(parentId);
            Long userId = parent.getUserId();
            AssertUtils.assertTrue(Objects.equals(userId, currentAccountId), "不能使用别人的数据");
            myLinkPO.setLevel(parent.getLevel() + 1);
        } else {
            myLinkPO.setLevel(1);
        }

        myLinkPO.setUserId(currentAccountId);
        myLinkPO.setCreateUserId(currentAccountId);
        myLinkPO.setUpdateUserId(currentAccountId);
    }

    @Path("/listCascade")
    @POST
    public R<MyLinkTreeVo> listCascade() {
        return R.success(service.listTreeByUserId(getCurrentAccountId()));
    }

    @Override
    protected void beforeDelete(MyLinkPO myLinkPO) {
        Long currentAccountId = getCurrentAccountId();
        Long userId = myLinkPO.getUserId();
        AssertUtils.assertTrue(Objects.equals(userId, currentAccountId), "不能删除别人的数据");
        List<MyLinkPO> children = service.findAllByParentId(myLinkPO.getId());
        AssertUtils.assertCollectionEmpty(children, "链接还有子链接");
    }

    @Path("/anon/listTreeByUserId")
    @POST
    public R<MyLinkTreeVo> listTreeByUserId(UserIdVo userIdVo) {
        return R.success(service.listTreeByUserId(userIdVo.getUserId()));
    }
}
