package cn.valuetodays.api2.basic.service.vocechat;

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
public class SampleMarkdownAutoReplyContent implements AutoReplyContent {
    @Override
    public List<String> title() {
        return List.of("markdown");
    }

    @Override
    public Pair<PushBaseReq.ContentType, String> replyContent(String value) {
        return Pair.of(
            PushBaseReq.ContentType.MARKDOWN_TEXT,
            value + " from deeply `" + this.getClass().getSimpleName() + "`"
        );
    }
}
