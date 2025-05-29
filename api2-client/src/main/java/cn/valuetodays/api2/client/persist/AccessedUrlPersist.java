package cn.valuetodays.api2.client.persist;

import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 访问过的url
 *
 * @author lei.liu
 * @since 2025-03-27 16:01
 */
@Cacheable
@Table(name = "t_accessed_url")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true /*, value = {"hibernateLazyInitializer", "handler"}*/)
@EqualsAndHashCode(callSuper = true)
@Data
public class AccessedUrlPersist extends JpaCrudLongIdBasePersist {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "access_at")
    private LocalDateTime accessAt;
    @Column(name = "url")
    private String url;
}
