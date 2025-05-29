package cn.vt.rest.third.xueqiu.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 雪球股票实时报价.
 * <p>
 * quote：报价
 *
 * @author lei.liu
 * @since 2023-05-23 09:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class XueQiuStockRealtimeQuoteResp extends XueQiuBaseResp<List<XueQiuStockRealtimeQuoteData>> {
}
