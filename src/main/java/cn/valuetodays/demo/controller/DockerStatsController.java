package cn.valuetodays.demo.controller;

import cn.valuetodays.demo.persist.DockerStatsPersist;
import cn.valuetodays.demo.service.DockerStatsService;
import cn.valuetodays.demo.vo.save.DockerStatsReq;
import cn.vt.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.jboss.resteasy.reactive.server.multipart.FileItem;
import org.jboss.resteasy.reactive.server.multipart.FormValue;
import org.jboss.resteasy.reactive.server.multipart.MultipartFormDataInput;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-01-24
 */
@Path("/dockerStats")
@Slf4j
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
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Map<String, Object> saveByText(MultipartFormDataInput dataInput) {
        String text = getFileContentAsString(dataInput, "file", StandardCharsets.US_ASCII);
        if (StringUtils.isBlank(text)) {
            return Map.of();
        }

        managedExecutor.execute(() -> {
            String json = "[" + StringUtils.substringBeforeLast(text, ",") + "]";
            List<DockerStatsReq> dockerStatsReqs = JsonUtils.fromJson(json, new TypeReference<List<DockerStatsReq>>() {
            });
            for (DockerStatsReq obj : dockerStatsReqs) {
                dockerStatsService.save(obj.toPersist());
            }
        });
        return Map.of("data", 1, "code", 0, "time", System.currentTimeMillis()); // <4>
    }

    private String getFileContentAsString(MultipartFormDataInput dataInput, String fileId, Charset charset) {
        Map<String, Collection<FormValue>> values = dataInput.getValues();
        if (MapUtils.isEmpty(values)) {
            return null;
        }
        Collection<FormValue> fileCollection = values.get(fileId);
        FormValue[] fileFormValue = fileCollection.toArray(FormValue[]::new);
        FormValue formValue = fileFormValue[0];
        FileItem fileItem = formValue.getFileItem();
        java.nio.file.Path file = fileItem.getFile();
        try {
            return FileUtils.readFileToString(file.toFile(), charset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
