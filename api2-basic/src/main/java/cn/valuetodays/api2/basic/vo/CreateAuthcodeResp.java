package cn.valuetodays.api2.basic.vo;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-04-23
 */
@Data
@Schema(name = "生成验证码-响应对象")
public class CreateAuthcodeResp implements Serializable {
    @Schema(name = "图片字节数组")
    private byte[] image;
    @Schema(name = "图片唯一id")
    private String imgToken;

    public static CreateAuthcodeResp of(byte[] imageArr, String uuid) {
        CreateAuthcodeResp resp = new CreateAuthcodeResp();
        resp.setImage(imageArr);
        resp.setImgToken(uuid);
        return resp;
    }

}
