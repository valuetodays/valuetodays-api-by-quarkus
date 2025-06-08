package cn.valuetodays.api.account.reqresp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lei.liu
 * @since 2019-10-22 10:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginBO {
    private String mobile;
    private String password;


    private String userAgent;
}
