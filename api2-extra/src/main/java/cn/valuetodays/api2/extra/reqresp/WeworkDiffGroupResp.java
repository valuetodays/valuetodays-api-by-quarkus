package cn.valuetodays.api2.extra.reqresp;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lei.liu
 * @since 2023-06-20
 */
@Data
public final class WeworkDiffGroupResp implements Serializable {
    private List<GroupUserVo> decreaseList;
    private List<GroupUserVo> increaseList;

    private WeworkDiffGroupResp() {
    }

    public static WeworkDiffGroupResp empty() {
        WeworkDiffGroupResp groupUserDiffVo = new WeworkDiffGroupResp();
        groupUserDiffVo.setDecreaseList(new ArrayList<>(0));
        groupUserDiffVo.setIncreaseList(new ArrayList<>(0));
        return groupUserDiffVo;
    }

    public static WeworkDiffGroupResp of(List<GroupUserVo> decreaseList, List<GroupUserVo> increaseList) {
        WeworkDiffGroupResp groupUserDiffVo = new WeworkDiffGroupResp();
        groupUserDiffVo.setDecreaseList(decreaseList);
        groupUserDiffVo.setIncreaseList(increaseList);
        return groupUserDiffVo;
    }

    @Data
    public static class GroupUserVo implements Serializable {
        private String email;
        private String name;
        private String inDateStr;
        private String outDateStr;
    }

}
