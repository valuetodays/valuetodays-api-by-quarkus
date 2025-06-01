package cn.valuetodays.module.codegenerator.controller;

import cn.valuetodays.module.codegenerator.DataSourceProperties;
import cn.valuetodays.module.codegenerator.advanced.config.configfile.Files;
import cn.valuetodays.module.codegenerator.advanced.config.configfile.MetaProperties;
import cn.valuetodays.module.codegenerator.advanced.config.configfile.Project;
import cn.valuetodays.module.codegenerator.advanced.config.configfile.TableNameFilter;
import cn.valuetodays.module.codegenerator.advanced.config.datasource.MysqlDatasource;
import cn.valuetodays.module.codegenerator.advanced.generate.BaseTemplate;
import cn.valuetodays.module.codegenerator.advanced.generate.FreemarkerStringBaseTemplate;
import cn.valuetodays.module.codegenerator.entity.CgDownloadAsZipReq;
import cn.valuetodays.module.codegenerator.entity.GenerateReq;
import cn.valuetodays.module.codegenerator.entity.GenerateResp;
import cn.valuetodays.module.codegenerator.po.CgTemplateGroupPersist;
import cn.valuetodays.module.codegenerator.po.CgTemplatePO;
import cn.valuetodays.module.codegenerator.service.CgTemplateGroupService;
import cn.valuetodays.module.codegenerator.service.CgTemplateService;
import cn.vt.exception.AssertUtils;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * .
 *
 * @author lei.liu
 * @since 2022-05-25
 */
@RestController
@Slf4j
@RequestMapping({"/cg/codegenerator"})
public class CodegeneratorController {

    @Inject
    private CgTemplateService cgTemplateService;
    @Inject
    private CgTemplateGroupService cgTemplateGroupService;
    @Inject
    private DataSourceProperties dataSourceProperties;

    @PostMapping(value = "/generateAsZip")
    public String generateAsZip(@RequestBody @Valid CgDownloadAsZipReq downloadZipReq) {
        String databaseName = downloadZipReq.getDatabaseName();
        boolean illegal = Stream.of("mysql", "information_schema", "performance_schema")
            .anyMatch(e -> e.equalsIgnoreCase(databaseName));
        AssertUtils.assertTrue(!illegal, "表名不合法");

        Project project = new Project();
        project.setName(downloadZipReq.getProjectName());
        project.setDebug(downloadZipReq.isDebug());
        project.setTargetBaseDir("/tmp");
        TableNameFilter tableNameFilter = new TableNameFilter();
        tableNameFilter.setPrefix(downloadZipReq.getTableNamePrefix());
        tableNameFilter.setSuffix(downloadZipReq.getTableNameSuffix());
        tableNameFilter.setIncludes(downloadZipReq.getTableNameIncludeList());
        tableNameFilter.setExcludes(downloadZipReq.getTableNameExcludeList());
        tableNameFilter.setTableName2ClassNameConverter(downloadZipReq.getTableName2ClassNameConverter());
        MysqlDatasource mysqlDataSource = new MysqlDatasource();
        mysqlDataSource.setUrl("jdbc:mysql://aliyun2:3306/"
            + databaseName
            + "?useUnicode=true&characterEncoding=utf8"
            + "&useSSL=false&zeroDateTimeBehavior=convertToNull"
            + "&transformedBitIsBoolean=true"
            + "&serverTimezone=Asia/Shanghai");
        mysqlDataSource.setUsername(dataSourceProperties.username());
        mysqlDataSource.setPassword(dataSourceProperties.password());
        MetaProperties properties = new MetaProperties();
        properties.setAuthor(downloadZipReq.getAuthor());
        properties.setBasePackage(downloadZipReq.getBasePackage());
        properties.setUseAtSince(true);
        properties.setModuleName("cg-sample");
        Files files = new Files();
        files.setUseAbsolutePath(false);
        files.setFilterDstPath(true);
        files.setTplResourceDir("/freemarkertemplates");

        List<BaseTemplate> templateList = getTemplateList(downloadZipReq.getTemplateGroupIds());
        files.setFileList(templateList);

        GenerateReq req = new GenerateReq();
        req.setProject(project);
        req.setTableNameFilter(tableNameFilter);
        req.setDatasource(mysqlDataSource);
        req.setProperties(properties);
        req.setFiles(files);
        try {
            GenerateResp resp = cn.valuetodays.module.codegenerator.MainApplication.doGenerate(req);
            if (resp.isOk()) {
                return resp.getFile().getName();
            }
            throw AssertUtils.create("下载失败，原因：" + resp.getMsg());
        } catch (Exception e) {
            log.error("error when doGenerate", e);
            throw AssertUtils.create("下载失败, 原因: " + e.getMessage());
        }
    }

    private List<BaseTemplate> getTemplateList(List<Long> templateGroupIds) {
        List<CgTemplateGroupPersist> groupList = cgTemplateGroupService.listByIds(templateGroupIds);
        List<Long> templateIds = groupList.stream()
            .flatMap(e -> Optional.ofNullable(e.getTemplateIds()).stream().flatMap(Collection::stream))
            .toList();
        List<CgTemplatePO> list = cgTemplateService.listByIds(templateIds);
        return list.stream().map(e -> {
            FreemarkerStringBaseTemplate freemarkerStringTemplate = new FreemarkerStringBaseTemplate();
            freemarkerStringTemplate.setTemplateString(e.getCode());
            freemarkerStringTemplate.setDstDir(e.getDstDir());
            freemarkerStringTemplate.setSuffix(e.getFileName());
            return freemarkerStringTemplate;
        }).collect(Collectors.toList());
    }

}
