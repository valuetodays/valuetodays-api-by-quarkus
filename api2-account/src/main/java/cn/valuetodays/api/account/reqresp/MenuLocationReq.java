package cn.valuetodays.api.account.reqresp;

import java.io.Serializable;

import cn.valuetodays.api.account.enums.AuthMenuEnums;
import lombok.Data;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-02-01
 */
@Data
public class MenuLocationReq implements Serializable {
    private AuthMenuEnums.Product product;
    private Long userId;
    private boolean onlyQueryNormalStatus;
}
