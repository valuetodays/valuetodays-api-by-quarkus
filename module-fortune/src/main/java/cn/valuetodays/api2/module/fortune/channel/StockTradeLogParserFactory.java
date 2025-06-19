package cn.valuetodays.api2.module.fortune.channel;


import java.util.List;

import cn.valuetodays.api2.module.fortune.channel.haitong.HaitongStockTradeLogParser;
import cn.valuetodays.api2.module.fortune.channel.huabao.HuabaoStockTradeLogParser;


/**
 * .
 *
 * @author lei.liu
 * @since 2024-02-07
 */
public final class StockTradeLogParserFactory {
    private StockTradeLogParserFactory() {
    }

    public static StockTradeLogParser choose(String text) {
        List<StockTradeLogParser> list = List.of(
            new HuabaoStockTradeLogParser(),
            new HaitongStockTradeLogParser()
        );
        for (StockTradeLogParser p : list) {
            if (p.isSupported(text)) {
                return p;
            }
        }
        return null;
    }
}
