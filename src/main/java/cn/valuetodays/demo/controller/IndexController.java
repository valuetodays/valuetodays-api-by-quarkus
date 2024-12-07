package cn.valuetodays.demo.controller;

import cn.valuetodays.demo.persist.IpPersist;
import cn.valuetodays.demo.service.IpService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-19
 */
@Path("/")
public class IndexController {
    @Inject
    private IpService ipService;

    @GET
    public Map<String, Object> index() {
        IpPersist n = ipService.getRepository().findById(1L).orElse(null);
        return Map.of("data", n, "code", 0, "time", System.currentTimeMillis()); // <4>
    }
}
