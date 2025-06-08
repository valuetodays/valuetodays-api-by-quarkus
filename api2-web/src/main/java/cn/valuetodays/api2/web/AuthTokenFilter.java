package cn.valuetodays.api2.web;

import cn.vt.R;
import cn.vt.auth.AuthUser;
import cn.vt.auth.AuthUserHolder;
import cn.vt.util.StringExUtils;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

@Provider
@Priority(Priorities.AUTHENTICATION) // 确保优先执行
@Slf4j
public class AuthTokenFilter implements ContainerRequestFilter {
    @Inject
    AuthUserHolder authUserHolder;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        log.info("check token 1 path={}", path);
        if (isWhiteListed(path)) {
            // 跳过验证
            return;
        }
        log.info("check token 2 path={}", path);

        // 从 Header 中获取 Token
        String token = requestContext.getHeaderString("Authorization");
        log.info("check token 3 token={}", token);
        if (token == null || !isValidToken(token)) {
            Response resp = Response.ok()
                .entity(R.noAuthError("Unauthorized: token missing or invalid"))
                .build();
            requestContext.abortWith(resp);
        }
    }

    private boolean isWhiteListed(String path) {
        // 统一处理成带前缀斜杠
        String pathToUse = StringExUtils.makePrefix(path, "/");

        // 不需要校验的路径（支持前缀匹配）
        // 连工具类都不需要了
        return pathToUse.contains("/feign/")
            || pathToUse.contains("/anon/") // anonymous
            || pathToUse.contains("/anno/")
            || pathToUse.contains("/open/")
            || pathToUse.contains("/public/");
    }

    private boolean isValidToken(String token) {
        AuthUser authUserByToken = authUserHolder.getAuthUserByToken(token);
        return Objects.nonNull(authUserByToken);
    }
}
