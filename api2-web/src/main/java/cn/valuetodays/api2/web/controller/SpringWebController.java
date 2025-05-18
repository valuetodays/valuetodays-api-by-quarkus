package cn.valuetodays.api2.web.controller;

import io.quarkus.runtime.configuration.ConfigUtils;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-12-07
 */
@Path("/spring")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SpringWebController {
    @ConfigProperty(name = "hello")
    private String hello;

    @GET
    @Path("/helloVar")
    public String helloVar() {
        List<String> profiles = ConfigUtils.getProfiles();
        return "helloVar: " + hello + "\n\nprofiles: " + String.join(",", profiles);
    }
}
