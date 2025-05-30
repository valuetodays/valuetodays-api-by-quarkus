package cn.vt.api.github;

import cn.vt.util.EnvironmentPropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-25
 */
public abstract class BaseApiGithub {
    // basePath 不要以/结尾，
    // 每个接口要以/开头
    public static final String API_GITHUB_BASE_PATH = "https://api.github.com";

    private String apiToken;

    public BaseApiGithub() {
        // rapidee -S -C -E -M GITHUB_TOKEN ghpxxx
        this(EnvironmentPropertyUtils.getString("GITHUB_TOKEN", null));
    }

    public BaseApiGithub(String apiKey) {
        this.apiToken = apiKey;
    }

    private static String buildUrl(String path) {
        return StringUtils.startsWithIgnoreCase(path, "http") ? path : API_GITHUB_BASE_PATH + path;
    }

    public <T> T get(String path, Class<T> responseEntityCls, Object... uriVariables) {
        String url = buildUrl(path);

        RestTemplate restTemplate = getRestTemplate();
        return restTemplate.getForObject(url, responseEntityCls, uriVariables);
    }

    private RestTemplate getRestTemplate() {
        // 借助拦截器的方式来实现塞统一的请求头
        ClientHttpRequestInterceptor interceptor = (httpRequest, bytes, execution) -> {
            httpRequest.getHeaders().set("Host", "api.github.com");
            httpRequest.getHeaders().set("Accept", "application/vnd.github.v3+json");
            httpRequest.getHeaders().set("Authorization", "token " + apiToken);
            return execution.execute(httpRequest, bytes);
        };

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(interceptor);
        return restTemplate;
    }

    public <T> List<T> getList(String path, Class<T[]> responseEntityCls, Object... uriVariables) {
        String url = buildUrl(path);
        RestTemplate restTemplate = getRestTemplate();
        T[] arr = restTemplate.getForObject(url, responseEntityCls, uriVariables);
        if (Objects.isNull(arr)) {
            return new ArrayList<>(0);
        }
        return new LinkedList<>(Arrays.stream(arr).toList());
    }

    public <T> ResponseEntity<T> put(String path, Object requestBody, Class<T> responseEntityCls, Object... uriVariables) {
        String url = buildUrl(path);
        RestTemplate restTemplate = getRestTemplate();
        HttpEntity<Object> httpEntity = new HttpEntity<>(requestBody);
        return restTemplate.exchange(url, HttpMethod.PUT, httpEntity, responseEntityCls, uriVariables);
    }
}
