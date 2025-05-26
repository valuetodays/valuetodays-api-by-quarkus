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
public class WeworkGroupAndMemberSaveReq implements Serializable {
    private String group;
    private String datetimeStr;
    private String names;
    private String emails;
    private String missedEmailsOfNames;
}

