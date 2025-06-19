package cn.valuetodays.api2.extra.persist;

import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 我的链接
 *
 * @author lei.liu
 * @since 2020-09-07 17:56
 */
@Table(name = "my_link")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(callSuper = true)
@Data
public class MyLinkPO extends JpaCrudLongIdBasePersist {
    @Column(name = "user_id", updatable = false)
    private Long userId;
    @Column(name = "title")
    private String title;
    @Column(name = "url")
    private String url;
    @Column(name = "fav_icon")
    private String favIcon;
    @Column(name = "level")
    private Integer level;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "use_status")
    private Integer useStatus;
}
