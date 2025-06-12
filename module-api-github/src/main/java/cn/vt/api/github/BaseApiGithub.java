package cn.vt.api.github;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.vt.exception.CommonException;
import cn.vt.util.EnvironmentPropertyUtils;
import cn.vt.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-09-25
 */
@Slf4j
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

    private OkHttpClient httpClient = createHttpClient();

    private static String buildUrl(String path) {
        return StringUtils.startsWithIgnoreCase(path, "http") ? path : API_GITHUB_BASE_PATH + path;
    }

    public <T> T get(String path, Class<T> responseEntityCls, Object... uriVariables) {
        String url = buildUrl(path);

        Request.Builder builder = new Request.Builder();
        fillHeaders(builder);
        okhttp3.Request request = builder.url(replacePathVariables(url, uriVariables))
            .get()
            .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String json = response.body() != null ? response.body().string() : null;
            log.info("response.body()={}", json);
            if (json == null || json.isEmpty()) {
                return null;
            }
            return JsonUtils.fromJson(json, responseEntityCls);
        } catch (IOException e) {
            throw new CommonException(e);
        }
    }

    private OkHttpClient createHttpClient() {
        return new OkHttpClient.Builder()
            /*   .addInterceptor(chain -> {
                   okhttp3.Request request = chain.request();
                   okhttp3.Request.Builder newRequestBuilder = request.newBuilder()
                       .header("Host", "api.github.com")
                       .header("Accept", "application/vnd.github.v3+json")
                       .header("Authorization", "token " + apiToken)
                       .url(request.url());

                   return chain.proceed(newRequestBuilder.build());
               })*/
            .build();
    }

    // 替换 /api/{name}/{code}/query 中的 {name} 和 {code}
    private String replacePathVariables(String pathTemplate, Object... uriVariables) {
        int varIndex = 0;
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < pathTemplate.length()) {
            int start = pathTemplate.indexOf('{', i);
            if (start == -1) {
                result.append(pathTemplate.substring(i));
                break;
            }
            int end = pathTemplate.indexOf('}', start);
            if (end == -1) {
                throw new IllegalArgumentException("Invalid path template: unmatched {");
            }
            result.append(pathTemplate, i, start);
            if (varIndex >= uriVariables.length) {
                throw new IllegalArgumentException("Not enough URI variables provided for the template.");
            }
            result.append(uriVariables[varIndex++].toString());
            i = end + 1;
        }
        if (varIndex < uriVariables.length) {
            throw new IllegalArgumentException("Too many URI variables provided.");
        }
        return result.toString();
    }


    public <T> List<T> getList(String path, Class<T[]> responseEntityCls, Object... uriVariables) {
        String url = buildUrl(path);

        Request.Builder builder = new Request.Builder();
        fillHeaders(builder);
        okhttp3.Request request = builder.url(replacePathVariables(url, uriVariables))
            .get()
            .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String json = response.body() != null ? response.body().string() : null;
            log.info("response.body()={}", json);
            if (json == null || json.isEmpty()) {
                return List.of();
            }
            return JsonUtils.fromJson(json, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new CommonException(e);
        }
    }

    private void fillHeaders(Request.Builder builder) {
        builder.header("Host", "api.github.com")
            .header("Accept", "application/vnd.github.v3+json")
            .header("Authorization", "token " + apiToken);
    }

    public <T> Pair<Integer, T> put(String path, Object requestBody, Class<T> responseEntityCls, Object... uriVariables) {
        String url = buildUrl(path);

        Request.Builder builder = new Request.Builder();
        fillHeaders(builder);
        okhttp3.Request request = builder.url(replacePathVariables(url, uriVariables))
            .put(RequestBody.create(JsonUtils.toJson(requestBody), MediaType.parse("application/json")))
            .build();
        try (Response response = httpClient.newCall(request).execute()) {
            int code = response.code();
            String json = response.body() != null ? response.body().string() : null;
            log.info("response.body()={}", json);
            if (json == null || json.isEmpty()) {
                return Pair.of(code, null);
            }
            boolean respEntityIsString = String.class.equals(responseEntityCls);
            T r;
            if (respEntityIsString) {
                r = (T) json;
            } else {
                r = JsonUtils.fromJson(json, responseEntityCls);
            }
            Map<String, Object> stringObjectMap = JsonUtils.fromJson(json);
            if ("422".equals(stringObjectMap.get("status"))) {
//            {"message":"Invalid request.\n\n\"sha\" wasn't supplied.",
//            "documentation_url":"https://docs.github.com/rest/repos/contents#create-or-update-file-contents",
//            "status":"422"}
                return Pair.of(200, r);
            }
            return Pair.of(code, r);
        } catch (IOException e) {
            throw new CommonException(e);
        }
    }

}
