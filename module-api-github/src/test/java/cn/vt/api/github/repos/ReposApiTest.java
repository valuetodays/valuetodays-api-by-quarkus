package cn.vt.api.github.repos;

import cn.vt.api.github.vo.CreateOrUpdateFileContentReq;
import cn.vt.api.github.vo.PublicKeyVo;
import cn.vt.api.github.vo.SecretsInRepo;
import cn.vt.test.TestBase;
import cn.vt.util.EnvironmentPropertyUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ReposApi}.
 *
 * @author lei.liu
 * @since 2024-09-25
 */
@Slf4j
public class ReposApiTest extends TestBase {
    private ReposApi reposApi = new ReposApi();

    @Test
    void createFileOrUpdateFileContent() {
        CreateOrUpdateFileContentReq req = new CreateOrUpdateFileContentReq();
        req.setOwnerName("valuetodays");
        req.setRepoName("static-data-repo-image");
        req.setBranch("main");
        req.setMessage("add " + 1 + " file(s)");
        req.setTargetFilePath("abc/ppp55_" + System.currentTimeMillis() + ".png");
        String fileAsBase64String = getFileAsBase64String("test-image-for-commit-to-github.jpg");
        req.setFileContentBase64String(fileAsBase64String);
        req.setCommitterName(req.getOwnerName());
        req.setCommitterEmail(EnvironmentPropertyUtils.getString("GITHUB_COMMITTER_EMAIL", ""));
        boolean flag = reposApi.createFileOrUpdateFileContent(req);
        MatcherAssert.assertThat(flag, CoreMatchers.equalTo(true));
    }

    @Test
    void getPublicKey() {
        PublicKeyVo pk = reposApi.getPublicKey("valuetodays", "common-spring-web");
        log.info("pk={}", pk);
    }

    @Test
    void createOrUpdateSecret() {
        reposApi.createOrUpdateSecret("valuetodays", "common-spring-web", "TMP_A", "A");
    }

    @Test
    void listSecretsInRepo() {
        SecretsInRepo secretsInRepo = reposApi.listSecrets("valuetodays", "valuetodays-api");
        log.info("secretsInRepo:{}", secretsInRepo);
    }
}
