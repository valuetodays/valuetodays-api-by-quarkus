package cn.valuetodays.api2.basic.service.vocechat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import cn.valuetodays.api2.web.basic.push.vocechat.AutoReplyContent;
import cn.valuetodays.api2.web.basic.push.vocechat.PushBaseReq;
import cn.vt.exception.CommonException;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-06-13
 */
@ApplicationScoped
public class SampleImageAutoReplyContent implements AutoReplyContent {
    @Override
    public List<String> title() {
        return List.of("image", "图片");
    }

    @Override
    public Pair<PushBaseReq.ContentType, String> replyContent(String value) {
        String filePath = "";
        String fileInClasspath = "vocechat/sample_image.png";
        try (InputStream inputStream = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(fileInClasspath)) {
            if (Objects.nonNull(inputStream)) {
                File tempFile = File.createTempFile("vocechat", ".png");
                byte[] imageBytes = inputStream.readAllBytes();
                FileUtils.writeByteArrayToFile(tempFile, imageBytes);
                filePath = tempFile.getAbsolutePath();
            }
        } catch (IOException e) {
            throw new CommonException(e);
        }
        if (StringUtils.isBlank(filePath)) {
            return Pair.of(
                PushBaseReq.ContentType.PLAIN_TEXT,
                "no file in classpath: " + fileInClasspath
            );
        }
        return Pair.of(
            PushBaseReq.ContentType.FILE,
            filePath
        );
    }
}
