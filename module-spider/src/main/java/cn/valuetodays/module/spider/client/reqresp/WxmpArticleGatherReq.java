package cn.valuetodays.module.spider.client.reqresp;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-08-03
 */
@Data
public class WxmpArticleGatherReq implements Serializable {
    @NotBlank(message = "url不能为空，该url需要抓包获取")
    private String url;
    private String lastArticleId;
    private boolean useLastArticleIdInRedis;

}
