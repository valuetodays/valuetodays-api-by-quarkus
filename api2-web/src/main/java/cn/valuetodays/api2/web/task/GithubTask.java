package cn.valuetodays.api2.web.task;

import cn.valuetodays.api2.client.persist.WxmpArticleImagePersist;
import cn.valuetodays.api2.web.component.WordPressComponent;
import cn.valuetodays.api2.web.service.WxmpArticleImageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-08
 */
@ApplicationScoped
public class GithubTask {
    @Inject
    WxmpArticleImageService wxmpArticleImageService;
    @Inject
    WordPressComponent wordPressComponent;


    @Scheduled(cron = "0 0/15 * * * ?")
//    @DistributeLock(id = "scheduleDownloadImage", milliSeconds = TimeConstants.T3m)
    public void scheduleDownloadImage() {
        wxmpArticleImageService.scheduleDownloadImage();
    }

    @Scheduled(cron = "0 0/18 * * * ?")
//    @DistributeLock(id = "schedulePost", milliSeconds = TimeConstants.T3m)
    public void schedulePost() {
        List<WxmpArticleImagePersist> list = wxmpArticleImageService.listTop6ToPost();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (WxmpArticleImagePersist p : list) {
            String lastFileUrl = p.getLastFileUrl();
            if (StringUtils.isBlank(lastFileUrl)) {
                continue;
            }
            boolean suc = wordPressComponent.newPost(lastFileUrl, p);
            if (suc) {
                LocalDateTime finishTime = LocalDateTime.now();
                p.setFinishTime(finishTime);
                wxmpArticleImageService.finishPost(p);
            }
        }
    }

}
