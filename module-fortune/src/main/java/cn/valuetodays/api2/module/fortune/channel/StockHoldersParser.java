package cn.valuetodays.api2.module.fortune.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.vt.moduled.fortune.enums.FortuneCommonEnums;
import cn.vt.text.FieldAndTile;
import cn.vt.text.TitleAndBodyTextParser;
import org.apache.commons.lang3.StringUtils;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-04-14
 */
public abstract class StockHoldersParser {
    public abstract boolean isSupported(String text);

    public abstract FortuneCommonEnums.Channel channel();

    public abstract List<FieldAndTile> getFieldAndTitleList();

    public abstract boolean isTitleLine(String line);

    public abstract Class<? extends StockHoldersInfo> getSelfHoldersInfo();

    /**
     * @param text 文本
     * @return StockHoldersInfo
     * @see StockHoldersInfo
     */
    public final List<StockHoldersInfo> parse(String text, String separator) {
        final String[] lines = StringUtils.split(text, separator);
        String headerLine = null;
        final List<String> bodyLines = new ArrayList<>(lines.length);
        for (String line : lines) {
            if (isTitleLine(line)) {
                headerLine = line;
                continue;
            }
            if (Objects.nonNull(headerLine)) {
                bodyLines.add(line);
            }
        }

        List<? extends StockHoldersInfo> infoList = TitleAndBodyTextParser.parseAsObj(
            headerLine, bodyLines, " ", getFieldAndTitleList(),
            getSelfHoldersInfo()
        );
        return infoList.stream().map(e -> {
            e.initValues();
            StockHoldersInfo item = new StockHoldersInfo();
            item.setStatDate(e.getStatDate());
            item.setSecCode(e.getSecCode());
            item.setSecAbbr(e.getSecAbbr());
            item.setHoldVolume(e.getHoldVolume());
            item.setCostPrice(e.getCostPrice());
            item.setMarketPrice(e.getMarketPrice());
            return item;
        }).toList();
    }
}
