package cn.valuetodays.api2.web.asyncevent;

import cn.valuetodays.quote.persist.AccessedUrlPersist;
import cn.valuetodays.quote.service.AccessedUrlService;
import cn.vt.util.JsonUtils;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-03-27
 */
@ApplicationScoped
public class AccessedUrlListener {
    @Inject
    private AccessedUrlService accessedUrlService;

    @ConsumeEvent(value = Events.EVENT_ACCESSED_URL)
    @Blocking
    public void onApplicationEvent(String eventStr) {
        AccessedUrlPersist obj = JsonUtils.fromJson(eventStr, AccessedUrlPersist.class);
        String url = obj.getUrl();
        accessedUrlService.savePersistIgnoreException(url);
    }
}
