package cn.valuetodays.api2.extra.vo.sonic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-06
 */
@Data
public abstract class SonicBaseResp<T> implements Serializable {
    private int status;
    private String message;
    private T data;

    @JsonIgnore
    public boolean isSuccess() {
        return 200 == status;
    }
}
