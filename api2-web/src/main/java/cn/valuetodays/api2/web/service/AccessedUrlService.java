package cn.valuetodays.api2.web.service;

import cn.valuetodays.api2.client.persist.AccessedUrlPersist;
import cn.valuetodays.api2.web.repository.AccessedUrlDAO;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * 访问过的url
 *
 * @author lei.liu
 * @since 2025-03-27 16:01
 */
@ApplicationScoped
@Slf4j
public class AccessedUrlService
    extends BaseCrudService<Long, AccessedUrlPersist, AccessedUrlDAO> {

    @Transactional
    public void savePersistIgnoreException(String url) {
        AccessedUrlPersist p = new AccessedUrlPersist();
        p.setAccessAt(LocalDateTime.now());
        p.setUserId(1L);
        p.setUrl(url);
        p.initUserIdAndTime(p.getUserId());

        try {
            super.save(p);
        } catch (Exception e) {
            log.warn("error ", e);
        }
    }
}
