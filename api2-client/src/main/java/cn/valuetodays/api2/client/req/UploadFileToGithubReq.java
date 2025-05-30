package cn.valuetodays.api2.client.req;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-12-13
 */
@Data
public class UploadFileToGithubReq implements Serializable {
    private Type type;
    // 文件
    private File file;

    /**
     * __ is / in real path
     */
    public enum Type {
        blog,
        doc2,
        wp,
        wp__mmpic;

        public String toDirectoryPath() {
            return StringUtils.replace(this.name(), "__", "/");
        }
    }
}
