package cn.valuetodays.api2.module.fortune.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import cn.valuetodays.api2.module.fortune.reqresp.StockPriceGradientReq;
import cn.valuetodays.api2.module.fortune.reqresp.StockPriceGradientResp;
import cn.vt.R;
import cn.vt.rest.third.eastmoney.EastMoneyStockUtils;
import cn.vt.rest.third.eastmoney.vo.EastMoneyStockDetailDataTyped;
import cn.vt.util.VtObjectUtils;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-11
 */
@Tag(name = "股票工具服务")
@Path("/fortune/stockUtil")
public class StockUtilController {
    @POST
    @Path("/stockPriceGradient")
    @Operation(summary = "股票价格梯度")
    public R<StockPriceGradientResp> stockPriceGradient(StockPriceGradientReq req) {
        String code = req.getCode();
        BigDecimal rangeValue = req.getRangeValue();
        BigDecimal rangeValueToUse = VtObjectUtils.defaultIfNull(rangeValue, BigDecimal.TWO);
        if (rangeValueToUse.compareTo(BigDecimal.ZERO) < 0) {
            rangeValueToUse = rangeValueToUse.negate();
        }

        EastMoneyStockDetailDataTyped realtimeStockDetail = EastMoneyStockUtils.getRealtimeStockDetail(code);
        BigDecimal price = realtimeStockDetail.getPrice();
        StockPriceGradientResp resp = new StockPriceGradientResp();
        resp.setCode(code);
        resp.setCurrentPrice(price);
        List<StockPriceGradientResp.GradientItem> gradientItems = new ArrayList<>();
        BigDecimal negateRangeValueToUse = rangeValueToUse.negate();
        final BigDecimal piecePtg = rangeValueToUse.subtract(negateRangeValueToUse)
            .divide(BigDecimal.TEN, 4, RoundingMode.HALF_UP);
        BigDecimal chgPtg = negateRangeValueToUse;
        do {
            BigDecimal gradientPrice = calcByPtg(price, chgPtg);
            StockPriceGradientResp.GradientItem gradientItem = new StockPriceGradientResp.GradientItem();
            gradientItem.setPrice(gradientPrice);
            gradientItem.setChgPtg(chgPtg);
            gradientItems.add(gradientItem);

            chgPtg = chgPtg.add(piecePtg);
        } while (chgPtg.compareTo(rangeValueToUse) <= 0);
        resp.setGradients(gradientItems);
        return R.success(resp);
    }

    private BigDecimal calcByPtg(BigDecimal price, BigDecimal chgPtg) {
        return price.multiply(
            BigDecimal.valueOf(100).add(chgPtg).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
        ).setScale(3, RoundingMode.HALF_UP);
    }
}
