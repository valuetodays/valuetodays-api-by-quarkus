package cn.valuetodays.api2.extra.service;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import cn.valuetodays.api2.extra.dao.WeiboDAO;
import cn.valuetodays.api2.extra.persist.WeiboPO;
import cn.valuetodays.api2.extra.persist.WeiboUserPO;
import cn.valuetodays.api2.web.ICookieCacheComponent;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import cn.vt.exception.CommonException;
import cn.vt.rest.third.xueqiu.vo.PushCookieReq;
import cn.vt.util.ConvertUtils;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;

/**
 * 微博
 *
 * @author lei.liu
 * @since 2024-04-28 14:07
 */
@ApplicationScoped
@Slf4j
public class WeiboServiceImpl
    extends BaseCrudService<Long, WeiboPO, WeiboDAO> {
    @Inject
    ICookieCacheComponent cookieCacheComponentWrapper;
    @Inject
    WeiboUserServiceImpl weiboUserService;

    private static String replaceVarsInHeader(Long uid) {
        final String headersAsString = """
                Host: weibo.com
                User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/119.0
                Accept: application/json, text/plain, */*
                Accept-Language: en-US,en;q=0.5
                Accept-Encoding: gzip, deflate, br
                X-Requested-With: XMLHttpRequest
                client-version: v2.43.47
                server-version: v2023.10.13.1
                Connection: keep-alive
                Referer: https://weibo.com/u/{uid}
                Sec-Fetch-Dest: empty
                Sec-Fetch-Mode: cors
                Sec-Fetch-Site: same-origin
            """;
        return headersAsString.replace("{uid}", uid.toString());
    }

    public void gather() {
        List<WeiboUserPO> userList = weiboUserService.findAllMaintaining();
        for (WeiboUserPO weiboUserPO : userList) {
            try {
                gather(weiboUserPO.getId());
            } catch (Exception e) {
                throw new CommonException(e);
            }
        }
    }

    public void gather(Long uid) throws IOException, InterruptedException {
        gather(uid, null, false);
    }

    public void gather(Long uid, String cookieValue, boolean fully) throws IOException, InterruptedException {
        String since_id = null;
        int page = 1;
        final Map<String, String> headerMap = getHeaderMap(uid, cookieValue);
        if (MapUtils.isEmpty(headerMap)) {
            return;
        }
        while (true) {
            String sinceIdParamString = (since_id == null) ? "" : ("&since_id=" + since_id);
            final String urlForPage = "https://weibo.com/ajax/statuses/mymblog?"
                + "uid=" + uid
                + "&page=" + page
                + "&feature=0"
                + sinceIdParamString;
            log.info("url={}", urlForPage);
            String jsonStr = HttpClient4Utils.doGet(urlForPage, null, headerMap, null);
            if (StringUtils.contains(jsonStr, "414 Request-URI Too Large")) {
                TimeUnit.SECONDS.sleep(5);
                continue;
            }
            log.info("jsonStr={}", jsonStr);
            MymblogResp mymblogResp = JsonUtils.fromJson(jsonStr, MymblogResp.class);
//            log.info("mymblogResp={}", mymblogResp);
            if (Objects.isNull(mymblogResp)) {
                return;
            }
            MymblogListData data = mymblogResp.getData();
            if (Objects.isNull(data)) {
                return;
            }
            fillLongTextInNecessary(mymblogResp, headerMap);
            List<MymblogData> list = data.getList();
            trySaveList(list, uid);
            since_id = data.getSince_id();
            if (StringUtils.isBlank(since_id) && CollectionUtils.isEmpty(list)) {
                log.info("lastPage occur.");
                break;
            }
            page++;
            if (!fully) {
                // 非全时时只获取2页，用户一般不会发布特别多
                if (page > 3) {
                    break;
                }
            }
        }
    }

    private void trySaveList(List<MymblogData> list, Long weiboUserId) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (MymblogData mymblogData : list) {
            WeiboPO weibo = ConvertUtils.convertObj(mymblogData, WeiboPO.class);
            weibo.setHtml(mymblogData.getTextFull());
            weibo.setUserId(weiboUserId);
            weibo.setCreatedTime(fromZonedTimeString(mymblogData.getCreatedAt()));
            try {
                getRepository().persist(weibo);
            } catch (ConstraintViolationException ignored) {
            } catch (Exception e) {
                log.error("error when saveList", e);
            }
        }
    }

    private LocalDateTime fromZonedTimeString(String timeStr) {
        String pattern = "EEE MMM dd HH:mm:ss Z yyyy";
        // 解析时间字符串
        DateTimeFormatter localedFormatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
        ZonedDateTime dateTime = ZonedDateTime.parse(timeStr, localedFormatter);
        return dateTime.toLocalDateTime();
    }

    private Map<String, String> getHeaderMap(Long uid, String cookieValue) {
        String headerAsString = replaceVarsInHeader(uid);
        Map<String, String> headers = Arrays.stream(headerAsString.split("\n"))
            .filter(e -> !e.startsWith("Content-Length:"))
            .collect(
                Collectors.toMap(
                    e -> e.split(":")[0].strip(),
                    e -> StringUtils.trim(e.split(":")[1]).strip()
                )
            );
        HashMap<String, String> headersToUse = new HashMap<>(headers);
        if (StringUtils.isBlank(cookieValue)) {
            List<String> excludeKeys = List.of("XSRF-TOKEN");
            String allCookieText = cookieCacheComponentWrapper.doGetAndBuildToString(PushCookieReq.DOMAIN_WEIBO, excludeKeys);
            // 如果没有cookie，请求也不会成功
            if (StringUtils.isBlank(allCookieText)) {
                return null;
            }
            headersToUse.put("Cookie", allCookieText);
        } else {
            headersToUse.put("Cookie", cookieValue);
        }
        return headersToUse;
    }

    public void fillLongTextInNecessary(MymblogResp resp, Map<String, String> headerMap) {
        MymblogListData listData = resp.getData();
        List<MymblogData> list = listData.getList();
        for (MymblogData mymblogData : list) {
            boolean longText = mymblogData.isLongText();
            if (longText) {
                String mblogid = mymblogData.getMblogid();
                String url = "https://weibo.com/ajax/statuses/longtext?id=" + mblogid;
                String respString = HttpClient4Utils.doGet(url, null, headerMap, null);
                LongTextResp longTextResp = JsonUtils.fromJson(respString, LongTextResp.class);
                mymblogData.setTextFull(longTextResp.getData().getLongTextContent());
            } else {
                mymblogData.setTextFull(mymblogData.getTextRaw());
            }
        }
    }


    @Data
    private static class LongTextResp implements Serializable {
        private int ok;
        private LongTextData data;
    }

    @Data
    private static class LongTextData implements Serializable {
        private String longTextContent;
    }

    @Data
    private static class MymblogResp implements Serializable {
        private int ok;
        private MymblogListData data;
    }

    @Data
    private static class MymblogListData implements Serializable {
        private String since_id;
        private int total;
        private List<MymblogData> list;
    }

    @Data
    private static class MymblogData implements Serializable {
        private String mid; // "4909801333066684"
        // https://weibo.com/7519797263/ObuQnfu7t
        private String mblogid; // "N46YBcRME"
        @JsonProperty("created_at")
        private String createdAt; // "Wed Jun 07 01:08:40 +0800 2023",
        private String source; // "生日动态"
        @JsonProperty("text_raw")
        private String textRaw; // "http://t.cn/A6pVErFn"
        private String textFull; // 不是weibo返回的字段，手工添加的
        @JsonProperty("edit_count")
        private int editCount;
        @JsonProperty("isLongText")
        private boolean longText; // false
        @JsonProperty("reposts_count")
        private int repostsCount; // 转发数 183
        @JsonProperty("comments_count")
        private int commentsCount; // 评论数 183
        @JsonProperty("attitudes_count")
        private int attitudesCount; // 赞数 183
        private int mblogtype;
        @JsonProperty("region_name")
        private String regionName; // "发布于 北京"
    }


}
