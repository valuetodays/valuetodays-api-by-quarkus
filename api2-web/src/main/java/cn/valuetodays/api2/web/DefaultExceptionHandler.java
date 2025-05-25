package cn.valuetodays.api2.web;

import cn.vt.R;
import cn.vt.exception.CommonException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionHandler implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        String msg;
        if (exception instanceof CommonException) {
            msg = exception.getMessage();
            return Response.status(Response.Status.OK).entity(R.fail(msg)).build();
        } else {
            msg = "Exception: " + exception.getMessage();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
    }
}
