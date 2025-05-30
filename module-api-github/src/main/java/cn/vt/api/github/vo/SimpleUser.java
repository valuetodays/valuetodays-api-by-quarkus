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
public class SimpleUser implements Serializable {
    private String login;
    private Long id;
    private String node_id;
    private String avatar_url;
    private String gravatar_id;
    private String url;
    private String html_url;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url; // "https://api.github.com/users/valuetodays/starred{/owner}{/repo}",
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type; // "User"
    private boolean site_admin; // false
}
