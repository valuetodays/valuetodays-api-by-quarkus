package cn.vt.rest.third.eastmoney.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-12-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IopvResp extends EastmoneyBaseResp {
    @JsonProperty("Data")
    private IopvData data;
}
