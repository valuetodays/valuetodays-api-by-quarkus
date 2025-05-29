package cn.vt.rest.third.eastmoney.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-12-21
 */
@Data
public abstract class EastmoneyBaseResp implements Serializable {
    @JsonProperty("ErrCode")
    private int errCode;
    @JsonProperty("ErrMsg")
    private String errMsg;
    @JsonProperty("PageIndex")
    private int pageIndex;
    @JsonProperty("PageSize")
    private int pageSize;
    @JsonProperty("TotalCount")
    private int totalCount;
}
