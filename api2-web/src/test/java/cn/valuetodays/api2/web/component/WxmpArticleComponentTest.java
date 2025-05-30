package cn.valuetodays.api2.web.component;

import io.smallrye.mutiny.tuples.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.io.File;
import java.util.List;

/**
 * Tests for {@link WxmpArticleComponent}.
 *
 * @author lei.liu
 * @since 2025-04-07
 */
@Slf4j
public class WxmpArticleComponentTest {

    private WxmpArticleComponent wxmpArticleComponent = new WxmpArticleComponent();

    @EnabledOnOs(OS.WINDOWS)
    @Test
    public void downloadImages() {
//        String url = "https://mp.weixin.qq.com/s/T6xLk7REm6NYqKkBBvnHFQ";
        String url = "https://mp.weixin.qq.com/s/8u9ma26aAUY_QbzyyT8-Gw";
        Tuple2<String, List<File>> tuple2 = wxmpArticleComponent.downloadImages(new File("c:/tmp"), url);
        String title = tuple2.getItem1();
        List<File> files = tuple2.getItem2();
        for (File file : files) {
            log.info("file={}", file);
        }
    }
}
