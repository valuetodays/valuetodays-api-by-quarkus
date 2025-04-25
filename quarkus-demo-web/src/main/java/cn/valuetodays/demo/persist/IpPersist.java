package cn.valuetodays.demo.persist;

import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ip")
public class IpPersist extends JpaCrudLongIdBasePersist {

    private String ip;
}
