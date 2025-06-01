package cn.valuetodays.module.codegenerator.entity;

import lombok.Data;

import java.io.File;

/**
 * 生成 - 响应.
 *
 * @author lei.liu
 * @since 2022-06-17
 */
@Data
public class GenerateResp {
    private boolean ok;
    private String msg;
    private File file;
}
