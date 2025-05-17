package cn.valuetodays.demo.controller;

import cn.valuetodays.api2.persist.IpPersist;
import cn.valuetodays.demo.properties.GreetingProperties;
import cn.valuetodays.demo.service.IpService;
import cn.valuetodays.demo.task.DemoTask;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
    IpService ipService;

    @Inject
    DemoTask demoTask;

    @Inject
    GreetingProperties greetingProperties;


    @GET
    @Path("/")
    public Map<String, Object> hello() {
        return Map.of("msg", "Hello from Quarkus REST");
    }

    @GET
    @Path("ip")
    public Map<String, Object> ip() {
        IpPersist n = ipService.getRepository().findById(1L).orElse(null);
        return Map.of("data", n, "code", 0, "time", System.currentTimeMillis()); // <4>
    }

    @GET
    @Path("counter")
    public int counter() {
        return demoTask.getCounter();
    }
    @GET
    @Path("greetingProperties")
    @Produces({MediaType.APPLICATION_JSON})
    public GreetingProperties greetingProperties() {
        return greetingProperties;
    }

}
