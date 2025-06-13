package cn.valuetodays.api.account.persist;

import cn.valuetodays.api.account.enums.AuthRoleEnums;
import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色
 *
 * @author lei.liu
 * @since 2020-09-24 10:14
 */
@Table(name = "account_auth_role")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthRolePO extends JpaCrudLongIdBasePersist {

    @Column(name = "product")
    @Enumerated(EnumType.STRING)
    private AuthRoleEnums.Product product;
    @Column(name = "name")
    private String name;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AuthRoleEnums.Status status;
    @Column(name = "order_num")
    private Integer orderNum;
}
