package cn.valuetodays.module.spider.service.impl.wxmp;

import cn.valuetodays.module.spider.client.persist.WxmpAuthorPO;
import cn.valuetodays.module.spider.dao.WxmpAuthorDAO;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-08-03
 */
@ApplicationScoped
public class WxmpAuthorServiceImpl
    extends BaseCrudService<Long, WxmpAuthorPO, WxmpAuthorDAO> {
}
