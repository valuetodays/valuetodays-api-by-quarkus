package cn.vt.rest.third.xueqiu;

import cn.vt.rest.third.xueqiu.vo.XueQiuUserTimelineResp;
import cn.vt.util.HttpClient4Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

/**
 * Tests for {@link XueQiuUserClientUtils}.
 *
 * @author lei.liu
 * @since 2024-09-29
 */
@Slf4j
class XueQiuUserClientUtilsIntegrationTest extends BaseXueQiuIntegrationTest {

    @Test
    void timeline() {
        String userId = "5337932916";
        int page = 1;
        XueQiuUserTimelineResp resp = XueQiuUserClientUtils.timeline(page, userId, getToken());
        assertThat(resp, notNullValue());
        assertThat(resp.getPage(), equalTo(1));
        List<XueQiuUserTimelineResp.StatusItem> statuses = resp.getStatuses();
        assertThat(statuses, notNullValue());
        assertThat(statuses.size(), greaterThan(1));
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void sdfdsfds() {
        String userId = "7737735561";
        int page = 1;
        String localPath = "x:/aaa";
        while (true) {
            XueQiuUserTimelineResp resp = XueQiuUserClientUtils.timeline(page, userId, getToken());
            List<XueQiuUserTimelineResp.StatusItem> statuses = resp.getStatuses();
            if (CollectionUtils.isEmpty(statuses)) {
                log.info("last page. page={}", page);
                break;
            }
            for (XueQiuUserTimelineResp.StatusItem status : statuses) {
                String text = status.getText();
                Document doc = Jsoup.parse(text);
                Elements imgElements = doc.select("img");
                for (Element imgElement : imgElements) {
                    String imageUrl = imgElement.attr("src");
                    if (StringUtils.contains(imageUrl, "/face/emoji")) {
                        continue;
                    }
                    String originUrl = StringUtils.replace(imageUrl, "!custom.jpg", "");
                    int lastSplash = originUrl.lastIndexOf("/");
                    String fileRelatePath = originUrl.substring(lastSplash); //  /abc.png
                    // 下载时不需要cookie
                    HttpClient4Utils.doDownloadFile(originUrl, Map.of(), localPath + fileRelatePath, false);
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            page++;
        }
    }
}
