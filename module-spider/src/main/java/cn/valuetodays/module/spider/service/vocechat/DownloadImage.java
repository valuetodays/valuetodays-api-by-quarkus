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
import lombok.Data;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;


public class DownloadImage {

    public static List<File> executeJson() {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
            .url("https://api.vvhan.com/api/wallpaper/mobileGirl?type=json")
            .get()
            .build();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody respBody = response.body();
            if (Objects.isNull(respBody)) {
                return null;
            }
            MediaType respMediaType = respBody.contentType();
            System.out.println(respMediaType);
            if (isText(respMediaType)) {
                String respString = respBody.string();
                System.out.println(respString);
                LoveanimerResponse loveanimerResponse = JsonUtils.fromJson(respString, LoveanimerResponse.class);
                System.out.println(loveanimerResponse);
                if (!loveanimerResponse.isSuccess()) {
                    return null;
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
        return null;
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
            System.out.println(respMediaType);
            if (isText(respMediaType)) {
                String respString = respBody.string();
                System.out.println(respString);
            } else {
                File tempFile = File.createTempFile("tmpf", ".jpg");
                System.out.println(tempFile);
                System.out.println(tempFile.getParent());
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


}
