package cn.vt.rest.third.danjuan.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-05-04 18:43
 */
@Data
public abstract class DjBaseResp<D extends DjBaseData> implements Serializable {
    private D data;
    @JsonProperty("result_code")
    private int resultCode;
}
