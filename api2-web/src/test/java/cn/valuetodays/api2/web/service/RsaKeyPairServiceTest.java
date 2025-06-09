package cn.valuetodays.api2.web.service;

import cn.valuetodays.api2.basic.service.RsaKeyPairService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-09
 */
@QuarkusTest
public class RsaKeyPairServiceTest {
    @Inject
    RsaKeyPairService rsaKeyPairService;

    @Test
    public void deleteOldKeyPairs() {
        rsaKeyPairService.deleteOldKeyPairs();
    }

    @Test
    public void renewKeyPairs() {
        rsaKeyPairService.renewKeyPairs();
    }
}
