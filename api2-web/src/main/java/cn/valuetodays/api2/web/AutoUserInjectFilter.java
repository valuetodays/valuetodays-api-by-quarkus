//package cn.valuetodays.api2.web;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.quarkus.security.identity.SecurityIdentity;
//import jakarta.annotation.Priority;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.container.ContainerRequestContext;
//import jakarta.ws.rs.container.ContainerRequestFilter;
//import jakarta.ws.rs.container.PreMatching;
//import jakarta.ws.rs.ext.Provider;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//@Provider
//@PreMatching
//@Priority(1)
//public class AutoUserInjectFilter implements ContainerRequestFilter {
//
//    @Inject
//    SecurityIdentity securityIdentity;
//
//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//        // 只拦截 POST，跳过 GET/DELETE
//        if (!"POST".equalsIgnoreCase(requestContext.getMethod())) {
//            return;
//        }
//
//        // 读取原始请求体
//        InputStream originalStream = requestContext.getEntityStream();
//        byte[] requestBodyBytes = originalStream.readAllBytes();
//
//        // 这里可以选择性解析，如果解析失败就跳过
//        try {
//            // 只要包含 currentLoginUserId 字段就处理（可以用更优的判断）
//            String json = new String(requestBodyBytes);
//            if (json.contains("currentLoginUserId")) {
//                // 动态读取 class，需要你自己根据请求路径映射到 DTO 类
//                // 简化：这里只做统一赋值，强制转为 Map 处理
//                var map = objectMapper.readValue(requestBodyBytes, Map.class);
//                map.put("currentLoginUserId", getCurrentUserId());
//
//                byte[] modifiedBody = objectMapper.writeValueAsBytes(map);
//                requestContext.setEntityStream(new ByteArrayInputStream(modifiedBody));
//            } else {
//                // 如果不是目标请求，恢复原请求体
//                requestContext.setEntityStream(new ByteArrayInputStream(requestBodyBytes));
//            }
//        } catch (Exception e) {
//            // 恢复请求体，防止请求体丢失
//            requestContext.setEntityStream(new ByteArrayInputStream(requestBodyBytes));
//        }
//    }
//
//    private Long getCurrentUserId() {
//        // 假设你通过 quarkus-security 已登录
//        return Long.valueOf(securityIdentity.getPrincipal().getName());
//    }
//}
