package cn.valuetodays.api2.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;

import cn.valuetodays.quarkus.commons.base.BaseAuthorizationController;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Provider
//@PreMatching
//@Priority(1)
@Slf4j
public class AutoUserInjectFilter extends BaseAuthorizationController implements ContainerRequestFilter {

    @Inject
    ObjectMapper objectMapper;
    @Inject
    ResourceInfo resourceInfo; // 注入当前请求信息

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method resourceMethod = resourceInfo.getResourceMethod();
        log.info("resourceMethod={}", resourceMethod);
        // 只拦截 POST，跳过 GET/DELETE
        if (!"POST".equalsIgnoreCase(requestContext.getMethod())) {
            return;
        }

        // 读取原始请求体
        InputStream originalStream = requestContext.getEntityStream();
        byte[] requestBodyBytes = originalStream.readAllBytes();

        // 这里可以选择性解析，如果解析失败就跳过
        try {
            // 只要包含 currentLoginUserId 字段就处理（可以用更优的判断）
            String json = new String(requestBodyBytes);
            if (json.contains("currentLoginUserId")) {
                // 动态读取 class，需要你自己根据请求路径映射到 DTO 类
                // 简化：这里只做统一赋值，强制转为 Map 处理
                var map = objectMapper.readValue(requestBodyBytes, Map.class);

                byte[] modifiedBody = objectMapper.writeValueAsBytes(map);
                requestContext.setEntityStream(new ByteArrayInputStream(modifiedBody));
            } else {
                // 如果不是目标请求，恢复原请求体
                requestContext.setEntityStream(new ByteArrayInputStream(requestBodyBytes));
            }
        } catch (Exception e) {
            // 恢复请求体，防止请求体丢失
            requestContext.setEntityStream(new ByteArrayInputStream(requestBodyBytes));
        }
    }

}
