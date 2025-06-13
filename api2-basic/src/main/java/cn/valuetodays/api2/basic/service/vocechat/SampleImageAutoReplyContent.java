package cn.valuetodays.api2.basic.service.vocechat;

import java.io.File;
import java.util.List;

import cn.valuetodays.api2.web.basic.push.vocechat.AutoReplyContent;
import cn.valuetodays.api2.web.basic.push.vocechat.PushBaseReq;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.tuple.Pair;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-13
 */
@ApplicationScoped
public class SampleImageAutoReplyContent implements AutoReplyContent {
    @Override
    public List<String> title() {
        return List.of("image", "图片");
    }

    @Override
    public Pair<PushBaseReq.ContentType, String> replyContent(String value) {
        return Pair.of(
            PushBaseReq.ContentType.FILE,
            new File("x:/b2cf1a99f64ac.png").getAbsolutePath()
        );
    }
}
