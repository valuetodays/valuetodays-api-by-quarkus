package cn.valuetodays.api2.web.basic.push.vocechat;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;


/**
 * 自动回复.
 * 用于vocechat.
 *
 * @author lei.liu
 * @since 2024-11-28
 */
public interface AutoReplyContent {
    static Pair<PushBaseReq.ContentType, String> makePlainText(String text) {
        return Pair.of(PushBaseReq.ContentType.PLAIN_TEXT, text);
    }

    static Pair<PushBaseReq.ContentType, String> makeFile(String filePath) {
        return Pair.of(PushBaseReq.ContentType.FILE, filePath);
    }

    List<String> title();

    default List<String> sampleValue() {
        return title();
    }

    Pair<PushBaseReq.ContentType, String> replyContent(String value);
}
