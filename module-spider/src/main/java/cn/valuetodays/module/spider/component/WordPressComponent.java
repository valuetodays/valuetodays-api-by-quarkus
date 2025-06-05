package cn.valuetodays.module.spider.component;

import cn.valuetodays.module.spider.client.persist.WxmpArticleImagePersist;
import cn.vt.util.DateUtils;
import cn.vt.util.HttpClient4Utils;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-08
 */
@ApplicationScoped
@Slf4j
public class WordPressComponent {

    private static final String wp_base_path = "http://wp.valuetodays.cn";
    private static final String username = "admin";
    private static final String password = "DtHX rtWl zhSC V9mN sc2F OCHQ";
    private static final String credentials = username + ':' + password;

//
//#
//    访问rest api报404时
//#https://blog.csdn.net/weixin_44440133/article/details/135074412

    /**
     * todo 未指定封面
     *
     * @param lastFileUrl
     * @param p
     * @return
     * @see <a href="https://developer.wordpress.org/rest-api/reference/posts/">official api</a>
     */
    public boolean newPost(String lastFileUrl, WxmpArticleImagePersist p) {
        String content = buildContent(lastFileUrl);
        if (StringUtils.isBlank(content)) {
            return false;
        }
        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.US_ASCII);
        byte[] encodeBase64 = Base64.encodeBase64(credentialsBytes);
        String token = new String(encodeBase64, StandardCharsets.UTF_8);
        Map<String, String> headers = Map.of("Authorization", "Basic " + token);

        final String restUrl = wp_base_path + "/wp-json/wp/v2/posts";


        String formattedTime = DateUtils.formatDatetimeToday();
        String title = "美女图片-" + formattedTime;
        if (Objects.nonNull(p)) {
            title = p.getTitle();
        }
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", title);
        payload.put("status", "publish");
        payload.put("content", content);
        payload.put("date_gmt", formattedTime);
        payload.put("categories", new Integer[]{16, 15});

        String s = HttpClient4Utils.doPostJson(restUrl, payload, null, headers);
        log.info("s={}", s);
        if (StringUtils.containsAny(s, "\"id\":")) {
            return true;
        }
        return false;
    }

    /**
     * @param lastFileUrl https://valuetodays.github.io/statics/images/wp/mmpic/IjxsUE0N6c0akLotOjHwIQ/6.jpeg
     */
    private String buildContent(String lastFileUrl) {
        final String tpl = """
            <img src="{{url}}" class="aligncenter size-medium" />
            """;

        String filePath = StringUtils.substringBeforeLast(lastFileUrl, "/");
        String filename = StringUtils.substringAfterLast(lastFileUrl, "/");
        String filenameWithoutExt = StringUtils.substringBeforeLast(filename, ".");
        String ext = StringUtils.substringAfterLast(filename, ".");
        int fileCount = Integer.parseInt(filenameWithoutExt);
        return IntStream.rangeClosed(0, fileCount)
            .distinct()
            .mapToObj(e -> StringUtils.replace(tpl, "{{url}}", filePath + "/" + e + "." + ext))
            .collect(Collectors.joining("\n\n"));
    }
}
