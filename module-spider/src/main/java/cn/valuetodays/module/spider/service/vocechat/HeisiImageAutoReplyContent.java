package cn.valuetodays.module.spider.service.vocechat;

import java.io.File;
import java.util.List;

import cn.valuetodays.api2.web.basic.push.vocechat.AutoReplyContent;
import cn.valuetodays.api2.web.basic.push.vocechat.PushBaseReq;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-17
 */
@ApplicationScoped
public class HeisiImageAutoReplyContent implements AutoReplyContent {
    @Override
    public List<String> title() {
        return List.of("heisi");
    }

    @Override
    public Pair<PushBaseReq.ContentType, String> replyContent(String value) {
        List<File> files = DownloadImage.getHeisiImageFileFromXxapi();
        if (CollectionUtils.isEmpty(files)) {
            return null;
        }
        File first = files.getFirst();
        return AutoReplyContent.makeFile(first.getAbsolutePath());
    }
}
