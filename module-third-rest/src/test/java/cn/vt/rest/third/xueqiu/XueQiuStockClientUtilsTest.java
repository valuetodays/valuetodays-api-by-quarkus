package cn.vt.rest.third.xueqiu;

import cn.vt.rest.third.xueqiu.vo.XueQiuKlineData;
import cn.vt.rest.third.xueqiu.vo.XueQiuKlineResp;
import cn.vt.rest.third.xueqiu.vo.XueQiuMinuteChartData;
import cn.vt.rest.third.xueqiu.vo.XueQiuMinuteChartResp;
import cn.vt.rest.third.xueqiu.vo.XueQiuQuoteDetailData;
import cn.vt.rest.third.xueqiu.vo.XueQiuQuoteDetailResp;
import cn.vt.rest.third.xueqiu.vo.XueQiuStockRealtimeQuoteData;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

/**
 * Tests for {@link XueQiuStockClientUtils}.
 *
 * @author lei.liu
 * @since 2024-10-01
 */
public class XueQiuStockClientUtilsTest extends BaseXueQiuIntegrationTest {

    @Test
    public void minuteChart1d() {
        XueQiuMinuteChartResp r = XueQiuStockClientUtils.minuteChart1d("SH600036", getToken());
        assertThat(r, notNullValue());
        assertThat(r.getError_code(), equalTo(0));
        XueQiuMinuteChartData data = r.getData();
        assertThat(data, notNullValue());
        assertThat(data.getItems_size(), equalTo(242));
    }

    @Test
    public void minuteChart5d() {
        XueQiuMinuteChartResp r = XueQiuStockClientUtils.minuteChart5d("SH600036", getToken());
        assertThat(r, notNullValue());
        assertThat(r.getError_code(), equalTo(0));
        XueQiuMinuteChartData data = r.getData();
        assertThat(data, notNullValue());
        assertThat(data.getItems_size(), equalTo(130));
    }

    @Test
    public void kline() {
        XueQiuKlineResp r = XueQiuStockClientUtils.kline("SH600036", getToken());
        assertThat(r, notNullValue());
        assertThat(r.getError_code(), equalTo(0));
        XueQiuKlineData data = r.getData();
        assertThat(data, notNullValue());
        String[][] item = data.getItem();
        assertThat(item, notNullValue());
        assertThat(item.length, equalTo(240));
    }

    @Test
    public void realtimeQuoteOne() {
        String code = "SH600036";
        XueQiuStockRealtimeQuoteData data = XueQiuStockClientUtils.realtimeQuoteOne(code);
        assertThat(data, notNullValue());
        assertThat(data.getSymbol(), equalTo(code));
        Double current = data.getCurrent();
        Double lastClose = data.getLastClose();
        assertThat(current, notNullValue());
        assertThat(lastClose, notNullValue());
    }

    @Test
    public void quoteDetail() {
        String code = "SH600036";
        XueQiuQuoteDetailResp r = XueQiuStockClientUtils.quoteDetail(code, getToken());
        assertThat(r, notNullValue());
        XueQiuQuoteDetailData data = r.getData();
        assertThat(data, notNullValue());
        XueQiuQuoteDetailData.Quote quote = data.getQuote();
        assertThat(quote, notNullValue());
        assertThat(quote.getSymbol(), equalTo(code));
        // iopv和溢价率，同时为null或同时不为null
        BigDecimal iopv = quote.getIopv();
        BigDecimal premiumRate = quote.getPremium_rate();
        if (Objects.isNull(iopv)) {
            assertThat(premiumRate, nullValue());
        } else {
            assertThat(premiumRate, notNullValue());
        }
    }

}
