package cn.vt.api.github.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-03-30
 */
@Data
public class SecretsInRepo implements Serializable {
    @JsonProperty("total_count")
    private int totalCount;
    private List<SecretVo> secrets;

    @Data
    public static class SecretVo implements Serializable {
        private String name;
        private String created_at;
        private String updated_at;
    }
}
