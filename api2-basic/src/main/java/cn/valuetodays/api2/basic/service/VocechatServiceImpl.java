package cn.valuetodays.api2.basic.service;

import cn.valuetodays.api2.basic.VocechatProperties;
import cn.valuetodays.api2.basic.vo.PushBaseReq;
import cn.valuetodays.api2.basic.vo.PushVocechatFileReq;
import cn.valuetodays.api2.basic.vo.PushVocechatTextReq;
import cn.valuetodays.api2.basic.vo.VocechatWebhookReq;
import cn.vt.util.HttpClient4Utils;
import cn.vt.util.JsonUtils;
import jakarta.activation.MimetypesFileTypeMap;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        String url = vocechatProperties.buildUrl(req);
        if (StringUtils.isBlank(url)) {
            return false;
        }
        String contentType = req.isPlainText() ? "text/plain" : "text/markdown";
        Map<String, String> headerMap = Map.of(
            "x-api-key", vocechatProperties.getApiKey()
        );
        try {
            String s = HttpClient4Utils.doPostPlainString(url, req.getContent(), contentType, headerMap, null);
            log.debug("respStr: {}", s);
        } catch (Exception e) {
            log.error("error when pushVocechatText()", e);
        }

        return true;
    }

    public void pushVocechatFile(PushVocechatFileReq req) {
        final Map<String, String> headerMap = Map.of(
            "x-api-key", vocechatProperties.getApiKey()
        );

        String basePath = vocechatProperties.getBasePath();
        String urlForPrepare = basePath + "/api/bot/file/prepare";
        String fileIdStr = prepareFile(urlForPrepare, headerMap, req);

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
            byte[] buffer = new byte[200_000_000];
            int len = 0;
            // IOUtils.read(inputStream, buffer) 读取完后，再读取则返回值为0
            while ((len = IOUtils.read(inputStream, buffer)) > 0) {
                byte[] temp = new byte[len];
                System.arraycopy(buffer, 0, temp, 0, len);
                ByteArrayBody byteArrayBody = new ByteArrayBody(temp, "");
                int available = inputStream.available();
                boolean isLast = (available == 0);
                Map<String, String> formMapForUpload = Map.of(
                    "file_id", fileIdStr,
                    "chunk_is_last", isLast ? "true" : "false"
                );
                Map<String, ContentBody> paramsByFile = new HashMap<>();
                paramsByFile.put("chunk_data", byteArrayBody);
                if (isLast) {
                    String s = HttpClient4Utils.doPostFile(urlForUpload, null, formMapForUpload, paramsByFile, headerToUseForUpload, null);
//                log.info("respStrForUpload={}", s);
                    UploadResp uploadResp = JsonUtils.fromJson(s, UploadResp.class);
                    String path = uploadResp.getPath();
                    pushVocechatFileMsg(path, headerMap, req);
                }
            }
        } catch (Exception e) {
            log.error("error when pushVocechatFile()", e);
        }
    }

    public void processWebhook(VocechatWebhookReq req) {
        if (Objects.isNull(req)) {
            return;
        }
        log.info("webhookreq: {}", req);
        PushVocechatTextReq pushVocechatTextReq = new PushVocechatTextReq();
        boolean toMe = false;
        Integer meId = 2;
        VocechatWebhookReq.DetailVo detail = req.getDetail();
        // uid有值，说明是两人私聊
        // gid有值，说明是群聊，mentions即是@的人的列表
        VocechatWebhookReq.TargetVo target = req.getTarget();
        Integer uid = target.getUid();
        if (Objects.equals(meId, uid)) {
            toMe = true;
            pushVocechatTextReq.useToUserId(req.getFrom_uid());
        } else {
            Map<String, Object> properties = detail.getProperties();
            if (MapUtils.isNotEmpty(properties)) {
                Object mentionsObj = properties.get("mentions");
                if (Objects.nonNull(mentionsObj)) {
                    try {
                        @SuppressWarnings("unchecked")
                        List<Integer> mentionIds = (List<Integer>) mentionsObj;
                        toMe = mentionIds.contains(meId);
                        pushVocechatTextReq.useToGroupId(target.getGid());
                    } catch (Exception ignored) {

                    }
                }
            }
        }
        if (!toMe) {
            return;
        }
        String rawContent = detail.getContent();
        // 把消息中的@我改为@发送者
        String replacedContent = StringUtils.replace(rawContent,
            " @" + meId + " ",
            " @" + req.getFrom_uid() + " ");
        Pair<PushBaseReq.ContentType, String> tuple2 = Pair.of(PushBaseReq.ContentType.PLAIN_TEXT, replacedContent);
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

    private void pushVocechatFileMsg(String path, Map<String, String> headerMap, PushVocechatFileReq req) {
        String urlForSendToGroup = vocechatProperties.buildUrl(req);
        String contentType = "vocechat/file";
        Map<String, String> bodyForSendToUser = Map.of("path", path);
        try {
            String s = HttpClient4Utils.doPostPlainString(
                urlForSendToGroup, JsonUtils.toJson(bodyForSendToUser), contentType, headerMap, null
            );
            log.info("respStr: {}", s);
        } catch (Exception e) {
            log.error("error when pushVocechatText()", e);
        }

    }

    private String prepareFile(String urlForPrepare, Map<String, String> headerMap, PushVocechatFileReq req) {
        File f = req.getFile();
        // 根据扩展名去解析
        String contentType = new MimetypesFileTypeMap().getContentType(f);
        Map<String, Object> dataForPrepare = Map.of("filename", req.getFileName(), "content_type", contentType);
        String respStrForPrepare = HttpClient4Utils.doPostJson(new HttpPost(urlForPrepare), dataForPrepare, null, headerMap);
//        log.info("respStrForPrepare={}", respStrForPrepare);
        return JsonUtils.fromJson(respStrForPrepare, String.class);
    }

    @Data
    private static class UploadResp implements Serializable {
        private String hash;
        private String path;
        private long size;
    }

}
