package cn.vt.api.github.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-03-30
 */
@Data
public class PublicKeyVo implements Serializable {
    private String key_id;
    private String key;
}
