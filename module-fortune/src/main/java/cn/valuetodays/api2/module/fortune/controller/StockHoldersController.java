package cn.valuetodays.api2.module.fortune.controller;

import java.util.List;

import cn.valuetodays.api2.module.fortune.persist.StockHoldersPO;
import cn.valuetodays.api2.module.fortune.service.StockHoldersServiceImpl;
import cn.valuetodays.api2.web.common.AffectedRowsResp;
import cn.valuetodays.quarkus.commons.base.BaseCrudController;
import cn.vt.web.req.SimpleTypesReq;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 * 我的证券账户持有服务
 *
 * @author lei.liu
 * @since 2023-01-10 21:38
 */
@Path("/stockHolders")
public class StockHoldersController
    extends BaseCrudController<Long, StockHoldersPO, StockHoldersServiceImpl> {

    @Path(value = "/parseTextAndSave")
    @POST
    public AffectedRowsResp parseTextAndSave(SimpleTypesReq req) {
        return getService().parseTextAndSave(req, getCurrentAccountId());
    }

    @Path(value = "/getCostPrice")
    @POST
    public List<StockHoldersPO> getCostPrice(SimpleTypesReq req) {
        return getService().getCostPrice(req);
    }


}
