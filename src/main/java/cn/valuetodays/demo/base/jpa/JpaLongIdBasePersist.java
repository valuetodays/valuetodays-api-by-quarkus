package cn.valuetodays.demo.base.jpa;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * entity基类
 */
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public abstract class JpaLongIdBasePersist extends JpaIdBasePersist<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
