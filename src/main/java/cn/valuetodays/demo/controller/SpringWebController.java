package cn.valuetodays.demo.controller;

import io.quarkus.runtime.configuration.ConfigUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-12-07
 */
@RestController
@RequestMapping("/spring")
public class SpringWebController {
    @ConfigProperty(name = "hello")
    private String hello;

    @GetMapping("/helloVar")
    public String helloVar() {
        List<String> profiles = ConfigUtils.getProfiles();
        return "helloVar: " + hello + "\n\nprofiles: " + String.join(",", profiles);
    }
}
