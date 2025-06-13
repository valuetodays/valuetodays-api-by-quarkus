package cn.valuetodays.api2.web.module.basic.controller;

import cn.valuetodays.api2.basic.controller.ImPushController;
import cn.valuetodays.api2.basic.vo.PushVocechatTextReq;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ImPushController}.
 *
 * @author lei.liu
 * @since 2025-06-12
 */
@QuarkusTest
public class ImPushControllerTest {
    @Inject
    ImPushController imPushController;

    @Test
    public void pushText() {
        PushVocechatTextReq req = new PushVocechatTextReq();
        req.setContent("this is 一个消息。 2025");
        req.setPlainText(true);
//        req.setToGroupId(2);
//        req.setToUserId(0);
        req.useToUserId(1);

        imPushController.pushVocechatPlainText(req);
    }

}
