package cn.valuetodays.module.spider.service.impl.wxmp.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-07-29
 */
@Data
public class WxmpGetArticlesResp implements Serializable {
    private String max_article_id;
    private BaseResp base_resp;
    private List<Article> articles;
}
