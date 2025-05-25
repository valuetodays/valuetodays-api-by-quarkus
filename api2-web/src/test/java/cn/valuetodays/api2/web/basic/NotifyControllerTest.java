package cn.valuetodays.api2.web.basic;

import cn.valuetodays.api2.basic.controller.NotifyController;
import cn.valuetodays.api2.web.service.BaseTest;
import cn.vt.web.req.SimpleTypesReq;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-25
 */
@QuarkusTest
public class NotifyControllerTest extends BaseTest {
    @Inject
    private NotifyController notifyController;

    @Test
    public void pushToBark() {
        SimpleTypesReq req = new SimpleTypesReq();
        req.setText("测试推送消息");
        notifyController.pushToBark(req);
    }
}
