package cn.valuetodays.api2.module.tests;

import cn.vt.encrypt.BCryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-31
 */
@Slf4j
public class BCryptUtilsTest {
    @Test
    public void test() {
        String hashpwed = BCryptUtils.hashpw("qJjrsfe0esG");
        log.info("hashpwed={}", hashpwed);
    }
}
