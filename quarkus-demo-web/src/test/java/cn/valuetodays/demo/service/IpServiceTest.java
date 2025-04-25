package cn.valuetodays.demo.service;

import cn.valuetodays.demo.persist.IpPersist;
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
public class IpServiceTest {

    @Inject
    private IpService ipService;

    @Test
    void testSave() {
        IpPersist p = new IpPersist();
        p.setIp("test-" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        IpPersist saved = ipService.save(p);
        log.info("saved={}", saved);
    }
}
