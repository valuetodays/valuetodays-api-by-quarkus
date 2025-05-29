package cn.vt.rest.third.xueqiu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author lei.liu
 * @since 2024-09-19
 */
public class XueQiuTokenGatherTest extends BaseXueQiuIntegrationTest {
    @Test
//    @EnabledIfInternet(url = XueQiuTokenGather.API_URL)
    public void getLoginCookie() {
        String loginCookie = getToken();
        Assertions.assertNotNull(loginCookie);
        Assertions.assertNotEquals(loginCookie, "null");
    }
}
