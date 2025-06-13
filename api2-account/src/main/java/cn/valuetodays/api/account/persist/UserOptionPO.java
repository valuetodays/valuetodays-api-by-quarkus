package cn.valuetodays.api.account.persist;

import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lei.liu
 * @since 2019-10-22 16:00
 */
@Entity
@Table(name = "account_user_option")
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class UserOptionPO extends JpaCrudLongIdBasePersist {

    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long id;
    @Column(name = "month_expense_limit", nullable = false)
    private int monthExpenseLimit;

    @Override
    public Long getId() {
        return id;
    }
}
