package cn.vt.rest.third.xueqiu.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-05-23 09:51
 */
@Data
public class XueQiuKlineData implements Serializable {
    private String symbol;
    private String[] column;
    private String[][] item;
}
