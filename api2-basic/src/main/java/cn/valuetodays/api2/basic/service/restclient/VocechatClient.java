
package cn.valuetodays.api2.basic.service.restclient;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "vocechat-api")
public interface VocechatClient {
    @POST
    @Path("/api/bot/send_to_user/{id}")
    @Consumes("text/plain")
    String sendToUser(@PathParam("id") Long id, @HeaderParam("x-api-key") String apiKey, String content);

    @POST
    @Path("{bizPath: .*}")
    Uni<String> sendToUserOrGroup(@PathParam("bizPath") String bizPath,
                                  @HeaderParam("x-api-key") String apiKey,
                                  @HeaderParam("Content-Type") String contentType,
                                  String content);
}

