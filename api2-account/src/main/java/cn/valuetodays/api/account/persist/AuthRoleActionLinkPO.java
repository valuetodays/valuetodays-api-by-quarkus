package cn.valuetodays.api.account.persist;

import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色-动作
 *
 * @author lei.liu
 * @since 2020-09-24 10:14
 */
@Table(name = "account_auth_role_action_link")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthRoleActionLinkPO extends JpaCrudLongIdBasePersist {

    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "action_id")
    private Long actionId;
    @Column(name = "create_user_id")
    private Long createUserId;
    @Column(name = "update_user_id")
    private Long updateUserId;
}
