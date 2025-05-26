package cn.valuetodays.api2.extra.reqresp;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-06-20
 */
@Data
public class WeworkDiffGroupReq implements Serializable {
    private Long groupId1;
    private Long groupId2;
}

