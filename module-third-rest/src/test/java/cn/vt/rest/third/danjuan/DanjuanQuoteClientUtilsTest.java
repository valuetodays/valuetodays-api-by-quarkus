package cn.vt.rest.third.danjuan;

import cn.vt.rest.third.danjuan.vo.DjIndexData;
import cn.vt.rest.third.danjuan.vo.DjIndexResp;
import cn.vt.rest.third.danjuan.vo.DjPbResp;
import cn.vt.rest.third.danjuan.vo.DjPeResp;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests for {@link DanjuanQuoteClientUtils}.
 *
 * @author lei.liu
 * @since 2024-10-01
 */
public class DanjuanQuoteClientUtilsTest {

    @Test
    public void evaluationList() {
        DjIndexResp djIndexResp = DanjuanQuoteClientUtils.evaluationList();
        assertThat(djIndexResp, notNullValue());
        int resultCode = djIndexResp.getResultCode();
        assertThat(resultCode, equalTo(0));
        DjIndexData data = djIndexResp.getData();
        assertThat(data, notNullValue());
        assertThat(data.getItems(), Matchers.hasSize(data.getSize()));
        DjIndexData.Item firstItem = data.getItems().get(0);
        String indexCode = firstItem.getIndexCode();
        String region = indexCode.substring(0, 2);
        String code = indexCode.substring(2);
        DjPbResp djPbResp = DanjuanQuoteClientUtils.pbHistory(region, code, "3y");
        assertThat(djPbResp, notNullValue());
        assertThat(djPbResp.getData(), notNullValue());
        DjPeResp djPeResp = DanjuanQuoteClientUtils.peHistory(region, code, "3y");
        assertThat(djPeResp, notNullValue());
        assertThat(djPeResp.getData(), notNullValue());
    }
}
