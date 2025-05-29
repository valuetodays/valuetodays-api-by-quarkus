package cn.vt.rest.third.eastmoney;

import cn.vt.rest.third.eastmoney.vo.EastMoneyIndustryInfoData;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Tests for {@link EastMoneyIndustryUtils}.
 *
 * @author lei.liu
 * @since 2025-05-17
 */
@Slf4j
public class EastMoneyIndustryUtilsTest {

    @Test
    public void getIndustryDailyInfo() {
        List<EastMoneyIndustryInfoData.IndustryInfoItemTyped> list = EastMoneyIndustryUtils.getIndustryDailyInfo();
        MatcherAssert.assertThat(list, CoreMatchers.notNullValue());
        MatcherAssert.assertThat(list, CoreMatchers.not(IsEmptyCollection.empty()));

        log.info("list.size={}", list.size());
    }
}
