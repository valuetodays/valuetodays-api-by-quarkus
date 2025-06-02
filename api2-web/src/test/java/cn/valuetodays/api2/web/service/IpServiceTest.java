package cn.valuetodays.api2.web.service;

import cn.valuetodays.api2.client.persist.IpPersist;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Tests for {@link IpService}.
 *
 * @author lei.liu
 * @since 2024-12-07
 */
@QuarkusTest
@Slf4j
public class IpServiceTest extends BaseTest {

    @Inject
    private IpService ipService;

    @Test
    void testSave() {
        IpPersist p = new IpPersist();
        p.setIp("test-" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        p.initUserIdAndTime(1L);
        IpPersist saved = ipService.save(p);
        log.info("saved={}", saved);
        ipService.deleteById(saved.getId());
    }
}
