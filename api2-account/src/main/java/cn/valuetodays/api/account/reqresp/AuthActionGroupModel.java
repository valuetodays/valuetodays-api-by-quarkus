package cn.valuetodays.api.account.reqresp;

import java.util.List;

import lombok.Data;

/**
 * .
 *
 * @author lei.liu
 * @since 2020-09-28 10:18
 */
@Data
public class AuthActionGroupModel {
    private String menuName;
    private List<Action> actionList;

    @Data
    public static class Action {
        private Long id;
        private String name;
        private String dependsString;
    }

}

