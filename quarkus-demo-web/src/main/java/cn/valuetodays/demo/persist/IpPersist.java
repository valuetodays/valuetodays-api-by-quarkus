package cn.valuetodays.demo.persist;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import valuetodays.demo.commons.base.jpa.JpaLongIdBasePersist;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "test_ip")
public class IpPersist extends JpaLongIdBasePersist {

    private String ip;
}
