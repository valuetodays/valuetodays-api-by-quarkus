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
@Table(name = "spider_wxmp_author")
@JsonIgnoreProperties(
    ignoreUnknown = true,
    value = {"hibernateLazyInitializer", "handler", "fieldHandler", "$$_hibernate_interceptor"}
)
@Data
public class WxmpAuthorPO extends JpaCrudLongIdBasePersist {
    @Column(name = "name")
    private String name;
    @Column(name = "wx_author_id")
    private String wxAuthorId;
    @Column(name = "last_req_time")
    private LocalDateTime lastReqTime;
}
