package cn.valuetodays.api2.extra.service;

import cn.valuetodays.api2.extra.vo.sonic.SonicLoginReq;
import cn.valuetodays.api2.extra.vo.sonic.SonicLoginResp;
import cn.valuetodays.api2.extra.vo.sonic.SonicPagedPostsResp;
import cn.valuetodays.api2.extra.vo.sonic.SonicPostIdResp;
import cn.valuetodays.api2.extra.vo.sonic.SonicPostItem;
import cn.vt.exception.AssertUtils;
import cn.vt.exception.CommonException;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.JsonUtils;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-06
 */
@ApplicationScoped
@Slf4j
public class EblogSyncToGithubPagesService {

    public void syncToGithubPages(String baseUrl, String user, String pass) {
        SonicLoginResp loginResp = login(baseUrl, user, pass);
        log.info("loginResp={}", loginResp);
        AssertUtils.assertTrue(loginResp.isSuccess(), "");
        SonicLoginResp.LoginRespData data = loginResp.getData();
        String accessToken = data.getAccessToken();
        getPosts(baseUrl, accessToken);
    }

    private void getPosts(String baseUrl, String accessToken) {
        int page = 0;
        String url = baseUrl + "/api/admin/posts?page=" + page + "&size=10";
        Map<String, String> headers = Map.of("Admin-Authorization", accessToken);
        String respString = HttpClient4Utils.doGet(url, null, headers, null);
        log.info("respString={}", respString);
        SonicPagedPostsResp respObj = JsonUtils.fromJson(respString, SonicPagedPostsResp.class);
        if (!respObj.isSuccess()) {
            return;
        }
        SonicPagedPostsResp.PagedPostsRespData data = respObj.getData();
        List<SonicPostItem> content = data.getContent();
        if (CollectionUtils.isEmpty(content)) {
            return;
        }
        for (SonicPostItem sonicPostItem : content) {
            saveAsLocalMarkdownFile(baseUrl, sonicPostItem, headers);
        }
    }

    private void saveAsLocalMarkdownFile(String baseUrl, SonicPostItem sonicPostItem, Map<String, String> headers) {
        String respString = HttpClient4Utils.doGet(baseUrl + "/api/admin/posts/" + sonicPostItem.getId(), null, headers, null);
        log.info("respString={}", respString);
        SonicPostIdResp postDetailObj = JsonUtils.fromJson(respString, SonicPostIdResp.class);
        if (!postDetailObj.isSuccess()) {
            return;
        }
        SonicPostItem data = postDetailObj.getData();
        String originalContent = data.getOriginalContent();
        File file = new File("x:/temp", data.getSlug() + ".md");
        try {
            FileUtils.writeStringToFile(file, originalContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new CommonException(e);
        }
    }

    public SonicLoginResp login(String baseUrl, String user, String pass) {
        SonicLoginReq sonicLoginReq = new SonicLoginReq();
        sonicLoginReq.setUsername(user);
        sonicLoginReq.setPassword(pass);
        String responseString = HttpClient4Utils.doPostJson(baseUrl + "/api/admin/login", sonicLoginReq, null);
        log.info("responseString={}", responseString);
        return JsonUtils.fromJson(responseString, SonicLoginResp.class);
    }


}
