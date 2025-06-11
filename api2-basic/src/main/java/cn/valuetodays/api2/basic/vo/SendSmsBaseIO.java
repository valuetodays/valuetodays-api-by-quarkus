package cn.valuetodays.api2.basic.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-04-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class SendSmsBaseIO extends PushBaseReq {

    @NotNull
    private String mobile;

    @NotNull
    private String imgToken;

    @JsonIgnore
    public boolean shouldIgnore() {
        return "-1".equals(imgToken);
    }

    @JsonIgnore
    public void makeIgnore() {
        imgToken = "-1";
    }
}
