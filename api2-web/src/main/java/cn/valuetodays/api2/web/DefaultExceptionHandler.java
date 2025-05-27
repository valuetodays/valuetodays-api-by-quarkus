package cn.valuetodays.api2.web;

import cn.valuetodays.api2.basic.service.NotifyServiceImpl;
import cn.vt.R;
import cn.vt.exception.CommonException;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;

@Provider
public class DefaultExceptionHandler implements ExceptionMapper<Exception> {
    @Inject
    NotifyServiceImpl notifyService;
    @Inject
    ManagedExecutor managedExecutor;
    @ConfigProperty(name = "quarkus.application.name")
    String applicationName;

    @Override
    public Response toResponse(Exception exception) {
        String msg;
        if (exception instanceof CommonException) {
            msg = exception.getMessage();
        } else {
            msg = "Exception: " + exception.getMessage();
        }

        managedExecutor.execute(() -> {
            notifyService.notifyApplicationException(applicationName, msg, exception);
        });
        return Response.status(Response.Status.OK).entity(R.fail(msg)).build();
    }
}
