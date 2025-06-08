package cn.valuetodays.api.account.reqresp;

import cn.valuetodays.api.account.enums.UserEnums;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AccountBO implements Serializable {
    public static final AccountBO EMPTY = new AccountBO();
    private Long id;
    //    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private Long createUserId;
    private Long updateUserId;
    private String createUserName;
    private String updateUserName;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 个性签名
     */
    private String hello;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String headimg;
    private UserEnums.Status status;
    /**
     * token
     */
    private String token;
}
