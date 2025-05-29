package cn.vt.rest.third.sse;

import cn.vt.rest.third.sse.vo.StockNewAccountResp;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for {@link SseNewStockAccountClientUtils}.
 *
 * @author lei.liu
 * @since 2025-04-24
 */
@Slf4j
public class SseNewStockAccountClientUtilsTest {

    @Test
    public void getStockNewAccount() {
        StockNewAccountResp a = SseNewStockAccountClientUtils.getStockNewAccount("202503");
        List<StockNewAccountResp.Item> list = a.getResult();
        assertThat(list, CoreMatchers.notNullValue());
        for (StockNewAccountResp.Item item : list) {
            log.info("item: {}", item);
        }
    }
}
