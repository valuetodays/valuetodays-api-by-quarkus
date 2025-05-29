package cn.vt.rest.third.jisilu;

import cn.vt.rest.third.jisilu.vo.EtfHistoryDetailsResp;
import cn.vt.rest.third.jisilu.vo.EtfResp;
import org.hamcrest.number.OrderingComparison;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for {@link JisiluEtfClientUtils}.
 *
 * @author lei.liu
 * @since 2025-01-02
 */
public class JisiluEtfClientUtilsTest {

    private static void check(EtfResp r) {
        assertThat(r, notNullValue());
        List<EtfResp.Row> rows = r.getRows();
        assertThat(rows, notNullValue());
        int size = rows.size();
        assertThat(size, OrderingComparison.greaterThan(0));
    }

    @Test
    void getEtfForAsia() {
        EtfResp r = JisiluEtfClientUtils.getEtfForAsia();
        check(r);
    }

    @Test
    void getEtfForGold() {
        EtfResp r = JisiluEtfClientUtils.getEtfForGold();
        check(r);
    }

    @Test
    void getHistoryDetails() {
        EtfHistoryDetailsResp r = JisiluEtfClientUtils.getHistoryDetails("159331");
        assertThat(r, notNullValue());
        List<EtfHistoryDetailsResp.Row> rows = r.getRows();
        assertThat(rows, notNullValue());
        int size = rows.size();
        assertThat(size, OrderingComparison.greaterThan(0));
    }

}
