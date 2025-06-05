package cn.valuetodays.module.spider.service.impl.wxmp.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-07-29
 */
@Data
public class Article implements Serializable {
    private String __biz;
    private String cover_url;
    private int is_paid;
    private int is_pay_subscribe;
    private String mid; // 文章id
    private long publish_time;
    private String title;
    private String url;

}
