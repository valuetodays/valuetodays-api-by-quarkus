package cn.valuetodays.module.spider.client.persist;

import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-08-03
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "spider_wxmp_article")
@JsonIgnoreProperties(
    ignoreUnknown = true,
    value = {"hibernateLazyInitializer", "handler", "fieldHandler", "$$_hibernate_interceptor"}
)
@Data
public class WxmpArticlePO extends JpaCrudLongIdBasePersist {
    @Column(name = "__biz")
    private String biz;
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "is_paid")
    private int paid;
    @Column(name = "title")
    private String title;
    @Column(name = "is_valid_html")
    private int validHtml;
    @Column(name = "html_content")
    private String htmlContent;
    @Column(name = "url")
    private String url;
    @Column(name = "mid")
    private String mid;
    @Column(name = "publish_time")
    private LocalDateTime publishTime;
}
