package cn.valuetodays.api.account.reqresp;

import java.util.ArrayList;
import java.util.List;

import cn.valuetodays.api.account.persist.AuthActionPO;
import lombok.Data;

/**
 * .
 *
 * @author lei.liu
 * @since 2020-12-17 11:45
 */
@Data
public class AuthActionTreeVoWithAuthMenu {

    private List<AuthMenu1> menuAndActionList = new ArrayList<>();

    public static AuthMenu1 newAuthMenu1() {
        return new AuthMenu1();
    }

    public static AuthMenu2 newAuthMenu2() {
        return new AuthMenu2();
    }

    @Data
    public static class AuthMenu1 {
        private Long id;
        private String name;
        private List<AuthMenu2> children;
    }

    @Data
    public static class AuthMenu2 {
        private Long id;
        private String name;
        private List<AuthActionPO> actionList;
    }
}

