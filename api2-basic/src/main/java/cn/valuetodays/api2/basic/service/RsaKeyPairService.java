package cn.valuetodays.api2.basic.service;

import java.util.List;

import cn.valuetodays.api2.basic.dao.RsaKeyPairRepository;
import cn.valuetodays.api2.basic.persist.RsaKeyPairPersist;
import cn.valuetodays.api2.web.common.CommonEnums.EnableStatus;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import cn.vt.exception.AssertUtils;
import cn.vt.util.RSAUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;


/**
 * rsa公钥私钥对
 *
 * @author lei.liu
 * @since 2025-06-09 19:57
 */
@ApplicationScoped
@Slf4j
public class RsaKeyPairService
    extends BaseCrudService<Long, RsaKeyPairPersist, RsaKeyPairRepository> {

    public RsaKeyPairPersist findByKeyId(String keyId) {
        return getRepository().findByKeyId(keyId);
    }

    @Transactional
    public void deleteOldKeyPairs() {
        getRepository().deleteOldKeyPairsBefore(3);
    }

    @Transactional
    public void renewKeyPairs(int n) {
        disableAllKeyPairsBeforeToday();
        createNewKeyPairs(n < 1 ? 3 : n);
    }

    private void createNewKeyPairs(int count) {
        AssertUtils.assertTrue(count > 0, "count should be greater than 0");
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

    public RsaKeyPairPersist randomOne() {
        List<RsaKeyPairPersist> list = getRepository().findEnabled();
        AssertUtils.assertCollectionNotEmpty(list, "rsa_key_pair should not be empty");
        int n = RandomUtils.secure().randomInt(0, list.size());
        return list.get(n);
    }
}

