package cn.valuetodays.api2.module.fortune.channel.huabao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.vt.text.FieldAndTile;
import cn.vt.text.TitleAndBodyTextParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-10-17
 */
@Slf4j
public class HuabaoTradeInterestParser {
    private static final String SQL_TEMPLATE = "INSERT INTO `eblog`.`fortune_stock_trade_interest` "
        + "(`trade_date`, `trade_time`, `amount_fee`, `content`) "
        + "VALUES ({trade_date}, '{trade_time}', {amount_fee}, '{content}')";

    private HuabaoTradeInterestParser() {
    }

    public static List<String> parse(String text) {
        // 利息支出有两种形式：卖出时归还利息；月底自动收息
        final String[] mustContainedTextArr = {"还利息", "(利息扣收)"};

        final String[] lines = StringUtils.split(text, "\r\n");
        String headerLine = null;
        List<String> bodyLines = new ArrayList<>(lines.length);
        for (String line : lines) {
            if (line.startsWith("成交日期")) {
                headerLine = line;
                continue;
            }
            if (Objects.nonNull(headerLine) && Arrays.stream(mustContainedTextArr).anyMatch(line::contains)) {
                bodyLines.add(line);
            }
        }

        final List<FieldAndTile> fieldAndTitleList = Arrays.asList(
            FieldAndTile.of("trade_date", "成交日期"),
            FieldAndTile.of("trade_time", "成交时间"),
            FieldAndTile.of("amount_fee", "发生金额"),
            FieldAndTile.of("content", "摘要")
        );
        List<Map<String, String>> maps = TitleAndBodyTextParser.parseAsMap(
            headerLine, bodyLines, " ", fieldAndTitleList);
        return maps.stream().map(e -> {
            String tmpSql = SQL_TEMPLATE;
            for (Map.Entry<String, String> entry : e.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                tmpSql = StringUtils.replace(tmpSql, "{" + key + "}", value);
            }
            return tmpSql;
        }).toList();

    }

}
