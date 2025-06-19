package cn.valuetodays.api2.module.fortune.channel;

import java.util.List;

import cn.valuetodays.api2.module.fortune.channel.haitong.HaitongStockHoldersParser;
import cn.valuetodays.api2.module.fortune.channel.huabao.HuabaoStockHoldersParser;


/**
 * .
 *
 * @author lei.liu
 * @since 2024-04-14
 */
public class StockHoldersParserFactory {
    private StockHoldersParserFactory() {
    }

    public static StockHoldersParser choose(String text) {
        List<StockHoldersParser> list = List.of(
            new HuabaoStockHoldersParser(),
            new HaitongStockHoldersParser()
        );
        for (StockHoldersParser p : list) {
            if (p.isSupported(text)) {
                return p;
            }
        }
        return null;
    }
}
