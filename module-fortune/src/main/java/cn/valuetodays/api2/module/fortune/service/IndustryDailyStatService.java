package cn.valuetodays.api2.module.fortune.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import cn.valuetodays.api2.module.fortune.dao.IndustryDailyStatRepository;
import cn.valuetodays.api2.module.fortune.persist.IndustryDailyStatPersist;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import cn.vt.rest.third.eastmoney.EastMoneyIndustryUtils;
import cn.vt.rest.third.eastmoney.vo.EastMoneyIndustryInfoData;
import cn.vt.rest.third.utils.NumberUtils;
import cn.vt.util.DateUtils;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.exception.ConstraintViolationException;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-29
 */
@ApplicationScoped
@Slf4j
public class IndustryDailyStatService extends BaseCrudService<Long, IndustryDailyStatPersist, IndustryDailyStatRepository> {

    public void refresh() {
        List<EastMoneyIndustryInfoData.IndustryInfoItemTyped> list = EastMoneyIndustryUtils.getIndustryDailyInfo();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (EastMoneyIndustryInfoData.IndustryInfoItemTyped item : list) {
            IndustryDailyStatPersist p = new IndustryDailyStatPersist();
            p.setStatDate(DateUtils.formatAsYyyyMMdd(LocalDate.now()));
            p.setCode(item.getCode());
            p.setTitle(item.getTitle());
            p.setClosePx(item.getPrice());
            p.setChgPtg(item.getChgPtg());
            p.setChgValue(item.getChgValue());
            p.setHuanShouLvPtg(item.getHuanShouLv());
            p.setTotalCapYi(NumberUtils.computeByYi(BigDecimal.valueOf(item.getTotalCap())));
            p.setZhangJiaShu(item.getShangZhangJiaShu());
            p.setDieJiaShu(item.getXiaDieJiaShu());
            p.setLingZhangStock(item.getLingZhangStockTitle() + "-" + item.getLingZhangStockCode());
            p.setLingZhangChgPtg(item.getLingZhangChgPtg());
            p.setLingDieStock(item.getLingDieStockTitle() + "-" + item.getLingDieStockCode());
            p.setLingDieChgPtg(item.getLingDieChgPtg());
            p.initUserIdAndTime(1L);
            try {
                getRepository().persist(p);
//            } catch (DuplicateKeyException ignored) {
            } catch (ConstraintViolationException ignored) {
                // fall through
            } catch (Exception e) {
                log.error("error when insert ", e);
            }
        }
    }

    public List<IndustryDailyStatPersist> getByStatDateGe(LocalDate localDate) {
        return getRepository().findAllByStatDateGe(DateUtils.formatAsYyyyMMdd(localDate));
    }

    public List<IndustryDailyStatPersist> getByStatDate(LocalDate localDate) {
        return getRepository().findAllByStatDate(DateUtils.formatAsYyyyMMdd(localDate));
    }
}
