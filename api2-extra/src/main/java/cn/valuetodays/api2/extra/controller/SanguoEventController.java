package cn.valuetodays.api2.extra.controller;

import java.util.List;

import cn.valuetodays.api2.extra.persist.SanguoEventPO;
import cn.valuetodays.api2.extra.service.SanguoEventServiceImpl;
import cn.vt.R;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 * @author lei.liu
 * @since 2022-10-05
 */
@Path("/extra/sanguoEvent")
public class SanguoEventController {

    @Inject
    SanguoEventServiceImpl sanguoEventService;

    @Path("/findAll")
    @POST
    public R<List<SanguoEventPO>> findAll() {
        return R.success(sanguoEventService.list());
    }
}
