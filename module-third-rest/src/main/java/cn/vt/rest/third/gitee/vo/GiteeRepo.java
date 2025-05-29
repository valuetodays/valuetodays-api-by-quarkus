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
public class GiteeRepo implements Serializable {
    private long id;
    private String url;
    private String ssh_url;
}
