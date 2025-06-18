package cn.valuetodays.api2.web;

import cn.valuetodays.api2.web.controller.EncryptController;
import cn.vt.R;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EncryptController}.
 *
 * @author lei.liu
 * @since 2024-12-22
 */
@QuarkusTest
@Slf4j
public class EncryptControllerTest {

    @Inject
    EncryptController controller;

    @Test
    public void getPublicKey() {
        R<String> publicKey = controller.getPublicKey();
        log.info("publicKey={}", publicKey);
    }
}
