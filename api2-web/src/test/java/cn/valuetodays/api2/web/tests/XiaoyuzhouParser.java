package cn.valuetodays.api2.web.tests;

import cn.vt.util.HttpClient4Utils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-18
 */
@Slf4j
public class XiaoyuzhouParser {
    /**
     * 解析小宇宙地址
     */
    @Test
    public void parseTitleAndAudioFromUrl() {
        String url = "https://www.xiaoyuzhoufm.com/episode/682333fcb7c8a9962ce627df";
        String pageSource = HttpClient4Utils.doGet(url);
        Document document = Jsoup.parse(pageSource);
        Elements metaEles = document.getElementsByTag("meta");
        String title = null;
        String audioUrl = null;
        for (Element metaEle : metaEles) {
            String property = metaEle.attr("property");
            if ("og:title".equals(property)) {
                title = metaEle.attr("content");
            } else if ("og:audio".equals(property)) {
                audioUrl = metaEle.attr("content");
            }
        }
        log.info("title={}", title);
        log.info("audioUrl={}", audioUrl);
    }
}
