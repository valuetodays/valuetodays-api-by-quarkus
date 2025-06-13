package cn.valuetodays.api.account.persist;

import java.util.Set;

import cn.valuetodays.api2.client.bases.jpa.SetLongJsonAttributeConverter;
import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Formula;

/**
 * 动作
 *
 * @author lei.liu
 * @since 2020-09-24 10:14
 */
@Table(name = "account_auth_action")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true /*, value = {"hibernateLazyInitializer", "handler"}*/)
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthActionPO extends JpaCrudLongIdBasePersist {

    @Column(name = "menu_id")
    private Long menuId;
    @Column(name = "name")
    private String name;
    @Column(name = "depends")
    @Convert(converter = SetLongJsonAttributeConverter.class)
    private Set<Long> depends;
    @Column(name = "order_num")
    private Integer orderNum;

    @Formula(value = "(select t.name from ty_auth_menu t where t.id = menu_id)")
    private String menuName;
}
