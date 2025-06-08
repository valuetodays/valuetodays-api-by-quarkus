package cn.valuetodays.api.account.reqresp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginByIdBO {
    private Long id;
    private String userAgent;
}
