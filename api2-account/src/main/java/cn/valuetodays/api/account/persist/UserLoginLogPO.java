package cn.valuetodays.api.account.persist;

import cn.valuetodays.api.account.enums.UserLoginLogEnums;
import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 用户登录日志表
 *
 * @author lei.liu
 * @since 2020-07-22 13:32
 */
@Cacheable
@Table(name = "account_user_login_log")
@Cache(region = "orm.UserLoginLog", usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class UserLoginLogPO extends JpaCrudLongIdBasePersist {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserLoginLogEnums.Status status;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "user_agent")
    private String userAgent;
    @Column(name = "reason")
    private String reason;
}
