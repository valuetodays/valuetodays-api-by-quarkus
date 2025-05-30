package cn.valuetodays.api2.web.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link WxmpArticleImageService}.
 *
 * @author lei.liu
 * @since 2025-05-30
 */
@QuarkusTest
public class WxmpArticleImageServiceTest {
    @Inject
    WxmpArticleImageService wxmpArticleImageService;

    @Test
    public void scheduleDownloadImage() {
        wxmpArticleImageService.scheduleDownloadImage();
    }
}
