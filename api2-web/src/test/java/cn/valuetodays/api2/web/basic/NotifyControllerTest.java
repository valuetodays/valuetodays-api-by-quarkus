package cn.valuetodays.api2.web.basic;

import cn.valuetodays.api2.basic.service.NotifyServiceImpl;
import cn.valuetodays.api2.web.service.BaseTest;
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
    private NotifyServiceImpl notifyService;

    @Test
    public void pushToBark() {
        notifyService.notifyApplicationMsg("application-msg is here");
        notifyService.notifyApplicationException("test", "application-msg is here");
    }
}
