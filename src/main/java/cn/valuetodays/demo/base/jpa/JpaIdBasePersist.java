package cn.valuetodays.demo.base.jpa;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * entity基类
 */
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public abstract class JpaIdBasePersist<I extends Serializable> extends JpaBasePersist<I> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private I id;
}
