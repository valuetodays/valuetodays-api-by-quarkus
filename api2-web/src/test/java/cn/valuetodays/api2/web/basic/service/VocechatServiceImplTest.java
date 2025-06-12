package cn.valuetodays.api2.web.basic.service;

import java.io.File;

import cn.valuetodays.api2.basic.service.VocechatServiceImpl;
import cn.valuetodays.api2.basic.vo.PushVocechatFileReq;
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
        vocechatTextReq.setFromUserId(3);
        vocechatService.pushVocechatText(vocechatTextReq);
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void pushVocechatMarkdown() {
        PushVocechatTextReq vocechatTextReq = new PushVocechatTextReq();
        vocechatTextReq.setContent("**test**-msg");
        vocechatTextReq.setPlainText(false);
        vocechatTextReq.useToGroupId(2);
        vocechatTextReq.setFromUserId(3);
        vocechatService.pushVocechatText(vocechatTextReq);
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void pushVocechatFile() {
        PushVocechatFileReq pushVocechatFileReq = new PushVocechatFileReq();
        pushVocechatFileReq.useToGroupId(2);
        pushVocechatFileReq.setFromUserId(3);
        pushVocechatFileReq.setFileName("b2cf1a99f64ac.png");
        pushVocechatFileReq.setFile(new File("x:/b2cf1a99f64ac.png"));
        vocechatService.pushVocechatFile(pushVocechatFileReq);
    }
}
