package cn.valuetodays.api2.basic.service;

import java.util.List;

import cn.valuetodays.api2.basic.service.vocechat.AutoReplyContent;
import cn.valuetodays.api2.basic.vo.PushBaseReq;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-07-05
 */
@ApplicationScoped
@Slf4j
public class KeywordsModuleImpl {
    @Inject
    Instance<AutoReplyContent> autoReplyContents;

    public Pair<PushBaseReq.ContentType, String> replyKeywords(String content) {
        for (final AutoReplyContent e : autoReplyContents) {
            List<String> titles = e.title();
            for (final String title : titles) {
                if (StringUtils.startsWith(content, title)) {
                    String value = StringUtils.substringAfterLast(content, title);
                    return e.replyContent(value);
                }
            }
        }
        return AutoReplyContent.makePlainText(content);
    }


}
