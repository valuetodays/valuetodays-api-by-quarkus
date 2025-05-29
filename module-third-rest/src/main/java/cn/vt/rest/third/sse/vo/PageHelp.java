package cn.vt.rest.third.sse.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-04
 */
@Data
public class PageHelp<T extends Serializable> implements Serializable {
    //        private int beginPage;
//        private int cacheSize;
//        private int endPage;
    // 总页面数
    private int pageCount;
    // 每页条数
    private int pageSize;
    // 总条数
    private int total;
    private List<T> data;
}
