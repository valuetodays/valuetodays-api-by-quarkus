package cn.valuetodays.api2.extra.controller;

import cn.valuetodays.api2.extra.persist.WeworkGroupBatchPersist;
import cn.valuetodays.api2.extra.reqresp.SaveGroupAndMemberResp;
import cn.valuetodays.api2.extra.reqresp.WeworkDiffGroupReq;
import cn.valuetodays.api2.extra.reqresp.WeworkDiffGroupResp;
import cn.valuetodays.api2.extra.reqresp.WeworkGroupAndMemberSaveReq;
import cn.valuetodays.api2.extra.service.WeworkGroupBatchServiceImpl;
import cn.valuetodays.quarkus.commons.base.BaseController;
import cn.vt.R;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 * 企业微信群组记录服务
 *
 * @author lei.liu
 * @since 2023-06-20 19:45
 */
@RequestScoped
@Path("/extra/weworkGroupBatch")
public class WeworkGroupBatchController
    extends BaseController<
    Long,
    WeworkGroupBatchPersist,
    WeworkGroupBatchServiceImpl
    > {


    @Path("/diffUser")
    @POST
    public R<WeworkDiffGroupResp> diffUser(WeworkDiffGroupReq req) {
        return R.success(service.diffUser(req.getGroupId1(), req.getGroupId2()));
    }

    @Path("/saveGroupAndMember")
    @POST
    public R<SaveGroupAndMemberResp> saveGroupAndMember(WeworkGroupAndMemberSaveReq req) {
        return R.success(service.saveGroupAndMember(req));
    }
}
