package cn.vt.rest.third.eastmoney;

import cn.vt.exception.CommonException;
import cn.vt.rest.third.eastmoney.vo.EastMoneyStockKlineData;
import cn.vt.rest.third.eastmoney.vo.EastMoneyStockKlineResp;
import cn.vt.rest.third.eastmoney.vo.QuoteDailyStatVO;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.JsonUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-29
 */
public class EastMoneyIndexUtils {

    public static List<QuoteDailyStatVO> getIndexKline(String code) {
        QuoteEnums.Region region = QuoteEnums.byCode(code);
        if (Objects.isNull(region)) {
            throw new CommonException("illegal code: " + code);
        }
        // page : https://quote.eastmoney.com/zs000300.html
        // 处理全量数据的天数要大一点，使用120处理增量数据
        long lmt = 120;
        String url = "http://27.push2his.eastmoney.com/api/qt/stock/kline/get?"
            + "cb=jQuery35105866457293913834_1680408774022"
//            + "&secid=1.000001"
            + "&secid=" + region.secid() + "." + code
            + "&ut=fa5fd1943c7b386f172d6893dbfba10b"
            + "&fields1=f1%2Cf2%2Cf3%2Cf4%2Cf5%2Cf6"
            + "&fields2=f51%2Cf52%2Cf53%2Cf54%2Cf55%2Cf56%2Cf57%2Cf58%2Cf59%2Cf60%2Cf61"
            + "&klt=101"
            + "&fqt=1"
            // ?为什么是这个值
            + "&end=20500101"
            + "&lmt=" + lmt
//            + "&_=1680408774027"
            + "&_=" + System.currentTimeMillis();

        String respStr = HttpClient4Utils.doGet(url);
        String jsonStr = EastMoneyCommons.substringBusiJsonString(respStr);
        EastMoneyStockKlineResp klineResp = JsonUtils.fromJson(jsonStr, EastMoneyStockKlineResp.class);
        EastMoneyStockKlineData data = klineResp.getData();
        if (Objects.nonNull(data)) {
            String[] klines = data.getKlines();
            return Arrays.stream(klines).map(QuoteDailyStatVO.TRANSFORMER).collect(Collectors.toList());
        }
        return List.of();
    }
}
