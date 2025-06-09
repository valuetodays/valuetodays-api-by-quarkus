package cn.valuetodays.api2.basic.dao;

import cn.valuetodays.api2.basic.enums.CommonEnums;
import cn.valuetodays.api2.basic.persist.RsaKeyPairPersist;
import cn.vt.util.DateUtils;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2025-06-09 19:57
 */
@ApplicationScoped
public class RsaKeyPairRepository implements PanacheRepository<RsaKeyPairPersist> {

    public void disableAllKeyPairsBeforeToday() {
        update("enableStatus = ?1 where createTime <= ?2",
            CommonEnums.EnableStatus.NO,
            DateUtils.minusDaysToday(1).toLocalDate().atStartOfDay()
        );
    }

    public void deleteOldKeyPairsBefore(int n) {
        delete(" createTime <=?1 ",
            DateUtils.minusDaysToday(n).toLocalDate().atStartOfDay()
        );
    }
}

