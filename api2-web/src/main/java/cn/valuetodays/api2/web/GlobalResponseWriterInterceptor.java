package cn.valuetodays.api2.web;

import java.io.IOException;
import java.lang.reflect.Method;

import cn.vt.R;
import cn.vt.web.DontWrapResponseBody;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.WriterInterceptor;
import jakarta.ws.rs.ext.WriterInterceptorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * wrapper response object as {@link R}
 *
 * @author lei.liu
 */
@Provider
@Slf4j
public class GlobalResponseWriterInterceptor implements WriterInterceptor {
    @Context
    ResourceInfo resourceInfo; // 注入当前请求信息
    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException {
        // 获取当前处理的方法和类
        Method method = resourceInfo.getResourceMethod();
        Class<?> resourceClass = resourceInfo.getResourceClass();

        // 判断是否有 @DoNotWrapResponse 注解，方法或类上有都跳过
        if (method.isAnnotationPresent(DontWrapResponseBody.class)
            || resourceClass.isAnnotationPresent(DontWrapResponseBody.class)) {
            context.proceed();
            return;
        }
        Object entity = context.getEntity();
        if (entity != null && !(entity instanceof R<?>)) {
            context.setEntity(R.success(entity));
        }

        context.proceed();
    }
}
