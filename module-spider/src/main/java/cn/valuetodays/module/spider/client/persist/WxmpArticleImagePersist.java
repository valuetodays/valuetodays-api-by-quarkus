package cn.valuetodays.module.spider.client.persist;

import cn.valuetodays.api2.client.enums.WxmpArticleImageEnums;
import cn.valuetodays.quarkus.commons.base.jpa.JpaLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 微信公众号文章
 *
 * @author lei.liu
 * @since 2025-04-08 00:08
 */
@Table(name = "spider_wxmp_article_image")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true /*, value = {"hibernateLazyInitializer", "handler"}*/)
@EqualsAndHashCode(callSuper = true)
@Data
public class WxmpArticleImagePersist extends JpaLongIdBasePersist {

    @Column(name = "url")
    private String url;
    @Column(name = "title")
    private String title;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private WxmpArticleImageEnums.Status status;
    @Column(name = "begin_time")
    private LocalDateTime beginTime;
    @Column(name = "finish_time")
    private LocalDateTime finishTime;
    @Column(name = "last_file_url")
    private String lastFileUrl;

}
