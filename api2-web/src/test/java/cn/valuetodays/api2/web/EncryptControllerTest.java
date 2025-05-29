package cn.valuetodays.api2.web;

import cn.valuetodays.api2.web.controller.EncryptController;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * Tests for {@link EncryptController}.
 *
 * @author lei.liu
 * @since 2024-12-22
 */
@QuarkusTest
public class EncryptControllerTest {

    @Inject
    EncryptController controller;

    @Test
    public void getPublicKey() {
        Map<String, Object> publicKey = controller.getPublicKey();
        System.out.println(publicKey);
    }
}
