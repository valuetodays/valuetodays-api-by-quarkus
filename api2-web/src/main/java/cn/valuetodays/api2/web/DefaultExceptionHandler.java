package cn.valuetodays.api2.web;

import java.util.List;

import cn.valuetodays.api2.basic.component.VtNatsClient;
import cn.valuetodays.quarkus.commons.base.RunAsync;
import cn.vt.R;
import cn.vt.exception.CommonException;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class DefaultExceptionHandler extends RunAsync implements ExceptionMapper<Exception> {
    @Inject
    VtNatsClient natsClient;
    private List<String> excludeMsgsPrefixNotNotify = List.of("No static resource");

    @Override
    public Response toResponse(Exception exception) {
        String msg;
        if (exception instanceof CommonException) {
            msg = exception.getMessage();
        } else {
            msg = "Exception: " + exception.getMessage();
        }
        log.error("error", exception);

        boolean excludeByMsgPrefix = excludeMsgsPrefixNotNotify.stream().anyMatch(msg::startsWith);
        if (!excludeByMsgPrefix) {
            super.executeAsync(() -> {
                natsClient.publishApplicationException(msg, exception);
            });
        }
        return Response.status(Response.Status.OK).entity(R.fail(msg)).build();
    }
}
