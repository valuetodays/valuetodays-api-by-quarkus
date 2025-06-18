package cn.valuetodays.api2.extra.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * .
 *
 * @author lei.liu
 * @since 2020-09-07 18:02
 */
@Data
public class MyLinkTreeVo {
    private List<Item> itemList = new ArrayList<>();

    @Data
    public static class Parent {
        private Long id;
        private Long parentId;
        private String title;
        private String favIcon;
        private List<Item> children;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Item extends Parent {
        private String url;
    }

}

