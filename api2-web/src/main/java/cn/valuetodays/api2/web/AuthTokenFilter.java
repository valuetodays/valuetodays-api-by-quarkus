package cn.valuetodays.api2.web;

import cn.vt.util.StringExUtils;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

//@Provider
//@Priority(Priorities.AUTHENTICATION) // 确保优先执行
public class AuthTokenFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        if (isWhiteListed(path)) {
            System.out.println("in whiteList");
            return; // 跳过验证
        }
        System.out.println("not in whiteList");

        // 从 Header 中获取 Token
        String token = requestContext.getHeaderString("Authorization");
        System.out.println("token=" + token);
        if (token == null || !isValidToken(token)) {
            Response resp = Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized: token missing or invalid").build();
            requestContext.abortWith(resp);
        }
    }

    private boolean isWhiteListed(String path) {
        // 统一处理成带前缀斜杠
        String pathToUse = StringExUtils.makePrefix(path, "/");

        // 不需要登录的路径（支持前缀匹配）
        // 连工具类都不需要了
        return pathToUse.contains("/feign/")
            || pathToUse.contains("/anon/") // anonymous
            || pathToUse.contains("/anno/")
            || pathToUse.contains("/open/")
            || pathToUse.contains("/public/");
    }

    private boolean isValidToken(String token) {
        // 实际应校验 JWT、Redis 或 DB，这里仅示意
        // todo
        return token.equals("valid-token");
    }
}
