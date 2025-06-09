package cn.valuetodays.api2.basic.service;

import cn.valuetodays.api2.basic.dao.RsaKeyPairRepository;
import cn.valuetodays.api2.basic.enums.CommonEnums.EnableStatus;
import cn.valuetodays.api2.basic.persist.RsaKeyPairPersist;
import cn.valuetodays.quarkus.commons.base.BaseService;
import cn.vt.exception.AssertUtils;
import cn.vt.util.RSAUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;


/**
 * rsa公钥私钥对
 *
 * @author lei.liu
 * @since 2025-06-09 19:57
 */
@ApplicationScoped
@Slf4j
public class RsaKeyPairService
    extends BaseService<Long, RsaKeyPairPersist, RsaKeyPairRepository> {
    @Transactional
    public void deleteOldKeyPairs() {
        getRepository().deleteOldKeyPairsBefore(3);
    }

    @Transactional
    public void renewKeyPairs() {
        disableAllKeyPairsBeforeToday();
        createNewKeyPairs(3);
    }

    private void createNewKeyPairs(int count) {
        AssertUtils.assertTrue(count > 1, "count should be greater than 1");
        for (int i = 0; i < count; i++) {
            String[] arr = RSAUtils.genKeyPair();
            String pubKey = arr[0];
            String priKey = arr[1];
            RsaKeyPairPersist p = new RsaKeyPairPersist();
            p.setPriKey(priKey);
            p.setPubKey(pubKey);
            p.setKeyId(DigestUtils.md5Hex(pubKey));
            p.setEnableStatus(EnableStatus.YES);
            p.initUserIdAndTime(1L);
            getRepository().persist(p);
        }
    }

    private void disableAllKeyPairsBeforeToday() {
        getRepository().disableAllKeyPairsBeforeToday();
    }
}

