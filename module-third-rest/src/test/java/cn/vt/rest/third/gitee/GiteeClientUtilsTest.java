package cn.vt.rest.third.gitee;

import cn.vt.rest.third.gitee.vo.GiteeRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Tests for {@link GiteeClientUtils}.
 *
 * @author lei.liu
 * @since 2024-02-24
 */
@Slf4j
public class GiteeClientUtilsTest {

    // 可去此处获取token: https://gitee.com/api/v5/swagger#/getV5ReposOwnerRepoStargazers?ex=no
    private static final String token = "xx";

    @Test
    public void repos() {
        List<GiteeRepo> repos = GiteeClientUtils.repos(token, null);
//        List<GiteeRepo> repos = giteeClient.repos(token, null);
        log.info("repos: {}", repos);
    }

    @Test
    public void downloadRepo() throws IOException {
        String owner = "valuetodays";
        String repo = "auto-config-when-install-win10";
        String sha = "master";
        File baseDir = new File("c:/tmp/");
        File localDir = GiteeClientUtils.downloadRepo(token, owner, repo, sha, baseDir);
        log.info("file(s) is in {}", localDir);
    }

}
