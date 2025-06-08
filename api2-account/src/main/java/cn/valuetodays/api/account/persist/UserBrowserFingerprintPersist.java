package cn.valuetodays.api.account.persist;

import cn.valuetodays.quarkus.commons.base.jpa.JpaAccountableLongIdEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户-浏览器指纹表
 *
 * @author lei.liu
 * @since 2024-12-04 18:38
 */
@Table(name = "account_user_browser_fingerprint")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true /*, value = {"hibernateLazyInitializer", "handler"}*/)
@EqualsAndHashCode(callSuper = true)
@Data
public class UserBrowserFingerprintPersist extends JpaAccountableLongIdEntity {
    @Column(name = "title")
    private String title;
    @Column(name = "fingerprint")
    private String fingerprint;
}
