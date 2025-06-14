package cn.valuetodays.api2.module.fortune.controller;

import java.math.BigDecimal;

import cn.valuetodays.api2.module.fortune.service.StockTradeInterestServiceImpl;
import cn.valuetodays.api2.web.common.AffectedRowsResp;
import cn.vt.R;
import cn.vt.web.req.SimpleTypesReq;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.extern.slf4j.Slf4j;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-10-17
 */
@Slf4j
@Path("/stockTradeInterest")
public class StockTradeInterestController {
    @Inject
    StockTradeInterestServiceImpl stockTradeInterestService;

    @Path("/sumAllInterest")
    @POST
    public R<BigDecimal> sumAllInterest() {
        return R.success(stockTradeInterestService.sumAllInterest());
    }

    @Path("/parseTextAndSave")
    @POST
    public R<AffectedRowsResp> parseTextAndSave(SimpleTypesReq req) {
        return R.success(stockTradeInterestService.parseTextAndSave(req));
    }

}
