package cn.vt.rest.third.sse;

import cn.vt.rest.third.sse.vo.EtfDailyVolumeResp;
import cn.vt.rest.third.sse.vo.FundListResp;
import cn.vt.rest.third.sse.vo.TotalSharesResp;
import cn.vt.rest.third.sse.vo.TradeInfoResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests for {@link SseEtfClientUtils}.
 *
 * @author lei.liu
 * @since 2025-01-04
 */
@Slf4j
public class SseEtfClientUtilsTest {

    @Test
    void getGoldenEtfList() {
        List<FundListResp.FundListItem> goldenEtfList = SseEtfClientUtils.getGoldenEtfList();
        assertThat(goldenEtfList, notNullValue());
        int size = goldenEtfList.size();
        assertThat(size, greaterThan(0));
    }

    @Test
    void getQdiiEtfList() {
        List<FundListResp.FundListItem> qdiiEtfList = SseEtfClientUtils.getQdiiEtfList();
        int size = qdiiEtfList.size();
        assertThat(size, greaterThan(0));
    }

    @Test
    void getVolumeInWan() {
        String code = "513100";
        LocalDate date = LocalDate.of(2025, 1, 3);
        while (true) {
            TotalSharesResp volumeInWan = SseEtfClientUtils.getTotalVolumeInWan(code, date);
            List<TotalSharesResp.Item> list = volumeInWan.getResult();
            if (CollectionUtils.isNotEmpty(list) && list.size() == 1) {
                TotalSharesResp.Item item = list.get(0);
                log.info("{}", item);
            }
            date = date.minusDays(1);
            if (date.isBefore(LocalDate.of(2024, 12, 25))) {
                return;
            }
        }
    }

    @Test
    void getDailyVolumes() {
        LocalDate date = LocalDate.of(2025, 1, 3);
        while (true) {
            List<EtfDailyVolumeResp.Item> dailyVolumes = SseEtfClientUtils.getDailyVolumes(date);
            if (CollectionUtils.isNotEmpty(dailyVolumes)) {
                for (EtfDailyVolumeResp.Item dailyVolume : dailyVolumes) {
                    log.info("dailyVolume={}", dailyVolume);
                }
            }
            date = date.minusDays(1);
            if (date.isBefore(LocalDate.of(2024, 12, 31))) {
                return;
            }
        }
    }

    @Test
    void getTradeInfo() {
        String code = "513100";
        LocalDate date = LocalDate.of(2025, 1, 3);
        while (true) {
            TradeInfoResp volumeInWan = SseEtfClientUtils.getTradeInfo(code, date);
            List<TradeInfoResp.Item> list = volumeInWan.getResult();
            if (CollectionUtils.isNotEmpty(list) && list.size() == 1) {
                TradeInfoResp.Item item = list.get(0);
                log.info("{}", item);
            }
            date = date.minusDays(1);
            if (date.isBefore(LocalDate.of(2024, 12, 25))) {
                return;
            }
        }
    }

}
