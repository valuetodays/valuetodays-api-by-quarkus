package cn.vt.rest.third.xueqiu;

import cn.vt.rest.third.xueqiu.vo.CommentArticleData;
import cn.vt.rest.third.xueqiu.vo.QueryTokenData;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests for {@link XueQiuArticleClientUtils}.
 *
 * @author lei.liu
 * @since 2024-10-01
 */
public class XueQiuArticleClientUtilsTest extends BaseXueQiuIntegrationTest {

    @Test
    public void querySessionToken() {
        String referer = "https://xueqiu.com/3014167167/293944930";
        QueryTokenData data = XueQiuArticleClientUtils.querySessionToken(referer, getToken());
        assertThat(data, notNullValue());
        assertThat(data.getSession_token(), notNullValue());
    }

    @Test
    @Disabled("this will post a comment to article")
    public void commentArticleQuick() {
        String referer = "https://xueqiu.com/3014167167/293944930";
        CommentArticleData data = XueQiuArticleClientUtils.commentArticleQuick(referer, getToken());
        assertThat(data, notNullValue());
        assertThat(data.getId(), notNullValue());
        assertThat(data.getTarget(), notNullValue());
    }
}
