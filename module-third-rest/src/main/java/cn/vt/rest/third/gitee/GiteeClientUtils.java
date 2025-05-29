package cn.vt.rest.third.gitee;

import cn.vt.exception.CommonException;
import cn.vt.rest.third.gitee.vo.GiteeGitBlob;
import cn.vt.rest.third.gitee.vo.GiteeGitTree;
import cn.vt.rest.third.gitee.vo.GiteeRepo;
import cn.vt.rest.third.gitee.vo.GiteeResponseRef;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-02-24
 */
public class GiteeClientUtils {
    /**
     * 获取用户授权的仓库列表.
     * 分页数据在response的header中，有"total_count"和"total_page"
     *
     * @param token token
     */
    public static List<GiteeRepo> repos(String token, GiteeResponseRef giteeResponseRef) {
        String url = "https://gitee.com/api/v5/user/repos?"
            + "access_token={token}"
            + "&type=all"
            + "&sort=full_name"
            + "&page=1"
            + "&per_page=100";
        // 需要将responseheaders放到GiteeResponseRef中
        Map<String, String> headers = Map.of("Content-Type", "application/json;charset=utf-8");
        String s = HttpClient4Utils.doGet(url, null, headers, StandardCharsets.UTF_8.name());
        return JsonUtils.fromJson(s, new TypeReference<>() {
        });
    }

    /**
     * 获取文件列表
     *
     * @param token token
     * @param owner owner 用户名称
     * @param repo  repo 仓库名称
     * @param sha   sha 分支名称 或 commit-id
     */
    public static GiteeGitTree gitTree(String token, String owner, String repo, String sha) {
        String url = "https://gitee.com/api/v5/repos/{owner}/{repo}/git/trees/{sha}?"
            + "access_token={token}" + "&recursive=1";
        String replacedUrl = StringUtils.replace(url, "{owner}", owner);
        replacedUrl = StringUtils.replace(replacedUrl, "{repo}", repo);
        replacedUrl = StringUtils.replace(replacedUrl, "{sha}", sha);
        replacedUrl = StringUtils.replace(replacedUrl, "{token}", token);

        Map<String, String> headers = Map.of("Content-Type", "application/json;charset=utf-8");
        String s = HttpClient4Utils.doGet(replacedUrl, null, headers, StandardCharsets.UTF_8.name());
        return JsonUtils.toObject(s, GiteeGitTree.class);
    }

    public static GiteeGitBlob gitBlob(String token, String owner, String repo, String sha) {
        String url = "https://gitee.com/api/v5/repos/{owner}/{repo}/git/blobs/{sha}?access_token={token}";
        String replacedUrl = StringUtils.replace(url, "{owner}", owner);
        replacedUrl = StringUtils.replace(replacedUrl, "{repo}", repo);
        replacedUrl = StringUtils.replace(replacedUrl, "{sha}", sha);
        replacedUrl = StringUtils.replace(replacedUrl, "{token}", token);

        Map<String, String> headers = Map.of("Content-Type", "application/json;charset=utf-8");
        String s = HttpClient4Utils.doGet(replacedUrl, null, headers, StandardCharsets.UTF_8.name());
        return JsonUtils.toObject(s, GiteeGitBlob.class);
    }


    public static File downloadRepo(String token, String owner, String repo, String sha, File targetDir) throws IOException {
        if (!targetDir.exists()) {
            boolean mkdirs = targetDir.mkdirs();
            if (!mkdirs) {
                throw new IOException("error when create directory: " + targetDir);
            }
        }
        if (!targetDir.isDirectory()) {
            throw new IllegalArgumentException("targetDir should be a directory");
        }
        GiteeGitTree giteeGitTree = gitTree(token, owner, repo, sha);
        File basePath = new File(targetDir, repo + "-" + sha + "-" + System.currentTimeMillis());
        boolean flag = basePath.mkdirs();
        if (!flag) {
            throw new IOException("error when create directory: " + basePath);
        }
        downloadBlobs(token, giteeGitTree, basePath, owner, repo);
        return basePath;
    }

    public static void downloadBlobs(String token, GiteeGitTree giteeGitTree,
                                     File basePath, String owner, String repo) {
        List<GiteeGitTree.TreeData> trees = giteeGitTree.getTree();
        for (GiteeGitTree.TreeData tree : trees) {
            try {
                downloadBlob(token, basePath, tree, owner, repo);
            } catch (Exception ignored) {

            }
        }
    }

    private static void downloadBlob(String token, File basePath,
                                     GiteeGitTree.TreeData tree, String owner, String repo) throws IOException {
        GiteeGitBlob giteeGitBlob = gitBlob(token, owner, repo, tree.getSha());
        String content = giteeGitBlob.getContent();
        String encoding = giteeGitBlob.getEncoding();
        if ("base64".equalsIgnoreCase(encoding)) {
            byte[] decodedBytes = Base64.getDecoder().decode(content);
            FileUtils.writeByteArrayToFile(new File(basePath, tree.getPath()), decodedBytes);
        } else {
            throw new CommonException("not supported");
        }
    }

}
