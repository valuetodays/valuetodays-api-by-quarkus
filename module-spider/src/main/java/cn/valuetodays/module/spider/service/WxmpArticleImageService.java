package cn.valuetodays.module.spider.service;

import cn.valuetodays.api2.client.enums.WxmpArticleImageEnums;
import cn.valuetodays.api2.web.common.GithubComponent;
import cn.valuetodays.module.spider.client.persist.WxmpArticleImagePersist;
import cn.valuetodays.module.spider.component.WordPressComponent;
import cn.valuetodays.module.spider.dao.WxmpArticleImageDAO;
import cn.valuetodays.quarkus.commons.base.BaseService;
import cn.vt.exception.AssertUtils;
import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 微信公众号文章图片
 *
 * @author lei.liu
 * @since 2025-04-08 00:08
 */
@ApplicationScoped
@Slf4j
public class WxmpArticleImageService
    extends BaseService<Long, WxmpArticleImagePersist, WxmpArticleImageDAO> {

    @Inject
    GithubComponent githubComponent;
    @Inject
    WordPressComponent wordPressComponent;

    @Override
    protected void beforeSave(WxmpArticleImagePersist wxmpArticleImagePersist) {
        super.beforeSave(wxmpArticleImagePersist);
        String url = wxmpArticleImagePersist.getUrl();
        AssertUtils.assertTrue(StringUtils.startsWith(url, "http"), "url应该以http(s)开头");

        wxmpArticleImagePersist.setStatus(WxmpArticleImageEnums.Status.NOT_RUN);
    }

    public List<WxmpArticleImagePersist> listTop6ToRun() {
        return getRepository().findTop6ByStatusOrderByIdDesc(WxmpArticleImageEnums.Status.NOT_RUN);
    }

    public List<WxmpArticleImagePersist> listTop6ToPost() {
        return getRepository().findTop6ByStatusOrderByIdDesc(WxmpArticleImageEnums.Status.DOWNLOAD_DONE);
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void finishDownload(WxmpArticleImagePersist p) {
        Long id = p.getId();
        WxmpArticleImagePersist old = findById(id);
        if (Objects.isNull(old)) {
            return;
        }
        if (old.getStatus() != WxmpArticleImageEnums.Status.NOT_RUN) {
            return;
        }
        old.setTitle(p.getTitle());
        old.setLastFileUrl(p.getLastFileUrl());
        old.setBeginTime(p.getBeginTime());
        old.setStatus(WxmpArticleImageEnums.Status.DOWNLOAD_DONE);
        getRepository().persist(old);
    }


    public void finishPost(WxmpArticleImagePersist p) {
        Long id = p.getId();
        WxmpArticleImagePersist old = findById(id);
        if (Objects.isNull(old)) {
            return;
        }
        if (old.getStatus() != WxmpArticleImageEnums.Status.DOWNLOAD_DONE) {
            return;
        }
        old.setFinishTime(p.getFinishTime());
        old.setStatus(WxmpArticleImageEnums.Status.POST_DONE);
        getRepository().persist(old);
    }

    public void scheduleDownloadImage() {
        List<WxmpArticleImagePersist> list = this.listTop6ToRun();
        log.info("list: {}", list.size());
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (WxmpArticleImagePersist p : list) {
            String url = p.getUrl();
            LocalDateTime beginTime = LocalDateTime.now();
            log.info("to uploadImage: {}", url);
            Tuple2<String, List<String>> tuple2 = githubComponent.uploadImageByWxmpUrl(url);
            log.info("end to uploadImage: {}", url);
            List<String> t2 = tuple2.getItem2();
            String t1 = tuple2.getItem1();
            LocalDateTime finishTime = LocalDateTime.now();
            p.setBeginTime(beginTime);
            p.setFinishTime(finishTime);
            p.setTitle(t1);
            p.setLastFileUrl(t2.getLast());
            this.finishDownload(p);
        }

    }
}
