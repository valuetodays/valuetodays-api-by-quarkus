package cn.valuetodays.demo.controller;

import io.quarkus.runtime.configuration.ConfigUtils;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@Path("/hello")
public class GreetingResource {
    @ConfigProperty(name = "hello")
    private String hello;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }
    @GET
    @Path("helloVar")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloVar() {
        List<String> profiles = ConfigUtils.getProfiles();
        return "Hello: " + hello + "\n\nprofiles: " + String.join(",", profiles);
    }
}
