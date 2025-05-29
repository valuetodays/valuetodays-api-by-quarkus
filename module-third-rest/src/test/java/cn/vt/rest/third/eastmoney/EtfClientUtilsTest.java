package cn.vt.rest.third.eastmoney;

import cn.vt.rest.third.eastmoney.vo.IopvResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EtfClientUtils}.
 *
 * @author lei.liu
 * @since 2024-12-21
 */
@Slf4j
public class EtfClientUtilsTest {

    @Test
    public void getIopv() {
        IopvResp resp = EtfClientUtils.getIopv("513300", 2);
        log.info("respStr={}", resp);
    }
}
