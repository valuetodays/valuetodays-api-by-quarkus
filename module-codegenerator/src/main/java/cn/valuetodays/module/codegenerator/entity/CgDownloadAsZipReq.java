package cn.valuetodays.module.codegenerator.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-05-25
 */
@Data
public class CgDownloadAsZipReq implements Serializable {
    @NotBlank
    private String projectName;
    @NotBlank
    private String databaseName;
    private boolean debug;

    private String tableNamePrefix;

    private String tableNameSuffix;

    private String tableNameIncludes;

    private String tableNameExcludes;
    @NotBlank
    private String tableName2ClassNameConverter;

    private String author = "valuetodays";
    @NotBlank
    private String basePackage;

    @NotEmpty(message = "模板组不能为空")
    private List<Long> templateGroupIds;

    private List<String> tableNameIncludeList;
    private List<String> tableNameExcludeList;

    public List<String> getTableNameIncludeList() {
        if (StringUtils.isBlank(tableNameIncludes)) {
            return new ArrayList<>(0);
        }
        return Arrays.stream(
                tableNameIncludes.trim().replace("，", ",").split(",")
            )
            .map(String::trim)
            .toList();
    }

    public List<String> getTableNameExcludeList() {
        return Arrays.stream(
                tableNameExcludes.trim().replace("，", ",").split(",")
            )
            .map(String::trim)
            .toList();
    }
}
