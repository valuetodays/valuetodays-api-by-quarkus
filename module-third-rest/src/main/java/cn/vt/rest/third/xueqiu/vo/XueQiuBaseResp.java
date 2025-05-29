package cn.vt.rest.third.xueqiu.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-05-23 09:52
 */
@Data
public abstract class XueQiuBaseResp<D> implements Serializable {
    private int error_code;
    private String error_description;
    private D data;
}
