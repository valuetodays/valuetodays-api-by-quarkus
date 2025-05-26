package cn.valuetodays.api2.extra.reqresp;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-10-08
 */
@Data
public class SaveGroupAndMemberResp implements Serializable {
    private int processedMembers;

    public SaveGroupAndMemberResp() {
    }

    public SaveGroupAndMemberResp(int processedMembers) {
        this.processedMembers = processedMembers;
    }
}
