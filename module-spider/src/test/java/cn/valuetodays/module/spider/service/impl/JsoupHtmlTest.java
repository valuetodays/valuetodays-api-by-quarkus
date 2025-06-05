package cn.valuetodays.module.spider.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-12-06
 */
public class JsoupHtmlTest {
    @Test
    void testRemoveElements() {
        String html = """
            <p>p1</p>
            <p><div class="aaa">div-with-class</div></p>
            """;
        Document document = Jsoup.parse(html);
        String s = document.outerHtml();
        assertThat(s, containsString("div-with-class"));
        Elements elements = document.select("div.aaa");
        for (Element element : elements) {
            element.remove();
        }
        String s1 = document.outerHtml();
        assertThat(s1, not(containsString("div-with-class")));
    }
}
