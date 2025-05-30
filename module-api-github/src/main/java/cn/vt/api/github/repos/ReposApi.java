package cn.vt.api.github.repos;

import cn.vt.api.github.BaseApiGithub;
import cn.vt.api.github.vo.CreateOrUpdateFileContentReq;
import cn.vt.api.github.vo.PublicKeyVo;
import cn.vt.api.github.vo.SecretsInRepo;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-25
 */
public class ReposApi extends BaseApiGithub {

    public ReposApi() {
    }

    public ReposApi(String apiKey) {
        super(apiKey);
    }

    public PublicKeyVo getPublicKey(String owner, String repo) {
//        """
//            curl -L \\
//              -H "Accept: application/vnd.github+json" \\
//              -H "Authorization: Bearer <YOUR-TOKEN>" \\
//              -H "X-GitHub-Api-Version: 2022-11-28" \\
//              https://api.github.com/repos/OWNER/REPO/actions/secrets/public-key
//            """
        Object[] vars = new Object[]{owner, repo};
        return super.get("/repos/{username}/{reponame}/actions/secrets/public-key", PublicKeyVo.class, vars);
    }

    public SecretsInRepo listSecrets(String owner, String repo) {
//        """
//            curl -L \\
//              -H "Accept: application/vnd.github+json" \\
//              -H "Authorization: Bearer <YOUR-TOKEN>" \\
//              -H "X-GitHub-Api-Version: 2022-11-28" \\
//              https://api.github.com/repos/OWNER/REPO/actions/secrets
//            """
        Object[] vars = new Object[]{owner, repo};
        SecretsInRepo secretsInRepo = super.get("/repos/{username}/{reponame}/actions/secrets", SecretsInRepo.class, vars);

        return secretsInRepo;
    }

    public Object createOrUpdateSecret(String owner, String repo, String secretName, String secretValue) {
//        curl -L \
//        -X PUT \
//        -H "Accept: application/vnd.github+json" \
//        -H "Authorization: Bearer <YOUR-TOKEN>" \
//        -H "X-GitHub-Api-Version: 2022-11-28" \
//        https://api.github.com/repos/OWNER/REPO/actions/secrets/SECRET_NAME \
//        -d '{"encrypted_value":"c2VjcmV0","key_id":"012345678912345678"}'
        final PublicKeyVo publicKey = getPublicKey(owner, repo);
        Object[] vars = new Object[]{owner, repo, secretName};
        String key = publicKey.getKey();
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("encrypted_value", "");// encryptSecret(key, secretValue));
        requestBody.put("key_id", publicKey.getKey_id());
        ResponseEntity<String> put = put("/repos/{owner}/{repo}/actions/secrets/{secretName}", requestBody, String.class, vars);
        System.out.println(put);
        return null;
    }


    public boolean createFileOrUpdateFileContent(CreateOrUpdateFileContentReq req) {
        String ownerName = req.getOwnerName();
        String repoName = req.getRepoName();
        String filePath = req.getTargetFilePath();

        Committer committer = new Committer();
        committer.setName(req.getCommitterName());
        committer.setEmail(req.getCommitterEmail());
        Object[] vars = new Object[]{ownerName, repoName, filePath};
        CommitRequestBody requestBody = new CommitRequestBody();
        requestBody.setMessage(req.getMessage());
        requestBody.setCommitter(committer);
        requestBody.setContent(req.getFileContentBase64String());
        // https://docs.github.com/en/rest/repos/contents?apiVersion=2022-11-28#create-or-update-file-contents
        // path不以/开头
        ResponseEntity<String> put = put("/repos/{username}/{reponame}/contents/{path}", requestBody, String.class, vars);
        int value = put.getStatusCode().value();
        return value >= 200 && value < 300;
    }

    @Data
    private static class CommitRequestBody implements Serializable {
        private String message;
        private Committer committer;
        private String content;
        private String sha; // 当update文件时传此值
    }

    @Data
    private static class Committer implements Serializable {
        private String name;
        private String email;
    }

}
