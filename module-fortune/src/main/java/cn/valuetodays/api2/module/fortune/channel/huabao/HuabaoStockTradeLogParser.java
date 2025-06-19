package cn.valuetodays.api2.module.fortune.channel.huabao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.valuetodays.api2.module.fortune.channel.StockTradeLogParser;
import cn.vt.moduled.fortune.enums.FortuneCommonEnums;
import cn.vt.text.FieldAndTile;
import lombok.extern.slf4j.Slf4j;

/**
 * 华宝股票交易交易解析.
 *
 * @author lei.liu
 * @since 2023-09-21
 */
@Slf4j
public class HuabaoStockTradeLogParser extends StockTradeLogParser {

    private static final Map<String, FortuneCommonEnums.TradeType> TRADE_TYPE_MAP = new HashMap<>();

    static {
        TRADE_TYPE_MAP.put("融资买入", FortuneCommonEnums.TradeType.RONG_BUY);
        TRADE_TYPE_MAP.put("卖券还款", FortuneCommonEnums.TradeType.SELL_FOR_REPAY);
        TRADE_TYPE_MAP.put("卖出", FortuneCommonEnums.TradeType.SELL);
        TRADE_TYPE_MAP.put("买入", FortuneCommonEnums.TradeType.BUY);
        TRADE_TYPE_MAP.put("红利", FortuneCommonEnums.TradeType.PASSIVE_SELL);
    }

    @Override
    public boolean isSupported(String text) {
        return text.contains("成交日期") && text.contains("委托类别");
    }

    @Override
    public FortuneCommonEnums.Channel channel() {
        return FortuneCommonEnums.Channel.HUABAO;
    }

    @Override
    public Map<String, FortuneCommonEnums.TradeType> getTradeTypeMap() {
        return TRADE_TYPE_MAP;
    }

    @Override
    public List<FieldAndTile> getFieldAndTitleList() {
        return Arrays.asList(
            FieldAndTile.of("code", "证券代码"),
            FieldAndTile.of("typeCn", "委托类别"),
            FieldAndTile.of("trade_date", "成交日期"),
            FieldAndTile.of("trade_time", "成交时间"),
            FieldAndTile.of("quantity", "成交数量"),
            FieldAndTile.of("price", "成交价格"),
            FieldAndTile.of("yj", "佣金"),
            FieldAndTile.of("yh", "印花税"),
            FieldAndTile.of("gh", "过户费"),
            FieldAndTile.of("order_id", "成交编号")
        );
    }


    @Override
    public boolean isTitleLine(String line) {
        return line.startsWith("成交日期");
    }

}
