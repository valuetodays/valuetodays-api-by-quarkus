package cn.valuetodays.module.spider.service.impl.wxmp;

import cn.valuetodays.module.spider.client.persist.WxmpArticlePO;
import cn.valuetodays.module.spider.dao.WxmpArticleDAO;
import cn.valuetodays.module.spider.service.impl.wxmp.pojo.Article;
import cn.valuetodays.module.spider.service.impl.wxmp.pojo.BaseResp;
import cn.valuetodays.module.spider.service.impl.wxmp.pojo.WxmpGetArticlesResp;
import cn.vt.util.DateUtils;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.JsonUtils;
import cn.vt.util.SleepUtils;
import io.quarkus.redis.datasource.RedisDataSource;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-08-03
 */
@Component
@Slf4j
public class WxmpArticleSpider {

    private static final String LAST_ARTICLE_ID_PREFIX = "wxmp.last_article_id:";

    @Inject
    WxmpArticleDAO wxmpArticleDAO;
    @Inject
    RedisDataSource stringRedisTemplate;

    public void gather(String wxAuthorId,
                       Long authorId, String fullUrlPrefix,
                       String lastArticleIdInParam,
                       boolean useLastArticleIdInRedis) {
        String lastArticleId = lastArticleIdInParam;
        if (useLastArticleIdInRedis) {
            lastArticleId = stringRedisTemplate.value(String.class).get(LAST_ARTICLE_ID_PREFIX + wxAuthorId);
        } else {
            stringRedisTemplate.key(String.class).del(LAST_ARTICLE_ID_PREFIX + wxAuthorId);
        }


        while (true) {
            Pair<WxmpGetArticlesResp, String> pair = doPageQueryAsResp(fullUrlPrefix, lastArticleId);
            if (Objects.isNull(pair)) {
                think(200);
                continue;
            }
            WxmpGetArticlesResp resp = pair.getLeft();
            BaseResp baseResp = resp.getBase_resp();
            int ret = baseResp.getRet();
            if (ret == 0) {
                String maxArticleId = resp.getMax_article_id();
                if ("0".equals(maxArticleId)) {
                    log.info("end of {}", wxAuthorId);
                    break;
                }
                List<Article> articles = resp.getArticles();
                for (Article article : articles) {
                    WxmpArticlePO old = wxmpArticleDAO.findByMid(article.getMid());
                    if (Objects.nonNull(old)) {
                        continue;
                    }
                    WxmpArticlePO wxmpArticlePO = new WxmpArticlePO();
                    wxmpArticlePO.setBiz(article.get__biz());
                    wxmpArticlePO.setAuthorId(authorId);
                    wxmpArticlePO.setPaid(article.getIs_paid());
                    wxmpArticlePO.setMid(article.getMid());
                    wxmpArticlePO.setPublishTime(DateUtils.getToday(article.getPublish_time() * 1000));
                    wxmpArticlePO.setUrl(article.getUrl());
                    wxmpArticlePO.setTitle(article.getTitle());
                    String pageSource = HttpClient4Utils.doGet(article.getUrl());
                    Document doc = Jsoup.parse(pageSource);
                    Element jsContentElement = doc.getElementById("js_content");
                    if (Objects.isNull(jsContentElement)) {
                        wxmpArticlePO.setValidHtml(0);
                        wxmpArticlePO.setHtmlContent(pageSource);
                    } else {
                        wxmpArticlePO.setValidHtml(1);
                        wxmpArticlePO.setHtmlContent(jsContentElement.html());
                    }
                    think();
                    think(3);
                    wxmpArticleDAO.persist(wxmpArticlePO);
                }
                lastArticleId = resp.getMax_article_id();
                stringRedisTemplate.value(String.class).set(LAST_ARTICLE_ID_PREFIX + wxAuthorId, lastArticleId);
            } else {
                log.info("end or error");
                break;
            }

            think();
            think(10);
        }
    }

    private void think() {
        think(50);
    }

    private void think(int ms) {
        SleepUtils.sleep(ms);
    }

    private Pair<WxmpGetArticlesResp, String> doPageQueryAsResp(String urlPrefix, String lastArticleId) {
        String jsonString = doPageQuery(urlPrefix, lastArticleId);
        try {
            WxmpGetArticlesResp wxmpGetArticlesResp = JsonUtils.fromJson(jsonString, WxmpGetArticlesResp.class);
            return Pair.of(wxmpGetArticlesResp, jsonString);
        } catch (Exception e) {
            log.error("error when get article detail", e);
            return null;
        }
    }

    private String doPageQuery(String urlPrefix, String lastArticleId) {
        String jsonString = HttpClient4Utils.doGet(urlPrefix + StringUtils.trimToEmpty(lastArticleId));
        log.info("jsonString: {}", jsonString);
        return jsonString;
    }
}
