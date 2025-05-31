package cn.valuetodays.api2.basic.service;

import cn.valuetodays.api2.basic.vo.PushVocechatTextReq;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * Tests for {@link VocechatServiceImpl}.
 *
 * @author lei.liu
 * @since 2025-05-31
 */
@QuarkusTest
public class VocechatServiceImplTest {

    @Inject
    VocechatServiceImpl vocechatService;

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void pushVocechatText() {
        PushVocechatTextReq vocechatTextReq = new PushVocechatTextReq();
        vocechatTextReq.setContent("test-msg");
        vocechatTextReq.setPlainText(true);
        vocechatTextReq.useToGroupId(2);
        vocechatService.pushVocechatText(vocechatTextReq);
    }
}
