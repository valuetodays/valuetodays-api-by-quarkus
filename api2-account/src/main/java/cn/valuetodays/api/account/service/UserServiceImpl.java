package cn.valuetodays.api.account.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import cn.valuetodays.api.account.dao.UserDAO;
import cn.valuetodays.api.account.dao.UserLoginLogDAO;
import cn.valuetodays.api.account.dao.UserRoleDAO;
import cn.valuetodays.api.account.enums.UserLoginLogEnums;
import cn.valuetodays.api.account.persist.UserLoginLogPO;
import cn.valuetodays.api.account.persist.UserPO;
import cn.valuetodays.api.account.persist.UserRolePO;
import cn.valuetodays.api.account.reqresp.AccountBO;
import cn.valuetodays.api.account.reqresp.AssignRoleReq;
import cn.valuetodays.api.account.reqresp.LoginBO;
import cn.valuetodays.api.account.reqresp.LoginByIdBO;
import cn.valuetodays.api.account.reqresp.LoginByOpenidBO;
import cn.valuetodays.api2.web.common.IVtNatsClient;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import cn.vt.encrypt.BCryptUtils;
import cn.vt.exception.AssertUtils;
import cn.vt.util.ConvertUtils;
import cn.vt.util.ConvertUtils2;
import cn.vt.util.MyUUIDGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.springframework.lang.Nullable;

/**
 * @author lei.liu
 * @since 2019-10-22 16:50
 */
@ApplicationScoped
public class UserServiceImpl extends BaseCrudService<Long, UserPO, UserDAO> {
    public static final String TOPIC_APPLICATION_MSG = "applicationmsg";
    @Inject
    IVtNatsClient vtNatsClient;
    @Inject
    private UserDAO userDAO;
    @Inject
    private UserLoginLogDAO userLoginLogDAO;
    @Inject
    private UserRoleDAO userRoleDAO;

    public UserDAO getRepository() {
        return userDAO;
    }

    @Transactional
    public AccountBO login(LoginBO loginBO) {
        String mobile = loginBO.getMobile();
        String password = loginBO.getPassword();
        String userAgent = loginBO.getUserAgent();

        UserPO loginUser = getRepository().findByAccount(mobile);
        return loginByPO(loginUser, userAgent, mobile, password, true);
    }

    /**
     * @param loginUser     从数据库中查到的对象
     * @param userAgent     user agent
     * @param usernameUsed  登录时使用的用户名
     * @param passwordUsed  登录时使用的密码
     * @param checkPassword 是否校验密码，sso登录时不校验
     */
    @Transactional
    public AccountBO loginByPO(@Nullable UserPO loginUser,
                               String userAgent, String usernameUsed, String passwordUsed,
                               boolean checkPassword) {
        UserLoginLogPO userLoginLogPO = new UserLoginLogPO();
        userLoginLogPO.setUserAgent(userAgent);
        userLoginLogPO.setName(usernameUsed);
        userLoginLogPO.setPassword(passwordUsed);

        if (Objects.isNull(loginUser)) {
            userLoginLogPO.setStatus(UserLoginLogEnums.Status.FAIL);
            userLoginLogPO.initUserIdAndTime(-1L);
            userLoginLogPO.setReason("用户名错误");
            doWhenLoginFailed(userLoginLogPO);
            return null;
        }

        userLoginLogPO.initUserIdAndTime(loginUser.getId());
        if (checkPassword) {
            String passwordStored = loginUser.getPassword();
            boolean passwordIsRight = BCryptUtils.checkpw(passwordUsed, passwordStored);
            if (!passwordIsRight) {
                userLoginLogPO.setStatus(UserLoginLogEnums.Status.FAIL);
                userLoginLogPO.setReason("密码错误");
                doWhenLoginFailed(userLoginLogPO);
                return null;
            }
        }

        userLoginLogPO.setStatus(UserLoginLogEnums.Status.SUC);
        // 登录成功后不要记录密码
        userLoginLogPO.setPassword("-");
        doWhenLoginSuccess(userLoginLogPO);

        AccountBO accountBO = ConvertUtils2.convertObj(loginUser, AccountBO.class);
        accountBO.setToken(MyUUIDGenerator.uuidTo16());
        return accountBO;
    }

    private void doWhenLoginSuccess(UserLoginLogPO userLoginLogPO) {
        String msg = "[valuetodays-api] login success. userName:" + userLoginLogPO.getName();
        vtNatsClient.publish(TOPIC_APPLICATION_MSG, msg);
        userLoginLogDAO.persist(userLoginLogPO);
    }

    private void doWhenLoginFailed(UserLoginLogPO userLoginLogPO) {

        String msg = "[valuetodays-api] login failed. userName:" + userLoginLogPO.getName() + ", reason: " + userLoginLogPO.getReason();
        vtNatsClient.publish(TOPIC_APPLICATION_MSG, msg);
        userLoginLogDAO.persist(userLoginLogPO);
    }

    @Transactional
    public AccountBO loginById(LoginByIdBO loginByIdBO) {
        Long id = loginByIdBO.getId();
        String userAgent = loginByIdBO.getUserAgent();
        UserPO byId = findById(id);
        return loginByPO(byId, userAgent, "SSO", "SSO", false);
    }

    public AccountBO loginByWxOpenid(LoginByOpenidBO loginByOpenidBO) {
        String openid = loginByOpenidBO.getOpenid();
        String userAgent = loginByOpenidBO.getUserAgent();

        UserLoginLogPO userLoginLogPO = new UserLoginLogPO();
        userLoginLogPO.setUserAgent(userAgent);
        userLoginLogPO.setName("by_openid");
        userLoginLogPO.setPassword(openid);

        UserPO loginUser = getRepository().findByWxOpenid(openid);
        if (Objects.isNull(loginUser)) {
            userLoginLogPO.setStatus(UserLoginLogEnums.Status.FAIL);
            userLoginLogPO.setCreateUserId(-1L);
            userLoginLogPO.setUpdateUserId(userLoginLogPO.getCreateUserId());
            userLoginLogPO.setReason("用户名错误");
//            publishEvent(new AccountLoginLogEvent(userLoginLogPO));
            return null;
        }
        userLoginLogPO.setCreateUserId(loginUser.getId());
        userLoginLogPO.setUpdateUserId(userLoginLogPO.getCreateUserId());
        userLoginLogPO.setStatus(UserLoginLogEnums.Status.SUC);
//        publishEvent(new AccountLoginLogEvent(userLoginLogPO));
        return ConvertUtils.convertObj(loginUser, AccountBO.class);
    }

    public UserPO findByWxOpenid(String openid) {
        AssertUtils.assertStringNotBlank(openid, "openid不能为空");
        return getRepository().findByWxOpenid(openid);
    }

    public List<UserPO> findAllByScope(long scope) {
        return null;
//        return getRepository().findAllByScope(scope);
    }

    public boolean assignRole(AssignRoleReq req) {
        UserRolePO old = userRoleDAO.findByUserId(req.getId());
        if (Objects.isNull(old)) {
            old = new UserRolePO();
            old.setUserId(req.getId());
        }
        old.setAdminRoleId(req.getAdminRoleId());
        old.setCreateUserId(req.getLoginId());
        old.setUpdateUserId(req.getLoginId());
        old.setCreateTime(LocalDateTime.now());
        old.setUpdateTime(old.getCreateTime());
        userRoleDAO.persist(old);
        return true;
    }
}
