package cn.valuetodays.api2.basic.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * webhook接收的数据 - 请求对象.
 *
 * @author lei.liu
 * @since 2024-07-05
 */
@Data
public class VocechatWebhookReq implements Serializable {
    private Long created_at; // 1672048481664, 消息创建的时间戳
    private Integer from_uid; // 7910, 来自于谁
    private Long mid; // 2978, 消息ID
    private TargetVo target; // 发送给谁，gid代表是发送给频道，uid代表是发送给个人
    private DetailVo detail; // 详情

    @Data
    public static class DetailVo implements Serializable {
        private String content; // 消息内容
        // 消息类型，text/plain：纯文本消息，text/markdown：markdown消息，vocechat/file：文件类消息
        private String content_type;
        private Long expires_in; // 消息过期时长，如果有大于0数字，说明该消息是个限时消息
        // 一些有关消息的元数据，比如at信息，文件消息的具体类型信息，如果是个图片消息，还会有一些宽高，图片名称等元信息
        private Map<String, Object> properties;
        private String type; // 消息类型，normal代表是新消息
        private Map<String, Object> detail; // 部分功能里会有二级详情
    }

    @Data
    public static class TargetVo implements Serializable {
        private Integer gid;
        private Integer uid;
    }
}
