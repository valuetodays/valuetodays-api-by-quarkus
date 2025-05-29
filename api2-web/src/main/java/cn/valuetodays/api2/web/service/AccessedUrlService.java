package cn.valuetodays.api2.web.service;

import cn.valuetodays.quarkus.commons.base.BaseService;
import cn.valuetodays.quote.persist.AccessedUrlPersist;
import cn.valuetodays.quote.repository.AccessedUrlDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 访问过的url
 *
 * @author lei.liu
 * @since 2025-03-27 16:01
 */
@Service
@Slf4j
public class AccessedUrlService
    extends BaseService<Long, AccessedUrlPersist, AccessedUrlDAO> {

    public void savePersistIgnoreException(String url) {
        AccessedUrlPersist p = new AccessedUrlPersist();
        p.setUserId(1L);
        p.setAccessAt(LocalDateTime.now());
        p.setUrl(url);
        p.setCreateUserName("1");
        p.setUpdateUserName("1");
        p.setCreateUserId(p.getUserId());
        p.setUpdateUserId(p.getUserId());

        try {
            super.save(p);
        } catch (Exception e) {
            log.warn("error ", e);
        }
    }
}
