package cn.valuetodays.module.spider.service.vocechat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.valuetodays.api2.web.basic.push.vocechat.AutoReplyContent;
import cn.valuetodays.api2.web.basic.push.vocechat.PushBaseReq;
import cn.valuetodays.api2.web.common.WebpToJpgUtils;
import cn.vt.exception.CommonException;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-16
 */
@ApplicationScoped
public class MeinvImageAutoReplyContent implements AutoReplyContent {
    @Override
    public List<String> title() {
        return List.of("meinv");
    }

    @Override
    public Pair<PushBaseReq.ContentType, String> replyContent(String value) {
        List<File> files = DownloadImage.executeJson();
        if (CollectionUtils.isEmpty(files)) {
            return null;
        }
        File first = files.getFirst();
        try {
            File file = WebpToJpgUtils.webpToJpg(first);
            return AutoReplyContent.makeFile(file.getAbsolutePath());
        } catch (IOException e) {
            throw new CommonException(e);
        }
    }
}
