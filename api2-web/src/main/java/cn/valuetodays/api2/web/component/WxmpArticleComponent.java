package cn.valuetodays.api2.web.component;

import cn.vt.util.HttpClient4Utils;
import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-07
 */
@ApplicationScoped
@Slf4j
public class WxmpArticleComponent {

    /**
     * 给定一个微信公众号url，下载正文中的图片到本地。
     */
    public Tuple2<String, List<File>> downloadImages(File baseDir, String url) {
//        String url = "https://mp.weixin.qq.com/s/T6xLk7REm6NYqKkBBvnHFQ";
        String pagesource = HttpClient4Utils.doGet(url);
//        log.info(pagesource);
        String dirName = StringUtils.substringAfterLast(url, "/");
        Document document = Jsoup.parse(pagesource);
        Element title_ele = document.getElementById("activity-name");
        String title = title_ele.text().strip();
        // var appuin = "" || "Mzk3NTczMjcyMw==";
        String strForBizId = StringUtils.substringAfter(pagesource, "var appuin = \"\" ||");
        String tmpStr = StringUtils.substringAfter(strForBizId, "\"");
        String bizId = StringUtils.substringBefore(tmpStr, "\"");
        log.info("bizId={}", bizId);

        Element js_content = document.getElementById("js_content");
//        String html = js_content.html();
        Elements elements = js_content.select("img");
        List<File> files = new ArrayList<>(elements.size());
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
//            log.info("element.attrs={}", element.attributes());
            // https://mmbiz.qpic.cn/sz_mmbiz_png/cmyiccjltmmbuO0ewaZSKAs8OiapTL6WM11ticS4WLunh4Q9pJILjTe63veZiaP5w
            // OaQVuB7qaLicJ1LzOrbb3NjsXQ/640?wx_fmt=png
            String dataSrc = element.attr("data-src");
            URI uri = URI.create(dataSrc);
            String rawQuery = uri.getRawQuery();
            List<NameValuePair> qsList = URLEncodedUtils.parse(rawQuery, StandardCharsets.UTF_8);
            Map<String, String> querAsMap = qsList.stream()
                .collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));
            String ext = querAsMap.getOrDefault("wx_fmt", "png");
            File file = new File(baseDir, dirName + "/" + i + "." + ext);
            HttpClient4Utils.doDownloadFile(dataSrc, file.getAbsolutePath());
            files.add(file);
        }
        return Tuple2.of(title, files);
    }

}
