package cn.valuetodays.module.spider.service.vocechat;

import java.io.File;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DownloadImage}.
 *
 * @author lei.liu
 * @since 2025-06-17
 */
@Slf4j
public class DownloadImageTest {

    @Test
    public void getHeisiImageFileFromXxapi() {
        List<File> heisiImageFileFromXxapi = DownloadImage.getHeisiImageFileFromXxapi();
        log.info("heisiImageFileFromXxapi={}", heisiImageFileFromXxapi);
    }
}
