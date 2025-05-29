package cn.vt.rest.third.eastmoney;

import cn.vt.core.TitleCapable;
import lombok.Getter;

import java.util.Locale;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-04-02 13:20
 */
public final class QuoteEnums {

    public static QuoteEnums.Region byCode(String code) {
        for (Region r : Region.values()) {
            if (r.indexCode().equals(code)) {
                return r;
            }
        }
        return null;
    }

    // todo 是否可以与StockEnums.RegionEnum合并
    @Getter
    public enum Region implements TitleCapable {
        SHANGHAI("上海") {
            @Override
            public String secid() {
                return "1";
            }

            @Override
            public String shortCode() {
                return "sh";
            }

            @Override
            public String indexCode() {
                return "000001";
            }
        },
        SHENZHEN("深圳") {
            @Override
            public String secid() {
                return "0";
            }

            @Override
            public String shortCode() {
                return "sz";
            }

            @Override
            public String indexCode() {
                return "399001";
            }
        },
        NEEQ("北证50") {
            @Override
            public String secid() {
                return "0";
            }

            @Override
            public String shortCode() {
                return "not-confirm";
            }

            @Override
            public String indexCode() {
                return "899050";
            }
        },
        HONGKONG("香港") {
            @Override
            public String secid() {
                return "2";
            }

            @Override
            public String shortCode() {
                return "hk";
            }

            @Override
            public String indexCode() {
                return null;
            }
        };

        private final String title;

        Region(String title) {
            this.title = title;
        }

        public abstract String secid();

        public abstract String shortCode();

        public abstract String indexCode();

        public String formatPrefixIndexCodeUpper() {
            return (shortCode() + indexCode()).toUpperCase(Locale.ENGLISH);
        }

        ;
    }
}
