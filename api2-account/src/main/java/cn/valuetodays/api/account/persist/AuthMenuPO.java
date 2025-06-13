package cn.valuetodays.api.account.persist;

import java.util.List;
import java.util.Set;

import cn.valuetodays.api.account.enums.AuthMenuEnums;
import cn.valuetodays.api2.client.bases.jpa.SetLongJsonAttributeConverter;
import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Entity
@Table(name = "account_auth_menu")
@JsonIgnoreProperties(
    ignoreUnknown = true,
    value = {"hibernateLazyInitializer", "handler", "fieldHandler", "$$_hibernate_interceptor"}
)
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthMenuPO extends JpaCrudLongIdBasePersist {
    public static final String PRODUCT_ADMIN = "ADMIN";

    @Column(name = "name")
    private String name;
    @Column(name = "product")
    @Enumerated(EnumType.STRING)
    private AuthMenuEnums.Product product;
    @Column(name = "app_code")
    private String appCode;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "location")
    @Enumerated(EnumType.STRING)
    private AuthMenuEnums.Location location;
    @Column(name = "icon")
    private String icon;
    @Column(name = "spa_icon")
    private String spaIcon;
    @Column(name = "domain")
    private String domain;
    @Column(name = "route")
    private String route;
    @Column(name = "spa_route")
    private String spaRoute;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthMenuEnums.Status status;
    @Column(name = "order_num", nullable = false)
    private int orderNum;
    @Column(name = "depends")
    @Convert(converter = SetLongJsonAttributeConverter.class)
    private Set<Long> depends;
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Transient
    private List<AuthMenuPO> children;

}
