package cn.valuetodays.api2.web.common;

import cn.valuetodays.api2.client.component.IWxmpArticleComponent;
import cn.valuetodays.api2.client.req.UploadFileToGithubReq;
import cn.valuetodays.api2.web.common.properties.GithubProperties;
import cn.vt.api.github.repos.ReposApi;
import cn.vt.api.github.vo.CreateOrUpdateFileContentReq;
import cn.vt.util.ClassPathResourceUtils;
import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-07
 */
@ApplicationScoped
@Slf4j
public class GithubComponent {
    @Inject
    GithubProperties githubProperties;
    @Inject
    IWxmpArticleComponent wxmpArticleComponent;

    private static boolean createFileOrUpdateFileContent0(File addedFile,
                                                          CreateOrUpdateFileContentReq req,
                                                          ReposApi reposApi) throws IOException {
        String fileAsBase64String = ClassPathResourceUtils.getFileAsBase64String(addedFile);
        req.setFileContentBase64String(fileAsBase64String);

        return reposApi.createFileOrUpdateFileContent(req);
    }

    public Tuple2<String, List<String>> uploadImageByWxmpUrl(String url) {
        Path tempDirectory;
        try {
            tempDirectory = Files.createTempDirectory("noname");
        } catch (Exception e) {
            log.error("error when createTempDirectory", e);
            return Tuple2.of("", List.of());
        }
        Tuple2<String, List<File>> tuple2 = wxmpArticleComponent.downloadImages(tempDirectory.toFile(), url);
        List<File> files = tuple2.getItem2();
        // 获取文件的父目录
        Function<File, String> fileParentFun = (f) -> {
            String s = StringUtils.substringBeforeLast(f.getAbsolutePath(), "/");
            return StringUtils.substringAfterLast(s, "/");
        };
        List<String> fileUrls = this.uploadFilesToGithub(files, fileParentFun, UploadFileToGithubReq.Type.wp__mmpic, false);
        return Tuple2.of(tuple2.getItem1(), fileUrls);
    }

    /**
     * @return 返回文件的全路径 eg, https://xx.github.io/repo/aaa/bbb/ccc.png
     */
    public List<String> uploadFilesToGithub(List<File> files, Function<File, String> fileParentFun,
                                            UploadFileToGithubReq.Type type,
                                            boolean appendRandomInFilename) {
        String username = githubProperties.getUsername();
        String email = githubProperties.getEmail();
        String gitToken = githubProperties.getGitToken();

        List<String> putFiles = new ArrayList<>();
        ReposApi reposApi = new ReposApi(gitToken);


        CreateOrUpdateFileContentReq req = new CreateOrUpdateFileContentReq();
        req.setOwnerName(username);
        req.setRepoName("statics"); // 固定，不要变
        req.setBranch("main"); // 固定，不要变
        req.setMessage("add 1 file");
        req.setCommitterEmail(email);
        req.setCommitterName(req.getOwnerName());
        for (File addedFile : files) {
            String name = appendRandomInFilename ? changeName(addedFile.getName()) : addedFile.getName();

            String parentDirName = fileParentFun.apply(addedFile);
            req.setTargetFilePath("images/" + type.toDirectoryPath() + "/" + parentDirName + "/" + name);
            try {
                log.info("begin to put file to github, file={}", addedFile.getAbsolutePath());
                boolean flag = createFileOrUpdateFileContent0(addedFile, req, reposApi);
                if (flag) {
                    putFiles.add("https://" + username + ".github.io/" + req.getRepoName() + "/" + req.getTargetFilePath());
                }
            } catch (Exception e) {
                if (StringUtils.contains(e.getMessage(), "properties/sha")) {
                    log.info("failed to put file to github, file is already in server.");
                } else {
                    log.error("error when createFileOrUpdateFileContent()", e);
                }
            }
        }

        return putFiles;
    }

    private String changeName(String nameWithExt) {
        String nameWithoutExt = StringUtils.substringBeforeLast(nameWithExt, ".");
        String ext = nameWithExt.substring(nameWithoutExt.length());
        return nameWithoutExt + "_" + LocalTime.now().toSecondOfDay() + ext;
    }
}
