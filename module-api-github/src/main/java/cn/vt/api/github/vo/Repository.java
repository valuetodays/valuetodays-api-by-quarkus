package cn.vt.api.github.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Repository，A git repository
 */
@Data
public class Repository implements Serializable {
    @JsonProperty("allow_merge_commit")
    private Boolean allowMergeCommit = true;

    @JsonProperty("allow_rebase_merge")
    private Boolean allowRebaseMerge = true;

    @JsonProperty("allow_squash_merge")
    private Boolean allowSquashMerge = true;

    @JsonProperty("archive_url")
    private String archiveUrl;

    @JsonProperty("archived")
    private Boolean archived = false;

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
    private String createdAt;

    @JsonProperty("default_branch")
    private String defaultBranch;

    @JsonProperty("delete_branch_on_merge")
    private Boolean deleteBranchOnMerge = false;

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
    private Boolean hasDownloads = true;

    @JsonProperty("has_issues")
    private Boolean hasIssues = true;

    @JsonProperty("has_pages")
    private Boolean hasPages;

    @JsonProperty("has_projects")
    private Boolean hasProjects = true;

    @JsonProperty("has_wiki")
    private Boolean hasWiki = true;

    @JsonProperty("homepage")
    private String homepage;

    @JsonProperty("hooks_url")
    private String hooksUrl;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("is_template")
    private Boolean isTemplate = false;

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
    private String language;

    @JsonProperty("languages_url")
    private String languagesUrl;

    @JsonProperty("license")
    private LicenseSimple license = null;

    @JsonProperty("master_branch")
    private String masterBranch;

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

    @JsonProperty("organization")
    private SimpleUser organization = null;

    @JsonProperty("owner")
    private SimpleUser owner = null;

    @JsonProperty("permissions")
    private RepositoryPermissions permissions;

    @JsonProperty("private")
    private Boolean _private = false;

    @JsonProperty("pulls_url")
    private String pullsUrl;

    @JsonProperty("pushed_at")
//    @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
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

    @JsonProperty("starred_at")
    private String starredAt;

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
    private String templateRepository;

    @JsonProperty("topics")
    private List<String> topics = null;

    @JsonProperty("trees_url")
    private String treesUrl;

    @JsonProperty("updated_at")
//    @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
    private String updatedAt;

    @JsonProperty("url")
    private String url;

    @JsonProperty("visibility")
    private String visibility = "public";

    @JsonProperty("watchers")
    private Integer watchers;

    @JsonProperty("watchers_count")
    private Integer watchersCount;
}

