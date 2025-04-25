package cn.valuetodays.demo.service;

import cn.valuetodays.demo.persist.StockAlertPersist;
import cn.valuetodays.demo.persist.enums.StockAlertEnums;
import cn.valuetodays.demo.repository.StockAlertDAO;
import cn.valuetodays.quarkus.commons.base.BaseService;
import cn.vt.exception.AssertUtils;
import cn.vt.rest.third.eastmoney.EastMoneyStockUtils;
import cn.vt.rest.third.eastmoney.vo.EastMoneyStockDetailDataTyped;
import cn.vt.util.DateUtils;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 股票告警
 *
 * @author lei.liu
 * @since 2025-04-16 08:40
 */
@ApplicationScoped
@Slf4j
public class StockAlertService
    extends BaseService<Long, StockAlertPersist, StockAlertDAO> {

    public void scheduleAlert(StockAlertEnums.ScheduleType scheduleType) {
        List<StockAlertPersist> list = getRepository().findAllByStatusAndScheduleType(StockAlertEnums.Status.NORMAL, scheduleType);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (StockAlertPersist p : list) {
            String code = p.getCode();
            // todo 创业板有0开头的，上证指数也是0开头的
            CodeType codeType = null;
            if (StringUtils.startsWithAny(code, "5", "1", "6", "3")) {
                codeType = CodeType.STOCK;
            } else if (StringUtils.equalsAny(code, "000001")) {
                codeType = CodeType.INDEX;
            }
            AssertUtils.assertNotNull(codeType, "id为" + p.getId() + "的code不是指数也不是股票");

            BigDecimal pointWhenAlert = p.getCurrentPoint();
            BigDecimal targetPoint = p.getTargetPoint();
            BigDecimal targetPtg = p.getTargetPtg();

            BigDecimal realtimePoint = BigDecimal.ZERO;
            if (CodeType.STOCK == codeType) {
                EastMoneyStockDetailDataTyped realtimeStockDetail = EastMoneyStockUtils.getRealtimeStockDetail(code);
                realtimePoint = realtimeStockDetail.getPrice();
            } else if (CodeType.INDEX == codeType) {

            }

            boolean reachAlertWhenUp = (pointWhenAlert.compareTo(targetPoint) < 0 && targetPoint.compareTo(realtimePoint) <= 0);
            boolean reachAlertWhenDown = (pointWhenAlert.compareTo(targetPoint) > 0 && targetPoint.compareTo(realtimePoint) > 0);
            BigDecimal realChgPtg = realtimePoint.subtract(pointWhenAlert)
                .divide(pointWhenAlert, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
            if (reachAlertWhenUp || reachAlertWhenDown) {
                // alert now
                String msg = "股票到达设定的条件啦：设定在" + DateUtils.formatDate(p.getCreateTime()) + "的" + code
                    + "的点位是" + pointWhenAlert + "，目标点位是" + targetPoint
                    + "，当前点位是" + realtimePoint + "，"
                    + "，涨跌幅度为" + realChgPtg + "%";
                log.info("ALERT::: alert msg: {}", msg);
            }
            if (BigDecimal.ZERO.compareTo(targetPtg) != 0) {
                targetPoint = pointWhenAlert.multiply(targetPtg);
                reachAlertWhenUp = (targetPtg.compareTo(BigDecimal.ONE) > 0 && targetPoint.compareTo(realtimePoint) <= 0);
                reachAlertWhenDown = (targetPtg.compareTo(BigDecimal.ONE) < 0 && targetPoint.compareTo(realtimePoint) > 0);

                if (reachAlertWhenUp || reachAlertWhenDown) {
                    BigDecimal chgPtg = targetPtg.subtract(BigDecimal.ONE)
                        .multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);

                    // alert now
                    String msg = "股票到达设定的条件啦：设定在" + DateUtils.formatDate(p.getCreateTime()) + "的" + code
                        + "的点位是" + pointWhenAlert
                        + "，当前点位是" + realtimePoint
                        + "，目标涨跌幅度是" + chgPtg + "%"
                        + "，实际涨跌幅度为" + realChgPtg + "%";
                    log.info("ALERT::: alert msg: {}", msg);
                }
            }

            if (StockAlertEnums.ScheduleType.CLOSE == scheduleType) {
//                AShareLatestTurnAmountResp.SingleRecord singleRecord = eastMoneyIndexModule.realtimeIndex();
            } else if (StockAlertEnums.ScheduleType.EVERY_10_MIN == scheduleType) {

            } else if (StockAlertEnums.ScheduleType.EVERY_20_MIN == scheduleType) {
            } else {
                log.error("unknown schedule_type: {}", scheduleType);
            }
        }
    }

    private enum CodeType {
        INDEX,
        STOCK;
    }

}
