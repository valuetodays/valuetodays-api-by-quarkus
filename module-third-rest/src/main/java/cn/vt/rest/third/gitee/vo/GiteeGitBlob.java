package cn.vt.rest.third.gitee.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-01-09
 */
@Data
public class GiteeGitBlob implements Serializable {
    private String sha;
    private long size;
    private String url;
    private String encoding; // base64
    private String content;
}
