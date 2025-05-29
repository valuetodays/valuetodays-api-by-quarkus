package cn.vt.rest.third.sse.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-04
 */
@Data
public abstract class BaseResp<T extends Serializable> implements Serializable {
    // result和pageHelp里的data一样
    private PageHelp<T> pageHelp;
    private List<T> result;
}
