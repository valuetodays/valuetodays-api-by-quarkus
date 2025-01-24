package cn.valuetodays.demo.controller;

import cn.valuetodays.demo.persist.DockerStatsPersist;
import cn.valuetodays.demo.service.DockerStatsService;
import cn.valuetodays.demo.vo.save.DockerStatsReq;
import cn.vt.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.context.ManagedExecutor;

import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-24
 */
@Path("/dockerStats")
public class DockerStatsController {
    @Inject
    DockerStatsService dockerStatsService;
    @Inject
    ManagedExecutor managedExecutor;

    @GET
    @Path("getById")
    public Map<String, Object> getById() {
        DockerStatsPersist n = dockerStatsService.getRepository().findById(1L).orElse(null);
        return Map.of("data", n, "code", 0, "time", System.currentTimeMillis()); // <4>
    }

    @POST
    @Path("saveByText")
    public Map<String, Object> saveByText(String text) {
        if (StringUtils.isBlank(text)) {
            return Map.of();
        }
        managedExecutor.execute(() -> {
            String json = "[" + text + "{}]";
            List<DockerStatsReq> dockerStatsReqs = JsonUtils.fromJson(json, new TypeReference<List<DockerStatsReq>>() {
            });
            for (DockerStatsReq req : dockerStatsReqs) {
                if (StringUtils.isNotBlank(req.getName())) {
                    dockerStatsService.save(req.toPersist());
                }
            }
        });
        return Map.of("data", 1, "code", 0, "time", System.currentTimeMillis()); // <4>
    }
}
