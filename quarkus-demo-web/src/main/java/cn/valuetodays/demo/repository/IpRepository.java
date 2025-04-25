package cn.valuetodays.demo.repository;


import cn.valuetodays.demo.persist.IpPersist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-29
 */
@Repository
public interface IpRepository extends JpaRepository<IpPersist, Long> {

}
