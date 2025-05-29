package cn.valuetodays.api2.web.controller;

import cn.valuetodays.api2.client.persist.AccessedUrlPersist;
import cn.valuetodays.api2.client.req.PullCookieReq;
import cn.valuetodays.api2.web.asyncevent.Events;
import cn.valuetodays.api2.web.component.CookieCacheComponent;
import cn.vt.rest.third.xueqiu.vo.PushCookieReq;
import cn.vt.util.JsonUtils;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-05-02
 */
@RestController
@RequestMapping("/cookie")
@Slf4j
public class CookieController {
    @Inject
    private EventBus eventBus;

    @Autowired
    private CookieCacheComponent cookieCacheComponent;

    @PostMapping("/anon/raw/push.do")
    public Map<String, Object> pushCookies(@RequestBody PushCookieReq req) {
        log.info("pushCookies: {}", req);
        AccessedUrlPersist p = new AccessedUrlPersist();
        p.setUrl(req.getReferer());
        eventBus.publish(Events.EVENT_ACCESSED_URL, JsonUtils.toJson(p));
        String domain = req.getDomain();
        List<String> partitions = req.getCookieTextArr();
        try {
            String cookieArrText = cookieCacheComponent.mergePartitions(partitions);
            cookieCacheComponent.doPut(domain, cookieArrText);
            return Map.of("ok", 1);
        } catch (Exception e) {
            log.error("error when pushCookies", e);
            return Map.of("ok", 0);
        }
    }

    @PostMapping("/anon/pullWeiboCookies.do")
    public Map<String, String> pullWeiboCookies() {
        List<String> excludeNames = List.of("XSRF-TOKEN");
        return pull(PushCookieReq.DOMAIN_WEIBO, excludeNames);
    }

    @PostMapping("/anon/pullXueqiuCookies.do")
    public Map<String, String> pullXueqiuCookies() {
        List<String> excludeNames = List.of("XSRF-TOKEN");
        return pull(PushCookieReq.DOMAIN_XUEQIU, excludeNames);
    }

    @PostMapping("/anon/pull.do")
    public Map<String, String> pullCookie(@RequestBody PullCookieReq req) {
        String domain = req.getDomain();
        return pull(domain, List.of());
    }

    private Map<String, String> pull(String domain, List<String> excludeNames) {
        String c = cookieCacheComponent.doGetAndBuildToString(domain, excludeNames);
        return Map.of("cookies", StringUtils.trimToEmpty(c));
    }

}
