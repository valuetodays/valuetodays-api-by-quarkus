package cn.vt.rest.third.eastmoney;

import cn.vt.rest.third.eastmoney.vo.EastMoneyIndustryInfoData;
import cn.vt.rest.third.eastmoney.vo.EastMoneyIndustryInfoResp;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.SleepUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-05-17
 */
@Slf4j
public class EastMoneyIndustryUtils {
    private EastMoneyIndustryUtils() {
    }

    public static List<EastMoneyIndustryInfoData.IndustryInfoItemTyped> getIndustryDailyInfo() {
        List<EastMoneyIndustryInfoData.IndustryInfoItemTyped> list = new ArrayList<>(100);
        int pn = 1;
        while (true) {
            List<EastMoneyIndustryInfoData.IndustryInfoItemTyped> tmpList = getIndustryDailyInfoByPage(pn);
            if (CollectionUtils.isEmpty(tmpList)) {
                break;
            }
            list.addAll(tmpList);
            SleepUtils.sleep(RandomUtils.secure().randomLong(100, 200));
            pn++;
        }

        return list;
    }

    /**
     * https://quote.eastmoney.com/center/gridlist.html#industry_board
     *
     * @param pn page number
     * @return
     */
    private static List<EastMoneyIndustryInfoData.IndustryInfoItemTyped> getIndustryDailyInfoByPage(int pn) {
        final String jsCallback = "jQuery37103155462727718401_1747492183995";
        String url = "https://push2.eastmoney.com/api/qt/clist/get"
            + "?"
            + "np=1"
            + "&fltt=1"
            + "&invt=2"
            + "&cb=" + jsCallback
            + "&fs=m%3A90%2Bt%3A2%2Bf%3A!50"
            + "&fields=f12%2Cf13%2Cf14%2Cf1%2Cf2%2Cf4%2Cf3%2Cf152%2Cf20%2Cf8%2Cf104%2Cf105%2Cf128%2Cf140%2Cf141%2Cf207%2Cf208%2Cf209%2Cf136%2Cf222"
            + "&fid=f3"
            + "&pn=" + pn
            + "&pz=20"
            + "&po=1"
            + "&dect=1"
            + "&ut=fa5fd1943c7b386f172d6893dbfba10b"
            + "&wbp2u=%7C0%7C0%7C0%7Cweb"
            + "&_=" + System.currentTimeMillis();

        String responseString = HttpClient4Utils.doGet(url);
        EastMoneyIndustryInfoResp resp = EastMoneyCommons.parseResponseAsObj(responseString, EastMoneyIndustryInfoResp.class);
        if (Objects.isNull(resp)) {
            return List.of();
        }
        EastMoneyIndustryInfoData data = resp.getData();
        if (Objects.isNull(data)) {
            return List.of();
        }
        List<EastMoneyIndustryInfoData.IndustryInfoItem> diff = data.getDiff();
        if (CollectionUtils.isEmpty(diff)) {
            return List.of();
        }
        return diff.stream()
            .map(EastMoneyIndustryInfoData.IndustryInfoItem::toTyped)
            .toList();
    }
}
