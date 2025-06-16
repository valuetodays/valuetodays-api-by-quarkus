package cn.valuetodays.module.spider.service.vocechat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkhttpFileDownloader {

    // 单例 OkHttpClient，支持高并发
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build();

    /**
     * 下载 URL 到本地路径，文件名自动从 URL 提取
     *
     * @param fileUrl 文件的网络地址
     * @param saveDir 本地保存路径（目录）
     * @return 下载后的本地文件路径
     * @throws IOException 下载失败时抛出
     */
    public static String downloadFile(String fileUrl, String saveDir) throws IOException {
        String fileName = extractFileName(fileUrl);
        return downloadFile(fileUrl, saveDir, fileName);
    }

    /**
     * 下载 URL 到本地路径，文件名可自定义
     *
     * @param fileUrl  文件的网络地址
     * @param saveDir  本地保存路径（目录）
     * @param fileName 保存的文件名
     * @return 下载后的本地文件路径
     * @throws IOException 下载失败时抛出
     */
    public static String downloadFile(String fileUrl, String saveDir, String fileName) throws IOException {
        Request request = new Request.Builder()
            .url(fileUrl)
            .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("下载失败，HTTP状态码：" + response.code());
            }

            ResponseBody body = response.body();
            if (body == null) {
                throw new IOException("下载失败，响应体为空");
            }

            File dir = new File(saveDir);
            if (!dir.exists() && !dir.mkdirs()) {
                throw new IOException("无法创建目录：" + saveDir);
            }

            File file = new File(dir, fileName);

            try (InputStream is = body.byteStream();
                 FileOutputStream fos = new FileOutputStream(file)) {

                byte[] buffer = new byte[4096];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            }

            return file.getAbsolutePath();
        }
    }

    /**
     * 从 URL 提取文件名
     *
     * @param fileUrl 文件 URL
     * @return 文件名
     */
    private static String extractFileName(String fileUrl) throws IOException {
        String path = new URL(fileUrl).getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }

    // 示例使用
    public static void main(String[] args) {
        try {
            String url = "https://example.com/sample.jpg";
            String localPath = downloadFile(url, "downloads");
            System.out.println("下载完成，文件保存在: " + localPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
