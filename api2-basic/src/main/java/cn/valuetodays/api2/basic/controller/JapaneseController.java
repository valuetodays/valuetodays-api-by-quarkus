package cn.valuetodays.api2.basic.controller;

import cn.vt.R;
import cn.vt.exception.AssertUtils;
import cn.vt.util.JapneseKanaUtils;
import cn.vt.web.req.SimpleTypesReq;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * .
 *
 * @author lei.liu
 * @since 2024-10-01
 */
@Tag(name = "日文")
@Path("/common/japanese")
public class JapaneseController {
    @Operation(summary = "给汉字添加假名注音")
    @Path("/addKanaToKanji")
    @POST
    public R<String> addKanaToKanji(SimpleTypesReq req) {
        String text = req.getText();
        AssertUtils.assertStringNotBlank(text, "text不能为空");
        return R.success(JapneseKanaUtils.replaceText(text));
    }

}
