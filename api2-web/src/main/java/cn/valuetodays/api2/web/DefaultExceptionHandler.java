package cn.valuetodays.api2.web;

import cn.valuetodays.api2.basic.component.VtNatsClient;
import cn.valuetodays.api2.basic.service.NotifyServiceImpl;
import cn.vt.R;
import cn.vt.exception.CommonException;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.context.ManagedExecutor;

@Provider
public class DefaultExceptionHandler implements ExceptionMapper<Exception> {
    @Inject
    NotifyServiceImpl notifyService;
    @Inject
    ManagedExecutor managedExecutor;

    @Inject
    VtNatsClient natsClient;

    @Override
    public Response toResponse(Exception exception) {
        String msg;
        if (exception instanceof CommonException) {
            msg = exception.getMessage();
        } else {
            msg = "Exception: " + exception.getMessage();
        }

        managedExecutor.execute(() -> {
            natsClient.publishApplicationException(msg, exception);
        });
        return Response.status(Response.Status.OK).entity(R.fail(msg)).build();
    }
}
