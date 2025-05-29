package cn.vt.rest.third.xueqiu.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-06-16
 */
@Data
public class QueryTokenData implements Serializable {
    private String session_token;
}
