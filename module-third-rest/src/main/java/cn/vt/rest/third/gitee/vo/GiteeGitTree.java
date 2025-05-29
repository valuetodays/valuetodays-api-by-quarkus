package cn.vt.rest.third.gitee.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-01-09
 */
@Data
public class GiteeGitTree implements Serializable {
    private String sha;
    private String url;
    private List<TreeData> tree;

    @Data
    public static class TreeData implements Serializable {
        private String path;
        private String mode;
        private String type; // blob tree
        private String sha;
        private long size;
        private String url;
    }
}
