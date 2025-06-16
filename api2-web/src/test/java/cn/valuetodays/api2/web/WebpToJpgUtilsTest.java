package cn.valuetodays.api2.web;

import java.io.File;
import java.io.IOException;

import cn.valuetodays.api2.web.common.WebpToJpgUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-16
 */
@Slf4j
public class WebpToJpgUtilsTest {

    @Test
    public void webpToJpg() throws IOException {
        File webpFile = new File("x:/abc.webp");
        File jpgFile = WebpToJpgUtils.webpToJpg(webpFile);
        log.info("jpgFile={}", jpgFile);
    }
}
