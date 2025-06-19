package cn.valuetodays.api2.web;

import java.io.IOException;

import cn.vt.R;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.WriterInterceptor;
import jakarta.ws.rs.ext.WriterInterceptorContext;

/**
 * wrapper response object as {@link R}
 *
 * @author lei.liu
 */
@Provider
public class GlobalResponseWriterInterceptor implements WriterInterceptor {

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException {
        Object entity = context.getEntity();

        if (entity != null && !(entity instanceof R<?>)) {
            context.setEntity(R.success(entity));
        }

        context.proceed();
    }
}
