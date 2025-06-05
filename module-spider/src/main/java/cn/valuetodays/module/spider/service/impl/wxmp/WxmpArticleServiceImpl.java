package cn.valuetodays.module.spider.service.impl.wxmp;

import cn.valuetodays.module.spider.client.persist.WxmpArticlePO;
import cn.valuetodays.module.spider.client.persist.WxmpAuthorPO;
import cn.valuetodays.module.spider.client.reqresp.WxmpArticleGatherReq;
import cn.valuetodays.module.spider.client.reqresp.WxmpArticleGatherResp;
import cn.valuetodays.module.spider.dao.WxmpArticleDAO;
import cn.valuetodays.module.spider.dao.WxmpAuthorDAO;
import cn.valuetodays.quarkus.commons.base.BaseService;
import cn.vt.exception.AssertUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-08-03
 */
@ApplicationScoped
public class WxmpArticleServiceImpl
    extends BaseService<Long, WxmpArticlePO, WxmpArticleDAO> {

    @Inject
    WxmpArticleSpider wxmpArticleSpider;
    @Inject
    WxmpAuthorDAO wxmpAuthorDAO;

    public WxmpArticleGatherResp gather(WxmpArticleGatherReq req) {
        String capturedUrl = req.getUrl();
        String lastArticleIdInReq = StringUtils.trimToEmpty(req.getLastArticleId());

        URI uri = URI.create(capturedUrl);
        String rawQuery = uri.getRawQuery();

        List<NameValuePair> qsList = URLEncodedUtils.parse(rawQuery, StandardCharsets.UTF_8);
        NameValuePair authorIdPair = qsList.stream()
            .filter(e -> e.getName().equals("author_id"))
            .findFirst().orElse(null);
        AssertUtils.assertNotNull(authorIdPair);
        assert authorIdPair != null;
        String wxAuthorId = authorIdPair.getValue();
        WxmpAuthorPO authorPO = wxmpAuthorDAO.findByWxAuthorId(wxAuthorId);
        AssertUtils.assertNotNull(authorPO, "作者不存在于spider_wxmp_author表中，" + wxAuthorId);

        final String qsAsStringWithoutFromArticleId = qsList.stream()
            .filter(e -> !"from_article_id".equals(e.getName()))
            .map(e -> e.getName() + "=" + e.getValue())
            .collect(Collectors.joining("&"));
        final String fullUrlPrefix = uri.getScheme() + "://" + uri.getHost() + uri.getPath()
            + "?" + qsAsStringWithoutFromArticleId + "&" + "from_article_id=";

        wxmpArticleSpider.gather(wxAuthorId,
            authorPO.getId(), fullUrlPrefix,
            lastArticleIdInReq, req.isUseLastArticleIdInRedis());
        return new WxmpArticleGatherResp();
    }
}
