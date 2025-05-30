//package cn.valuetodays.api2.web.task;
//
//import cn.valuetodays.api2.web.properties.GithubProperties;
//import cn.valuetodays.common.core.web.aop.TimeConstants;
//import cn.valuetodays.common.core.web.aop.annotation.DistributeLock;
//import cn.valuetodays.module.extra.client.properties.RepoForSubmitProperties;
//import cn.vt.helper.GitHelper;
//import cn.vt.util.DateUtils;
//import cn.vt.util.MyUUIDGenerator;
//import jakarta.inject.Inject;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//import org.eclipse.jgit.api.errors.GitAPIException;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
///**
// * 定时提交文件到git，可以变相提高代码提交次数.
// *
// * @author lei.liu
// * @since 2024-02-25
// */
////@Component
//@Slf4j
//public class CommitToRepoTask {
//    @Inject
//    private GithubProperties githubProperties;
//    @Inject
//    private RepoForSubmitProperties repoForSubmitProperties;
//
////    @Scheduled(cron = "0 0 0/6 * * ?") // 每6小时提交一次
//    @DistributeLock(id="scheduleCommit", milliSeconds = TimeConstants.T55s)
//    public void scheduleCommit() {
//        commitContentToGitRepo();
//    }
//
//    private void commitContentToGitRepo() {
//        String gitUrl = repoForSubmitProperties.getGitUrl();
//        String localPath = repoForSubmitProperties.getLocalPath();
//        String username = githubProperties.getUsername();
//        String gitToken = githubProperties.getGitToken();
//
//        try (GitHelper gitHelper = new GitHelper(gitUrl, localPath, username, gitToken)) {
//            gitHelper.pull();
//            // write to file 【开始】
//            String fileName = "a_very_long.properties";
//            File file = new File(localPath, "/" + fileName);
//            String line = "\r\n# " + DateUtils.formatDatetimeToday() + "\r\n"
//                + "key_" + MyUUIDGenerator.uuidTo16() + "=" + MyUUIDGenerator.nativeUuid();
//            FileUtils.write(file, line, StandardCharsets.UTF_8, true);
//            // write to file 【结束】
//            gitHelper.addAll();
//            gitHelper.commit("update file " + fileName,
//                githubProperties.getUsername(), githubProperties.getEmail()
//            );
//            gitHelper.push();
//        } catch (IOException | GitAPIException e) {
//            log.error("error when new GitHelper()", e);
//        }
//    }
//
//}
