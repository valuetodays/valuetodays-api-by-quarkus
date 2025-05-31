package cn.valuetodays.api2.basic.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-06-10
 */
@Data
public class PushBaseReq implements Serializable {
    private Integer toGroupId = 1;
    private Integer toUserId = null;

    public void useToGroupId(Integer toGroupId) {
        this.toGroupId = toGroupId;
        this.toUserId = null;
    }

    public void useToUserId(Integer toUserId) {
        this.toUserId = toUserId;
        this.toGroupId = null;
    }

    public enum ContentType {
        PLAIN_TEXT,
        MARKDOWN_TEXT,
        FILE
    }
}
