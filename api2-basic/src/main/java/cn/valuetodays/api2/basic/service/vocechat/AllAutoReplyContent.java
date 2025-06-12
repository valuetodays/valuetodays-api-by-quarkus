package cn.valuetodays.api2.basic.service.vocechat;

import java.util.List;
import java.util.stream.Collectors;

import cn.valuetodays.api2.basic.vo.PushBaseReq;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-11-28
 */
@ApplicationScoped
public class AllAutoReplyContent implements AutoReplyContent {
    @Inject
    Instance<AutoReplyContent> autoReplyContents;

    @Override
    public List<String> title() {
        return List.of("help", "all");
    }

    @Override
    public Pair<PushBaseReq.ContentType, String> replyContent(String value) {
        String titlesAsString = autoReplyContents.stream()
            .filter(e -> e != this)
            .map(e -> StringUtils.join(e.sampleValue(), " / "))
            .collect(Collectors.joining("\n"));
        return AutoReplyContent.makePlainText(
            "你可以问我如下问题：\n" + StringUtils.join(this.title(), " / ") + "\n" + titlesAsString
        );
    }
}
