package cn.valuetodays.api2.basic.service;

import cn.valuetodays.api2.basic.RedisKeyConstant;
import cn.valuetodays.api2.basic.dao.SmsLogDAO;
import cn.valuetodays.api2.basic.enums.SmsLogEnums;
import cn.valuetodays.api2.basic.persist.SmsLogPO;
import cn.valuetodays.api2.basic.vo.SendSmsBaseIO;
import cn.valuetodays.api2.basic.vo.SendSmsCodeIO;
import cn.valuetodays.api2.basic.vo.SendSmsCodeVO;
import cn.valuetodays.api2.basic.vo.SendSmsIO;
import cn.valuetodays.api2.basic.vo.SmsResultVO;
import cn.valuetodays.api2.basic.vo.VerifySmsCodeIO;
import cn.valuetodays.api2.web.common.ProfileUtils;
import cn.vt.exception.AssertUtils;
import cn.vt.util.JsonUtils;
import cn.vt.util.Randoms;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.SetArgs;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

/**
 * @author liulei
 * @since 2017-11-14 16:32
 */
@ApplicationScoped
@Slf4j
public class SmsServiceImpl {

    @Inject
    ProfileUtils profileUtils;
    @Inject
    RedisDataSource basicCache;
    @Inject
    SmsLogDAO smsLogDAO;

    private String getSmscode() {
        int i = Randoms.RANDOM.nextInt(9000) + 1000;
        return String.valueOf(i);
    }

    public SmsResultVO sendCode(SendSmsCodeIO sendSmsCodeIO) {
        SmsResultVO r = checkBaseParam(sendSmsCodeIO);
        if (r != null) {
            return r;
        }
        r = checkAuthCode(sendSmsCodeIO);
        if (r != null) {
            return r;
        }

        String mobile = sendSmsCodeIO.getMobile();

        // 上次发送时间在90s内
        String oldSmscodeObj = basicCache.value(String.class).get(RedisKeyConstant.MOBILE_SMSCODE + mobile);
        if (oldSmscodeObj != null) {
            SendSmsCodeVO oldSmscode = JsonUtils.fromJson(oldSmscodeObj, SendSmsCodeVO.class);
            long createAt = oldSmscode.getCreateAt();
            long offset = System.currentTimeMillis() - createAt;
            if (offset < 90 * 1000) { // 90s
                return SmsResultVO.fail("您发送验证码过于频繁，请稍后再试。");
            }
        }
        String smscode = getSmscode();
        String content = "验证码：" + smscode;

        try {
            sendSmsAndSaveToMysql(mobile, content);

            SetArgs setArgs = new SetArgs().ex(Duration.ofMinutes(15));
            basicCache.value(String.class).set(
                RedisKeyConstant.MOBILE_SMSCODE + mobile,
                JsonUtils.toJson(new SendSmsCodeVO(smscode)),
                setArgs);
            // 开发环境将短信验证码返回
            String returnSmscode = StringUtils.EMPTY;
            if (profileUtils.isDev()) {
                returnSmscode = smscode;
            }
            return SmsResultVO.success(returnSmscode);
        } catch (Exception e) {
            log.error("error when express sms.", e);
        }

        return SmsResultVO.fail("发送短信异常");
    }

    private SmsResultVO checkAuthCode(SendSmsCodeIO sendSmsCodeIO) {
        if (sendSmsCodeIO.shouldIgnore()) {
            return null;
        }
        String token = sendSmsCodeIO.getImgToken();

        String authcode = sendSmsCodeIO.getAuthcode();
        if (StringUtils.isEmpty(authcode)) {
            return SmsResultVO.fail("图片验证码不应为空");
        }

        String key = RedisKeyConstant.AUTHCODE_KEY + token;
        String cachedAuthcode = basicCache.value(String.class).get(key);
        if (cachedAuthcode == null) {
            return SmsResultVO.fail("图片验证码已过期");
        }
        if (StringUtils.isEmpty(cachedAuthcode)) {
            return SmsResultVO.fail("图片验证码已过期");
        }
        if (!cachedAuthcode.equals(authcode)) {
            return SmsResultVO.fail("图片验证码不正确");
        }

        basicCache.key(String.class).del(key);
        return null;
    }

    public SmsResultVO sendSms(SendSmsIO sendSmsIO) {
        SmsResultVO r = checkBaseParam(sendSmsIO);
        if (r != null) {
            return r;
        }
        String mobile = sendSmsIO.getMobile();
        String content = sendSmsIO.getContent();
        AssertUtils.assertStringNotBlank(content, "content");

        Pair<SmsLogPO, SmsUtil.SmsResult> tuple2 = sendSmsAndSaveToMysql(mobile, content);
        SmsUtil.SmsResult t2 = tuple2.getRight();
        if (t2.isSuccess()) {
            return SmsResultVO.success("发送短信正常");
        } else {
            return SmsResultVO.fail("发送短信异常：" + t2.getResult());
        }
    }

    private Pair<SmsLogPO, SmsUtil.SmsResult> sendSmsAndSaveToMysql(String mobile, String content) {
        SmsUtil.SmsResult smsResult = SmsUtil.sendSms(mobile, content);
        SmsLogPO smsLogPO = new SmsLogPO();
        smsLogPO.setStatus(smsResult.isSuccess() ? SmsLogEnums.StatusEnum.SUC : SmsLogEnums.StatusEnum.FAIL);
        smsLogPO.setMobile(mobile);
        smsLogPO.setContent(smsResult.getResult());
        smsLogPO.initCreateTimeAndUpdateTime();
        smsLogPO.setCreateUserId(1L);
        smsLogPO.setUpdateUserId(smsLogPO.getCreateUserId());
        smsLogDAO.persist(smsLogPO);
        return Pair.of(smsLogPO, smsResult);
    }

    private SmsResultVO checkMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return SmsResultVO.fail("手机号码不应为空");
        }
        if (!StringUtils.isNumeric(mobile)) {
            return SmsResultVO.fail("手机号码不合法");
        }
        if (mobile.length() != 11) {
            return SmsResultVO.fail("手机号码不合法");
        }
        return null;
    }

    public SmsResultVO verifyCode(VerifySmsCodeIO verifySmsCodeIO) {
        String mobile = verifySmsCodeIO.getMobile();
        SmsResultVO r = checkMobile(mobile);
        if (r != null) {
            return r;
        }

        String oldSmscodeObj = basicCache.value(String.class).get(RedisKeyConstant.MOBILE_SMSCODE + mobile);
        if (oldSmscodeObj == null) {
            return SmsResultVO.fail("手机验证码已过期，请重新发送。");
        }
        SendSmsCodeVO oldSmscode = JsonUtils.fromJson(oldSmscodeObj, SendSmsCodeVO.class);
        if (oldSmscode == null) {
            return SmsResultVO.fail("手机验证码已过期，请重新发送。");
        }
        String code = verifySmsCodeIO.getSmscode();
        if (Objects.equals(code, oldSmscode.getCode())) {
            basicCache.key(String.class).del(RedisKeyConstant.MOBILE_SMSCODE + mobile);
            String token = UUID.randomUUID().toString();
            SetArgs exArgs = new SetArgs().ex(Duration.ofMinutes(5));
            basicCache.value(String.class).set(RedisKeyConstant.RESET_PASSWORD + mobile, token, exArgs);
            return SmsResultVO.success(token);
        }

        return SmsResultVO.fail("手机验证码不正确，请重新发送。");
    }

    private SmsResultVO checkBaseParam(SendSmsBaseIO sendSmsBaseIO) {
        if (sendSmsBaseIO.shouldIgnore()) {
            return null;
        }
        String mobile = sendSmsBaseIO.getMobile();
        return checkMobile(mobile);
    }

}
