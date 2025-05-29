package cn.vt.rest.third;

import cn.vt.core.TitleCapable;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author lei.liu
 * @since 2021-03-23 18:17
 */
public final class StockEnums {
    public static final String[] REGION_SHORT_CODE_LIST =
        Arrays.stream(Region.values())
            .map(Region::getShortCode)
            .distinct()
            .toArray(String[]::new);

    @Getter
    public enum Region implements TitleCapable {
        /*
上海证券交易所上市交易基金列表
https://zh.wikipedia.org/wiki/%E4%B8%8A%E6%B5%B7%E8%AF%81%E5%88%B8%E4%BA%A4%E6%98%93%E6%89%80%E4%B8%8A%E5%B8%82%E4%BA%A4%E6%98%93%E5%9F%BA%E9%87%91%E5%88%97%E8%A1%A8
深圳证券交易所上市交易基金列表
https://zh.wikipedia.org/wiki/%E6%B7%B1%E5%9C%B3%E8%AF%81%E5%88%B8%E4%BA%A4%E6%98%93%E6%89%80%E4%B8%8A%E5%B8%82%E4%BA%A4%E6%98%93%E5%9F%BA%E9%87%91%E5%88%97%E8%A1%A8
         */
        SHANGHAI("上海", new String[]{"5", "6"}, "SH", "1"),
        SHENZHEN("深圳", new String[]{"0", "1", "3"}, "SZ", "0"),
        HONGKONG("香港", new String[]{}, "HK", "2");

        private final String title;
        private final List<String> codePrefixList;
        private final String shortCode;
        private final String secid;

        Region(String title, String[] codePrefixList, String shortCode, String secid) {
            this.title = title;
            this.codePrefixList = Arrays.asList(codePrefixList);
            this.shortCode = shortCode;
            this.secid = secid;
        }

        /**
         * @param code 600036
         */
        public static Region byCode(String code) {
            for (Region value : Region.values()) {
                List<String> codePrefixList = value.getCodePrefixList();
                for (String codePrefix : codePrefixList) {
                    if (code.startsWith(codePrefix)) {
                        return value;
                    }
                }
            }
            return null;
        }

        public String getRegionShortCode() {
            return shortCode;
        }
    }

}
