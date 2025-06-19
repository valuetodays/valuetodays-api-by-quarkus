package cn.valuetodays.api2.module.fortune.channel.huabao;

import java.util.List;

import cn.valuetodays.api2.module.fortune.channel.StockHoldersInfo;
import cn.valuetodays.api2.module.fortune.channel.StockHoldersParser;
import cn.vt.moduled.fortune.enums.FortuneCommonEnums;
import cn.vt.text.FieldAndTile;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-04-14
 */
public class HuabaoStockHoldersParser extends StockHoldersParser {
    @Override
    public boolean isSupported(String text) {
        return text.contains("摊簿成本价");
    }

    @Override
    public FortuneCommonEnums.Channel channel() {
        return FortuneCommonEnums.Channel.HUABAO_XIN;
    }

    @Override
    public List<FieldAndTile> getFieldAndTitleList() {
        return List.of(
            FieldAndTile.of("secCode", "证券代码"),
            FieldAndTile.of("secAbbr", "证券名称"),
            FieldAndTile.of("holdVolume", "证券数量"),
            FieldAndTile.of("costPrice", "摊簿成本价"),
            FieldAndTile.of("marketPrice", "当前价")
        );
    }

    @Override
    public boolean isTitleLine(String line) {
        return line.startsWith("证券代码");
    }

    @Override
    public Class<? extends StockHoldersInfo> getSelfHoldersInfo() {
        return StockHoldersInfo.class;
    }
}
