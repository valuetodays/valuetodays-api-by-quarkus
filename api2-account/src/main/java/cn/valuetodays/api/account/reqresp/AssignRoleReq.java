package cn.valuetodays.api.account.reqresp;

import cn.valuetodays.api2.web.common.base.BaseLoginIdReq;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssignRoleReq extends BaseLoginIdReq {
    private Long id;
    @NotNull(message = "角色不能为空")
    private Long adminRoleId;
}

