package cn.valuetodays.api2.extra.persist;

import java.time.LocalDateTime;

import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微博
 *
 * @author lei.liu
 * @since 2024-04-28 14:07
 */
@Table(name = "extra_weibo")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class WeiboPO extends JpaCrudLongIdBasePersist {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "mid")
    private String mid;
    @Column(name = "mblogid")
    private String mblogid;
    @Column(name = "edit_count")
    private Integer editCount;
    @Column(name = "source")
    private String source;
    @Column(name = "reposts_count")
    private Integer repostsCount;
    @Column(name = "comments_count")
    private Integer commentsCount;
    @Column(name = "attitudes_count")
    private Integer attitudesCount;
    @Column(name = "mblogtype")
    private Integer mblogtype;
    @Column(name = "html")
    private String html;
    @Column(name = "region_name")
    private String regionName;
    @Column(name = "created_time")
    private LocalDateTime createdTime;
}
