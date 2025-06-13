package cn.valuetodays.api2.basic.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import cn.valuetodays.api2.basic.VocechatProperties;
import cn.valuetodays.api2.basic.service.restclient.VocechatClient;
import cn.valuetodays.api2.basic.vo.PushBaseReq;
import cn.valuetodays.api2.basic.vo.PushVocechatFileReq;
import cn.valuetodays.api2.basic.vo.PushVocechatTextReq;
import cn.valuetodays.api2.basic.vo.VocechatWebhookReq;
import cn.vt.exception.CommonException;
import cn.vt.util.JsonUtils;
import jakarta.activation.MimetypesFileTypeMap;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-06-20
 */
@ApplicationScoped
@Slf4j
public class VocechatServiceImpl {
    @Inject
    VocechatProperties vocechatProperties;
    @Inject
    KeywordsModuleImpl keywordsModule;
    @Inject
    @RestClient
    VocechatClient vocechatClient;

    private final OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build();

    public static List<ContentBody> splitFile(File srcFile, int size) throws IOException {
        List<ContentBody> segmentList = new ArrayList<>();
        try (
            FileInputStream inputStream = FileUtils.openInputStream(srcFile)
        ) {
            byte[] buffer = new byte[size];
            int len = 0;

            // IOUtils.read(inputStream, buffer) 读取完后，再读取则返回值为0
            while ((len = IOUtils.read(inputStream, buffer)) > 0) {
                byte[] temp = new byte[len];
                System.arraycopy(buffer, 0, temp, 0, len);
                segmentList.add(new ByteArrayBody(temp, ""));
            }
            return segmentList;
        }
    }

    public Boolean pushVocechatText(PushVocechatTextReq req) {
        String url = buildUrl(req);
        if (StringUtils.isBlank(url)) {
            return false;
        }
        VocechatProperties.Bot bot = findApiKeyByUid(req.getFromUserId());
        String apiKey = bot.apiKey();
        String contentType = req.isPlainText() ? "text/plain" : "text/markdown";
        try {
            String respStr = doPostStringAsync(vocechatProperties.basePath() + url,
                req.getContent() + "\n---\nby " + bot.title(),
                contentType,
                apiKey);
            log.info("respStr: {}", respStr);
//            Uni<String> sUni = vocechatClient.sendToUserOrGroup(url, apiKey, contentType, req.getContent());
//            sUni.onItem().transform(response -> {
//                    log.info("respStr: {}", response);
//                    return "处理后的结果: " + response;
//                })
//                .onFailure().recoverWithItem(throwable -> {
//                    // 可以统一处理异常
//                    return "发送失败: " + throwable.getMessage();
//                });
        } catch (Exception e) {
            log.error("error when pushVocechatText()", e);
            throw new CommonException(e);
        }

        return true;
    }

    private String doPostStringAsync(String url, String content, String contentType, String apiKey) {
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        RequestBody body = RequestBody.create(contentBytes, null);
        Request request = new Request.Builder()
            .url(url)
            .post(body)
            .header("x-api-key", apiKey)
            .header("Content-Type", contentType)  // 显式声明Content-Type
            .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 请求失败的处理逻辑
                log.error("error when doPostStringAsync()", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody respBody = response.body();
                if (Objects.nonNull(respBody)) {
                    String responseBody = respBody.string();
                    log.info("respStr={}", responseBody);
                }
            }
        });
//        try (Response response = call.execute()) {
//            ResponseBody respBody = response.body();
//            if (Objects.nonNull(respBody)) {
//                return respBody.string();
//            }
//        } catch (IOException e) {
//            log.error("error when doPostString()", e);
//            throw new CommonException(e);
//        }
        return null;
    }

    private VocechatProperties.Bot findApiKeyByUid(Integer fromUserId) {
        List<VocechatProperties.Bot> botList = vocechatProperties.botList();
        return botList.stream()
            .filter(e -> Objects.equals(fromUserId, e.uid()))
            .findFirst().orElse(botList.getFirst());
    }

    public boolean pushVocechatFile(PushVocechatFileReq req) {
        VocechatProperties.Bot bot = findApiKeyByUid(req.getFromUserId());
        final Map<String, String> headerMap = Map.of(
            "x-api-key", bot.apiKey()
        );

        String basePath = vocechatProperties.basePath();
        String urlForPrepare = basePath + "/api/bot/file/prepare";
        final String fileIdStr = prepareFile(urlForPrepare, bot.apiKey(), req);

        Map<String, String> headerForUpload = Map.of(
            "accept", "application/json; charset=utf-8",
            "content_type", "multipart/form-data"
        );
        Map<String, String> headerToUseForUpload = new HashMap<>();
        headerToUseForUpload.putAll(headerMap);
        headerToUseForUpload.putAll(headerForUpload);

        String urlForUpload = basePath + "/api/bot/file/upload";
        try (
            FileInputStream inputStream = FileUtils.openInputStream(req.getFile())
        ) {
            int _1M = (int) (1.0 * 1024 * 1024);
            byte[] buffer = new byte[_1M];
            int len = 0;
            while ((len = IOUtils.read(inputStream, buffer)) > 0) {
                byte[] chunkData = new byte[len];
                System.arraycopy(buffer, 0, chunkData, 0, len);
                int available = inputStream.available();
                boolean isLast = (available == 0);
                String s = doPostFileByOkhttp(chunkData, fileIdStr, isLast, headerToUseForUpload, urlForUpload);
                log.info("respStrForUpload={}", s);
                if (isLast) {
                    UploadResp uploadResp = JsonUtils.fromJson(s, UploadResp.class);
                    String path = uploadResp.getPath();
                    pushVocechatFileMsg(path, req, bot.apiKey());
                }

            }
        } catch (Exception e) {
            log.error("error when pushVocechatFile()", e);
        }
        return true;
    }

    private String doPostFileByOkhttp(byte[] chunkData,
                                      String fileIdStr,
                                      boolean isLast,
                                      Map<String, String> headerForUpload,
                                      String urlForUpload) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), chunkData);
        MultipartBody requestBody = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("chunk_data", "", fileBody)
            .addFormDataPart("file_id", fileIdStr)
            .addFormDataPart("chunk_is_last", isLast ? "true" : "false")
            .build();
        Request.Builder builder = new Request.Builder();
        for (Map.Entry<String, String> kv : headerForUpload.entrySet()) {
            String key = kv.getKey();
            String value = kv.getValue();
            builder.header(key, value);
        }
        Request request = builder
            .url(urlForUpload)
            .post(requestBody)
            .build();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (Objects.nonNull(body)) {
                return body.string();
            }
        } catch (IOException e) {
            throw new CommonException(e);
        }
        return null;
    }

    public void processWebhook(VocechatWebhookReq req) {
        if (Objects.isNull(req)) {
            return;
        }
        log.info("webhookreq: {}", req);
        PushVocechatTextReq pushVocechatTextReq = new PushVocechatTextReq();
        InnerResult innerResult = processChatIsFromUserOrGroupId(req, pushVocechatTextReq);
        if (!innerResult.toMe()) {
            return;
        }
        pushVocechatTextReq.setFromUserId(innerResult.meId());
        String rawContent = innerResult.detail().getContent();
        // 把消息中的@我改为@发送者
        String replacedContent = StringUtils.replace(rawContent,
            " @" + innerResult.meId() + " ",
            " @" + req.getFrom_uid() + " ");
//        Pair<PushBaseReq.ContentType, String> tuple2 = Pair.of(PushBaseReq.ContentType.PLAIN_TEXT, replacedContent);
        Pair<PushBaseReq.ContentType, String> tuple2 = keywordsModule.replyKeywords(replacedContent);
        PushBaseReq.ContentType t1 = tuple2.getLeft();
        String t2 = tuple2.getRight();
        if (t1 == PushBaseReq.ContentType.PLAIN_TEXT) {
            pushVocechatTextReq.setContent(t2);
            pushVocechatTextReq.setPlainText(true);
            this.pushVocechatText(pushVocechatTextReq);
        } else if (t1 == PushBaseReq.ContentType.FILE) {
            PushVocechatFileReq reqForPushFile = new PushVocechatFileReq();
            reqForPushFile.setToUserId(pushVocechatTextReq.getToUserId());
            reqForPushFile.setToGroupId(pushVocechatTextReq.getToGroupId());
            File file = new File(t2);
            reqForPushFile.setFileName(file.getName());
            reqForPushFile.setFile(file);
            this.pushVocechatFile(reqForPushFile);
        }
    }

    private InnerResult processChatIsFromUserOrGroupId(VocechatWebhookReq req, PushVocechatTextReq pushVocechatTextReq) {
        boolean toMe = false;
        List<VocechatProperties.Bot> botList = vocechatProperties.botList();
        Integer meId = null;
        List<Integer> meIds = botList.stream().map(VocechatProperties.Bot::uid).toList();
        VocechatWebhookReq.DetailVo detail = req.getDetail();
        // uid有值，说明是两人私聊
        // gid有值，说明是群聊，mentions即是@的人的列表
        VocechatWebhookReq.TargetVo target = req.getTarget();
        Integer uid = target.getUid();
        if (meIds.contains(uid)) {
            toMe = true;
            meId = uid;
            pushVocechatTextReq.useToUserId(req.getFrom_uid());
        } else {
            Map<String, Object> properties = detail.getProperties();
            if (MapUtils.isNotEmpty(properties)) {
                Object mentionsObj = properties.get("mentions");
                if (Objects.nonNull(mentionsObj)) {
                    try {
                        @SuppressWarnings("unchecked")
                        List<Integer> mentionIds = (List<Integer>) mentionsObj;
                        Integer first = meIds.stream().filter(mentionIds::contains).findFirst().orElse(null);
                        toMe = Objects.nonNull(first);
                        meId = first;
                        pushVocechatTextReq.useToGroupId(target.getGid());
                    } catch (Exception ignored) {

                    }
                }
            }
        }
        return new InnerResult(toMe, meId, detail);
    }

    private void pushVocechatFileMsg(String path, PushVocechatFileReq req, String apiKey) {
        String urlForSendToGroup = buildUrl(req);
        String contentType = "vocechat/file";
        Map<String, String> bodyForSendToUser = Map.of("path", path);
        try {
            String s = doPostStringAsync(urlForSendToGroup, JsonUtils.toJson(bodyForSendToUser), contentType, apiKey);
            log.info("respStr: {}", s);
        } catch (Exception e) {
            log.error("error when pushVocechatText()", e);
        }

    }

    private String prepareFile(String urlForPrepare, String apiKey, PushVocechatFileReq req) {
        File f = req.getFile();
        // 根据扩展名去解析
        String contentType = new MimetypesFileTypeMap().getContentType(f);
        Map<String, Object> dataForPrepare = Map.of("filename", req.getFileName(), "content_type", contentType);
        String respStrForPrepare = doPostJsonByOkhttp(urlForPrepare, apiKey, dataForPrepare);
        log.info("respStrForPrepare={}", respStrForPrepare);
        return JsonUtils.fromJson(respStrForPrepare, String.class);
    }

    private String doPostJsonByOkhttp(String url, String apiKey, Map<String, Object> data) {
        byte[] contentBytes = JsonUtils.toJson(data).getBytes(StandardCharsets.UTF_8);
        RequestBody body = RequestBody.create(contentBytes, null);
        Request request = new Request.Builder()
            .url(url)
            .post(body)
            .header("x-api-key", apiKey)
            .header("Content-Type", "application/json")  // 显式声明Content-Type
            .build();

        try (Response response = client.newCall(request).execute()) {
            ResponseBody respBody = response.body();
            if (Objects.nonNull(respBody)) {
                return respBody.string();
            }
        } catch (IOException e) {
            log.error("error when doPostJsonByOkhttp()", e);
            throw new CommonException(e);
        }
        return null;
    }

    private record InnerResult(boolean toMe, Integer meId, VocechatWebhookReq.DetailVo detail) {
    }

    @Data
    private static class UploadResp implements Serializable {
        private String hash;
        private String path;
        private long size;
    }

    private String buildUrl(PushBaseReq req) {
        String url = null;
        Integer toGroupId = req.getToGroupId();
        if (Objects.nonNull(toGroupId)) {
            url = vocechatProperties.urlSendToGroup() + toGroupId;
        }
        Integer toUserId = req.getToUserId();
        if (Objects.nonNull(toUserId)) {
            url = vocechatProperties.urlSendToUser() + toUserId;
        }

        return url;
    }
}
