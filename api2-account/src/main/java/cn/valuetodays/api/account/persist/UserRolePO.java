package cn.valuetodays.api.account.persist;

import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户-角色表
 *
 * @author lei.liu
 * @since 2020-09-29 11:06
 */
@Table(name = "account_user_role")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRolePO extends JpaCrudLongIdBasePersist {

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "admin_role_id")
    private Long adminRoleId;
}
