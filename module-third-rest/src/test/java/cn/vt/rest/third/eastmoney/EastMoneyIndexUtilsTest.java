package cn.vt.rest.third.eastmoney;

import cn.vt.rest.third.eastmoney.vo.QuoteDailyStatVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Tests for {@link EastMoneyIndexUtils}.
 *
 * @author lei.liu
 * @since 2025-04-29
 */
@Slf4j
public class EastMoneyIndexUtilsTest {
    @Test
    void getIndexKline() {
        List<QuoteDailyStatVO> indexKlines = EastMoneyIndexUtils.getIndexKline("000001");
        log.info("{}", indexKlines);
        if (CollectionUtils.isNotEmpty(indexKlines)) {
            QuoteDailyStatVO last = indexKlines.getLast();
            log.info("last: {}", last);
        } else {
            log.info("no element");
        }
    }

}
