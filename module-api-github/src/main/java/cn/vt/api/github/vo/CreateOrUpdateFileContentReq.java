package cn.vt.api.github.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-25
 */
@Data
public class CreateOrUpdateFileContentReq implements Serializable {
    private String ownerName;
    private String repoName;
    private String targetFilePath;
    private String branch = "master";
    private String message;
    private String committerName;
    private String committerEmail;
    private String fileContentBase64String;
}
