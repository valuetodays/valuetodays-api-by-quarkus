package cn.valuetodays.api.account.persist;

import cn.valuetodays.api.account.enums.UserEnums;
import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.EnumUtils;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author lei.liu
 * @since 2019-10-22 16:02
 */
@Entity
@Table(name = "account_user")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPO extends JpaCrudLongIdBasePersist {

    public static final String DEFAULT_AVATAR = "/assets/avatar/0.png";

    /**
     * 昵称
     */
    @Column(name = "nick", nullable = false)
    private String nick;
    /**
     * 个性签名
     */
    @Column(name = "hello", nullable = false)
    private String hello;
    /**
     * 手机号
     */
    @Column(name = "mobile", nullable = false)
    private String mobile;
    /**
     * 邮箱
     */
    @Column(name = "email", nullable = false)
    private String email;
    /**
     * 密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;
    /**
     * 头像
     */
    @Column(name = "headimg", nullable = false)
    private String headimg;
    /**
     * 注册时间
     */
    @Column(name = "regist_time", updatable = false)
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registTime;
    /**
     * 注册终端,1pc,2ios,3android,
     */
    @Column(name = "reg_origin", updatable = false)
    @Enumerated(EnumType.STRING)
    private UserEnums.RegOrigin regOrigin;
    @Column(name = "status", updatable = false)
    @Enumerated(EnumType.STRING)
    private UserEnums.Status status;
    @Column(name = "site_scope")
    private Long siteScope;
    @Column(name = "articles")
    private Long articles;
    @Column(name = "last_visit_time", updatable = true)
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisitTime;
    @Column(name = "qq_openid")
    private String qqOpenid;
    @Column(name = "wx_openid")
    private String wxOpenid;
    @Transient
    private String roleName;

    @JsonProperty(value = "siteScopes", access = JsonProperty.Access.WRITE_ONLY)
    public Set<UserEnums.SiteScope> getSiteScopes() {
        if (siteScope == null) {
            return new HashSet<>();
        }
        return EnumUtils.processBitVector(UserEnums.SiteScope.class, siteScope);
    }

    @JsonIgnore
    public void setSiteScopes(EnumSet<UserEnums.SiteScope> set) {
        if (Objects.nonNull(set) && !set.isEmpty()) {
            siteScope = EnumUtils.generateBitVector(UserEnums.SiteScope.class, set);
        }
    }

}
