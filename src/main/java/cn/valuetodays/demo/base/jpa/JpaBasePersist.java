package cn.valuetodays.demo.base.jpa;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;

/**
 * entity基类
 */
@MappedSuperclass
@Data
public abstract class JpaBasePersist<I extends Serializable> implements Serializable {

}
