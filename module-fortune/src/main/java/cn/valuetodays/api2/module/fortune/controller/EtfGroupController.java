package cn.valuetodays.api2.module.fortune.controller;

import java.util.List;
import java.util.Objects;

import cn.valuetodays.api2.module.fortune.persist.EtfGroupPO;
import cn.valuetodays.api2.module.fortune.reqresp.AllEtfsCompareReq;
import cn.valuetodays.api2.module.fortune.reqresp.EtfGroupAndDetailVo;
import cn.valuetodays.api2.module.fortune.reqresp.EtfsCompareReq;
import cn.valuetodays.api2.module.fortune.reqresp.EtfsCompareResp;
import cn.valuetodays.api2.module.fortune.service.EtfGroupServiceImpl;
import cn.valuetodays.quarkus.commons.base.BaseCrudController;
import cn.vt.R;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-05-02
 */
@Path("/etfGroup")
public class EtfGroupController extends BaseCrudController<
    Long, EtfGroupPO, EtfGroupServiceImpl> {

//    @Inject
//    private StockRealtimeQuoteAdvanceScenarioComponent stockRealtimeQuoteAdvanceScenarioComponent;


    @Path("/realtimeBatchCompare")
    @POST
    public R<List<EtfsCompareResp>> realtimeBatchCompare(@RequestBody AllEtfsCompareReq req) {
        int minMoneyPerTrade = req.getMinMoneyPerTrade();
        if (minMoneyPerTrade <= 0) {
            minMoneyPerTrade = EtfsCompareReq.DEFAULT_MIN_MONEY_PER_TRADE;
        }
        final int finalMinMoneyPerTrade = minMoneyPerTrade;
        List<EtfsCompareReq> reqs = req.getReqs();
        List<EtfsCompareReq> reqsToUse = reqs;
        if (CollectionUtils.isEmpty(reqs)) {
            List<EtfGroupAndDetailVo> list;
            int tn = req.getTn();
            if (tn < 0) {
                list = service.getAllEtfGroupAndDetail();
            } else {
                list = service.getAllTnEtfGroupAndDetail(tn);
            }
            reqsToUse = list.stream().map(e -> {
                EtfsCompareReq ecr = new EtfsCompareReq();
                ecr.setMinMoneyPerTrade(finalMinMoneyPerTrade);
                ecr.setGroup(e.getName());
                ecr.setTn(e.getTn());
                ecr.setEtfs(e.getCodes());
                return ecr;
            }).toList();
        }

        return R.success(
            reqsToUse.stream()
                .map(this::realtimeCompare)
                .filter(Objects::nonNull)
                .map(R::getData)
                .toList()
        );
    }

    @Path("/realtimeCompare")
    @POST
    public R<EtfsCompareResp> realtimeCompare(@RequestBody EtfsCompareReq req) {
        // 每次交易的最小金额
        int minMoneyPerTrade = req.getMinMoneyPerTrade();
        if (minMoneyPerTrade <= 1200) {
            minMoneyPerTrade = EtfsCompareReq.DEFAULT_MIN_MONEY_PER_TRADE;
        }

//        return stockRealtimeQuoteAdvanceScenarioComponent.processCompare(
//            req.getGroup(), req.getTn(), req.getEtfs(), minMoneyPerTrade
//        );
        return null;
    }

}
