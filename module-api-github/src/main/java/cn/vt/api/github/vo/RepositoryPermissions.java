package cn.vt.api.github.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-25
 */
@Data
public class RepositoryPermissions implements Serializable {
    @JsonProperty("admin")
    private Boolean admin;

    @JsonProperty("maintain")
    private Boolean maintain;

    @JsonProperty("pull")
    private Boolean pull;

    @JsonProperty("push")
    private Boolean push;

    @JsonProperty("triage")
    private Boolean triage;
}
