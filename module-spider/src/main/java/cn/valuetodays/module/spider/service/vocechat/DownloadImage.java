package cn.valuetodays.module.spider.service.vocechat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.vt.exception.CommonException;
import cn.vt.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;


public class DownloadImage {
    private static OkHttpClient client = new OkHttpClient().newBuilder().build();

    /**
     * see <a href="https://api.vvhan.com/article/mobileGirl.html">page</a>
     */
    public static List<File> getImageFileFromVvhan() {
        Request request = new Request.Builder()
            .url("https://api.vvhan.com/api/wallpaper/mobileGirl?type=json")
            .get()
            .build();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody respBody = response.body();
            if (Objects.isNull(respBody)) {
                return List.of();
            }
            MediaType respMediaType = respBody.contentType();
            if (isText(respMediaType)) {
                String respString = respBody.string();
                LoveanimerResponse loveanimerResponse = JsonUtils.fromJson(respString, LoveanimerResponse.class);
                if (!loveanimerResponse.isSuccess()) {
                    return List.of();
                }
                List<File> files = new ArrayList<>();
                String url = loveanimerResponse.getUrl();
                String file = OkhttpFileDownloader.downloadFile(url, SystemUtils.getJavaIoTmpDir().getAbsolutePath());
                files.add(new File(file));
                return files;
            }
        } catch (IOException e) {
            throw new CommonException(e);
        }
        return List.of();
    }

    /**
     * @see <a href="https://xxapi.cn/doc/heisi">page</a>
     */
    public static List<File> getHeisiImageFileFromXxapi() {
        return getImageFileFromXxapi("https://v2.xxapi.cn/api/heisi");
    }

    /**
     * @see <a href="https://xxapi.cn/doc/baisi">page</a>
     */
    public static List<File> getBaisiImageFileFromXxapi() {
        return getImageFileFromXxapi("https://v2.xxapi.cn/api/baisi");
    }

    public static List<File> getImageFileFromXxapi(String url) {
        Request request = new Request.Builder()
            .url(url)
            .get()
            .build();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody respBody = response.body();
            if (Objects.isNull(respBody)) {
                return List.of();
            }
            MediaType respMediaType = respBody.contentType();
            if (isText(respMediaType)) {
                String respString = respBody.string();
                XxapiStringDataResponse respObj = JsonUtils.fromJson(respString, XxapiStringDataResponse.class);
                if (!respObj.isSuccess()) {
                    return List.of();
                }
                List<File> files = new ArrayList<>();
                String fileUrl = respObj.getData();
                String file = OkhttpFileDownloader.downloadFile(fileUrl, SystemUtils.getJavaIoTmpDir().getAbsolutePath());
                files.add(new File(file));
                return files;
            }
        } catch (IOException e) {
            throw new CommonException(e);
        }
        return List.of();
    }

    private static boolean isText(MediaType mediaType) {
        if (Objects.isNull(mediaType)) {
            return false;
        }
        if (mediaType.subtype().contains("json")) {
            return true;
        }
        if (mediaType.type().contains("image")) {
            return false;
        }

        return false;
    }

    public void executeImage() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
            .url("https://api.suyanw.cn/api/meinv.php")
            .get()
            .build();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody respBody = response.body();
            if (Objects.isNull(respBody)) {
                return;
            }
            MediaType respMediaType = respBody.contentType();
            if (isText(respMediaType)) {
                String respString = respBody.string();
            } else {
                File tempFile = File.createTempFile("tmpf", ".jpg");
                try (InputStream is = respBody.byteStream();) {
                    FileUtils.copyInputStreamToFile(is, tempFile);
                }
            }
        }

    }


    @Data
    static class LoveanimerResponse implements Serializable {
        private boolean success;
        private String type;
        private String url;
    }

    @Data
    static class XxapiStringDataResponse implements Serializable {
        private int code;
        private String data;

        @JsonIgnore
        public boolean isSuccess() {
            return 200 == code;
        }
    }


}
