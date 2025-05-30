package cn.vt.api.github.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserRepoVo implements Serializable {
    @JsonProperty("archive_url")
    private String archiveUrl;

    @JsonProperty("archived")
    private Boolean archived;

    @JsonProperty("assignees_url")
    private String assigneesUrl;

    @JsonProperty("blobs_url")
    private String blobsUrl;

    @JsonProperty("branches_url")
    private String branchesUrl;

    @JsonProperty("clone_url")
    private String cloneUrl;

    @JsonProperty("collaborators_url")
    private String collaboratorsUrl;

    @JsonProperty("comments_url")
    private String commentsUrl;

    @JsonProperty("commits_url")
    private String commitsUrl;

    @JsonProperty("compare_url")
    private String compareUrl;

    @JsonProperty("contents_url")
    private String contentsUrl;

    @JsonProperty("contributors_url")
    private String contributorsUrl;

    @JsonProperty("created_at")
    private String createdAt; // 2024-03-01T13:53:03Z

    @JsonProperty("default_branch")
    private String defaultBranch;

    @JsonProperty("delete_branch_on_merge")
    private Boolean deleteBranchOnMerge;

    @JsonProperty("deployments_url")
    private String deploymentsUrl;

    @JsonProperty("description")
    private String description;

    @JsonProperty("disabled")
    private Boolean disabled;

    @JsonProperty("downloads_url")
    private String downloadsUrl;

    @JsonProperty("events_url")
    private String eventsUrl;

    @JsonProperty("fork")
    private Boolean fork;

    @JsonProperty("forks")
    private Integer forks;

    @JsonProperty("forks_count")
    private Integer forksCount;

    @JsonProperty("forks_url")
    private String forksUrl;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("git_commits_url")
    private String gitCommitsUrl;

    @JsonProperty("git_refs_url")
    private String gitRefsUrl;

    @JsonProperty("git_tags_url")
    private String gitTagsUrl;

    @JsonProperty("git_url")
    private String gitUrl;

    @JsonProperty("has_downloads")
    private Boolean hasDownloads;

    @JsonProperty("has_issues")
    private Boolean hasIssues;

    @JsonProperty("has_pages")
    private Boolean hasPages;

    @JsonProperty("has_projects")
    private Boolean hasProjects;

    @JsonProperty("has_wiki")
    private Boolean hasWiki;

    @JsonProperty("homepage")
    private String homepage;

    @JsonProperty("hooks_url")
    private String hooksUrl;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("is_template")
    private Boolean isTemplate;

    @JsonProperty("issue_comment_url")
    private String issueCommentUrl;

    @JsonProperty("issue_events_url")
    private String issueEventsUrl;

    @JsonProperty("issues_url")
    private String issuesUrl;

    @JsonProperty("keys_url")
    private String keysUrl;

    @JsonProperty("labels_url")
    private String labelsUrl;

    @JsonProperty("language")
    private String language; // shell / Java / HTML / ...

    @JsonProperty("languages_url")
    private String languagesUrl;

    @JsonProperty("license")
    private LicenseSimple license;

    @JsonProperty("merges_url")
    private String mergesUrl;

    @JsonProperty("milestones_url")
    private String milestonesUrl;

    @JsonProperty("mirror_url")
    private String mirrorUrl;

    @JsonProperty("name")
    private String name;

    @JsonProperty("network_count")
    private Integer networkCount;

    @JsonProperty("node_id")
    private String nodeId;

    @JsonProperty("notifications_url")
    private String notificationsUrl;

    @JsonProperty("open_issues")
    private Integer openIssues;

    @JsonProperty("open_issues_count")
    private Integer openIssuesCount;

    @JsonProperty("owner")
    private SimpleUser owner;

    @JsonProperty("permissions")
    private RepositoryPermissions permissions;

    @JsonProperty("private")
    private Boolean _private;

    @JsonProperty("pulls_url")
    private String pullsUrl;

    @JsonProperty("pushed_at")
    private String pushedAt;

    @JsonProperty("releases_url")
    private String releasesUrl;

    @JsonProperty("size")
    private Integer size;

    @JsonProperty("ssh_url")
    private String sshUrl;

    @JsonProperty("stargazers_count")
    private Integer stargazersCount;

    @JsonProperty("stargazers_url")
    private String stargazersUrl;

    @JsonProperty("statuses_url")
    private String statusesUrl;

    @JsonProperty("subscribers_count")
    private Integer subscribersCount;

    @JsonProperty("subscribers_url")
    private String subscribersUrl;

    @JsonProperty("subscription_url")
    private String subscriptionUrl;

    @JsonProperty("svn_url")
    private String svnUrl;

    @JsonProperty("tags_url")
    private String tagsUrl;

    @JsonProperty("teams_url")
    private String teamsUrl;

    @JsonProperty("temp_clone_token")
    private String tempCloneToken;

    @JsonProperty("template_repository")
    private Object templateRepository = null;

    @JsonProperty("topics")
    private List<String> topics = null;

    @JsonProperty("trees_url")
    private String treesUrl;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("url")
    private String url;

    @JsonProperty("visibility")
    private String visibility;

    @JsonProperty("watchers")
    private Integer watchers;

    @JsonProperty("watchers_count")
    private Integer watchersCount;

}

