package cn.valuetodays.api.account.reqresp;

import java.util.List;

import cn.valuetodays.api2.web.common.base.BaseLoginIdReq;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssignActionReq extends BaseLoginIdReq {
    @NotNull(message = "角色不能为空")
    private Long roleId;
    @NotEmpty(message = "动作列表不能为空")
    private List<Long> actionIdList;
}

