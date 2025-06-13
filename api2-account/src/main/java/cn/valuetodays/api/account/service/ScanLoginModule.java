package cn.valuetodays.api.account.service;

import java.util.Map;
import java.util.Objects;

import cn.valuetodays.api.account.persist.UserPO;
import cn.valuetodays.api.account.reqresp.AccountBO;
import cn.valuetodays.api.account.reqresp.LoginByOpenidBO;
import cn.vt.core.TitleCapable;
import cn.vt.exception.AssertUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.LRUMap;

/**
 * 账号管理器（扫码）
 *
 * @author lei.liu
 */
@ApplicationScoped
@Slf4j
public class ScanLoginModule {
    private static final String PRESENT_OPENID = "-";
    private final Map<String, ScanLoginObj> map = new LRUMap<>(100);
    @Inject
    UserServiceImpl userService;

    public boolean prepareLogin(String uid) {
        if (!map.containsKey(uid)) {
            map.put(uid, ScanLoginObj.prepare());
            log.info("prepareLogin, uid: {}", uid);
            return true;
        }
        return false;
    }

    public boolean scanForLogin(String uid, String openid) {
        if (map.containsKey(uid)) {
            map.put(uid, ScanLoginObj.scan(openid));
            log.info("wxscan login, openid: {}, uid: {}", openid, uid);
            return true;
        }
        return false;
    }

    public boolean confirmLogin(String uid, String openid) {
        if (map.containsKey(uid)) {
            map.put(uid, ScanLoginObj.confirm(openid));
            log.info("wxscan confirm login, openid: {}, uid: {}", openid, uid);
            return true;
        }
        return false;
    }

    public boolean cancelLogin(String uid) {
        if (map.containsKey(uid)) {
            map.remove(uid);
            log.info("cancelLogin login, uid: {}", uid);
            return true;
        }
        return false;
    }

    public AccountBO checkLogin(String uid) {
        ScanLoginObj scanLoginObj = map.get(uid);
        if (Objects.isNull(scanLoginObj)) {
            return null;
        }
        String openid = scanLoginObj.getOpenid();
        boolean valid = !Objects.equals(openid, PRESENT_OPENID);
        if (valid) {
            LoginByOpenidBO req = LoginByOpenidBO.builder().openid(openid).userAgent("TEST_USER_AGENT").build();
            return userService.loginByWxOpenid(req);
        }
        return null;
    }

    public ScanLoginStatus checkStatus(String uid) {
        ScanLoginObj scanLoginObj = map.get(uid);
        if (Objects.isNull(scanLoginObj)) {
            return null;
        }
        return scanLoginObj.getScanLoginStatus();
    }

    @Transactional
    public boolean bind(Long userId, String openid) {
        UserPO byWxOpenid = userService.findByWxOpenid(openid);
        // 1. 将openid从原用户取消绑定
        if (Objects.nonNull(byWxOpenid)) {
            byWxOpenid.setWxOpenid(null);
            userService.save(byWxOpenid);
        }
        // 2. 将openid从绑定到新用户上
        UserPO user = userService.findById(userId);
        AssertUtils.assertNotNull(user);
        user.setWxOpenid(openid);
        userService.save(user);
        return true;
    }

    @Getter
    public enum ScanLoginStatus implements TitleCapable {
        WAIT_FOR_SCAN("等待扫码"),
        SCANED("已扫码"),
        CONFIRMED("已确认");

        private final String title;

        ScanLoginStatus(String title) {
            this.title = title;
        }
    }

    @Data
    public static class ScanLoginObj {
        private ScanLoginStatus scanLoginStatus;
        private String openid;

        public static ScanLoginObj prepare() {
            ScanLoginObj scanLoginObj = new ScanLoginObj();
            scanLoginObj.setScanLoginStatus(ScanLoginStatus.WAIT_FOR_SCAN);
            scanLoginObj.setOpenid(PRESENT_OPENID);
            return scanLoginObj;
        }

        public static ScanLoginObj scan(String openid) {
            ScanLoginObj scanLoginObj = new ScanLoginObj();
            scanLoginObj.setScanLoginStatus(ScanLoginStatus.SCANED);
            scanLoginObj.setOpenid(openid);
            return scanLoginObj;
        }

        public static ScanLoginObj confirm(String openid) {
            ScanLoginObj scanLoginObj = new ScanLoginObj();
            scanLoginObj.setScanLoginStatus(ScanLoginStatus.CONFIRMED);
            scanLoginObj.setOpenid(openid);
            return scanLoginObj;
        }
    }
}
