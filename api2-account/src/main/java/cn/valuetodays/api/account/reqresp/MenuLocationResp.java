package cn.valuetodays.api.account.reqresp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.valuetodays.api.account.persist.AuthMenuPO;
import lombok.Data;

/**
 * @author lei.liu
 * @since 2019-11-27 10:21
 */
@Data
public class MenuLocationResp implements Serializable {
    private Map<String, List<AuthMenuPO>> menus = new HashMap<>();

}
