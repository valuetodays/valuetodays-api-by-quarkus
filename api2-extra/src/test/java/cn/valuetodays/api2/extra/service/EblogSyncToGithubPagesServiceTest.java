package cn.valuetodays.api2.extra.service;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EblogSyncToGithubPagesService}.
 *
 * @author lei.liu
 * @since 2025-06-06
 */
public class EblogSyncToGithubPagesServiceTest {

    @Test
    public void syncToGithubPages() {
        String baseUrl = "http://eblog.valuetodays.cn";
        String username = "valuetodays";
        String password = "x";
        EblogSyncToGithubPagesService s = new EblogSyncToGithubPagesService();
        s.syncToGithubPages(baseUrl, username, password);


    }
}
