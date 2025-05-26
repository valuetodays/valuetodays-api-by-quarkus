package cn.valuetodays.api2.extra.controller;

import cn.valuetodays.api2.extra.persist.WeworkGroupBatchPersist;
import cn.valuetodays.api2.extra.reqresp.SaveGroupAndMemberResp;
import cn.valuetodays.api2.extra.reqresp.WeworkDiffGroupReq;
import cn.valuetodays.api2.extra.reqresp.WeworkDiffGroupResp;
import cn.valuetodays.api2.extra.reqresp.WeworkGroupAndMemberSaveReq;
import cn.valuetodays.api2.extra.service.WeworkGroupBatchServiceImpl;
import cn.valuetodays.quarkus.commons.base.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 企业微信群组记录服务
 *
 * @author lei.liu
 * @since 2023-06-20 19:45
 */
@RestController
@RequestMapping("/extra/weworkGroupBatch")
public class WeworkGroupBatchController
    extends BaseController<
    Long,
    WeworkGroupBatchPersist,
    WeworkGroupBatchServiceImpl
    > {


    @PostMapping("/diffUser")
    public WeworkDiffGroupResp diffUser(@RequestBody WeworkDiffGroupReq req) {
        return service.diffUser(req.getGroupId1(), req.getGroupId2());
    }

    @PostMapping("/saveGroupAndMember")
    public SaveGroupAndMemberResp saveGroupAndMember(@RequestBody WeworkGroupAndMemberSaveReq req) {
        return service.saveGroupAndMember(req);
    }
}
