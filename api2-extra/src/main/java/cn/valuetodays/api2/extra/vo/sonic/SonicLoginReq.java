package cn.valuetodays.api2.extra.vo.sonic;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-06
 */
@Data
public class SonicLoginReq implements Serializable {
    private String username;
    private String password;

}
