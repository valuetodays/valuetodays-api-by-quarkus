package cn.vt.rest.third.gitee.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 获取http响应的数据，包括header等.
 *
 * @author lei.liu
 * @since 2024-02-24
 */
@Data
public class GiteeResponseRef implements Serializable {
    private Map<String, String> responseHeaders;
}
