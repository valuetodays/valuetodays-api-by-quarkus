package cn.vt.rest.third.eastmoney;

import cn.vt.rest.third.eastmoney.vo.EastMoneyStockDetailDataTyped;
import cn.vt.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests for {@link EastMoneyStockUtils}.
 *
 * @author lei.liu
 * @since 2025-04-25
 */
@Slf4j
public class EastMoneyStockUtilsTest {

    @Test
    void getStockDetail() {
        String stockCode = "000001";
        EastMoneyStockDetailDataTyped sd = EastMoneyStockUtils.getRealtimeStockDetail(stockCode);
        assertThat(sd, notNullValue());
        assertThat(sd.getCode(), equalTo(stockCode));
        log.info("sd={}", JsonUtils.toJson(sd));
    }
}
