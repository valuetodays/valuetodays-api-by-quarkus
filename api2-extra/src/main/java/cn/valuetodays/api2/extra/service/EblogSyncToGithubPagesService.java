package cn.valuetodays.api2.extra.service;

import cn.valuetodays.api2.extra.vo.sonic.SonicLoginReq;
import cn.valuetodays.api2.extra.vo.sonic.SonicLoginResp;
import cn.valuetodays.api2.extra.vo.sonic.SonicPagedPostsResp;
import cn.valuetodays.api2.extra.vo.sonic.SonicPostIdResp;
import cn.valuetodays.api2.extra.vo.sonic.SonicPostItem;
import cn.vt.exception.AssertUtils;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.JsonUtils;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

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
        // todo 写入markdown
    }

    public SonicLoginResp login(String baseUrl, String user, String pass) {
//        curl -XPOST 'http://eblog.valuetodays.cn' -H 'Admin-Authorization:adfsdf'
//        -H 'content-type:application/json'
//        -d '{"username": "valuetodays", "password":"xxx"}'
        SonicLoginReq sonicLoginReq = new SonicLoginReq();
        sonicLoginReq.setUsername(user);
        sonicLoginReq.setPassword(pass);
        String responseString = HttpClient4Utils.doPostJson(baseUrl + "/api/admin/login", sonicLoginReq, null);
        log.info("responseString={}", responseString);
        return JsonUtils.fromJson(responseString, SonicLoginResp.class);
    }


}
