package cn.valuetodays.api2.web.service;

import cn.valuetodays.api2.client.enums.WxmpArticleImageEnums;
import cn.valuetodays.api2.client.persist.WxmpArticleImagePersist;
import cn.valuetodays.api2.web.repository.WxmpArticleImageDAO;
import cn.valuetodays.quarkus.commons.base.BaseService;
import cn.vt.exception.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 微信公众号文章图片
 *
 * @author lei.liu
 * @since 2025-04-08 00:08
 */
@Service
@Slf4j
public class WxmpArticleImageService
    extends BaseService<Long, WxmpArticleImagePersist, WxmpArticleImageDAO> {

    @Override
    protected void beforeSave(WxmpArticleImagePersist wxmpArticleImagePersist) {
        super.beforeSave(wxmpArticleImagePersist);
        String url = wxmpArticleImagePersist.getUrl();
        if (!StringUtils.startsWith(url, "http")) {
            throw AssertUtils.create("url应该以http(s)开头");
        }

        wxmpArticleImagePersist.setStatus(WxmpArticleImageEnums.Status.NOT_RUN);
    }

    public List<WxmpArticleImagePersist> listTop6ToRun() {
        return getRepository().findTop6ByStatusOrderByIdDesc(WxmpArticleImageEnums.Status.NOT_RUN);
    }

    public List<WxmpArticleImagePersist> listTop6ToPost() {
        return getRepository().findTop6ByStatusOrderByIdDesc(WxmpArticleImageEnums.Status.DOWNLOAD_DONE);
    }

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
        getRepository().save(old);
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
        getRepository().save(old);
    }
}
