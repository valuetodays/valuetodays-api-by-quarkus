package cn.valuetodays.module.codegenerator.pojo;

import cn.valuetodays.module.codegenerator.advanced.AfterPropertiesSet;
import cn.valuetodays.module.codegenerator.advanced.Validation;
import cn.valuetodays.module.codegenerator.advanced.config.configfile.CgConfig;
import cn.valuetodays.module.codegenerator.util.StringExUtil;
import cn.vt.exception.AssertUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//-- select table_name as name, comments as comment from user_tab_comments
//-- select * from user_col_comments where table_name='USER_INFO'

@Getter
@Setter
public class TableClass implements AfterPropertiesSet, Validation {
    private CgConfig cgConfig;
    private String tableName; // 数据库中的表名称
    private String tableNameWithoutPrefix; // 假设表名是t_user_info，前缀是t_，则此值是user_info
    private String tableNameWithoutPrefixKebab; // 将tableNameWithoutPrefix中的_换成-
    private String comment;
    private String className; // 对应java的类名
    private String camelClassName; // 将className的首字母小写
    private String pathName1;
    private String pathName2;
    private boolean hasDateType;
    private boolean hasEnumType;
    /**
     * 是否有基本字段：create_user_id，update_user_id, create_time, update_time
     */
    private boolean hasBasicColumns;
    private List<Field> fields;
    private String primaryKeyFieldJavaType;
    private boolean unionPrimaryKey; // 是否是联合主键

    @Override
    public void afterPropertiesSet() {
        fields.forEach(e -> {
            e.setTableClass(this);
            e.afterPropertiesSet();
        });

        hasDateType = fields.stream().anyMatch(Field::isDateType);
        hasEnumType = fields.stream().anyMatch(Field::isEnumType);
        hasBasicColumns = fields.stream().filter(Field::isBasicColumn).count() == Field.BASIC_COLUMNS.size();
        List<Field> pkList = fields.stream().filter(Field::isPrimaryKey).toList();
        if (CollectionUtils.isNotEmpty(pkList)) {
            if (1 == pkList.size()) {
                unionPrimaryKey = false;
                primaryKeyFieldJavaType = pkList.get(0).getFieldJavaType();
            } else {
                unionPrimaryKey = true;
                primaryKeyFieldJavaType = className + "Pk";
            }
        }
        this.setCamelClassName(StringExUtil.uncapitaliseFirst(className));
        this.setPathName1(classNameToPathName());
        this.setPathName2(camelClassName);
        String prefix = cgConfig.getTableNameFilter().getPrefix();
        if (tableName.startsWith(prefix)) {
            tableNameWithoutPrefix = this.tableName.substring(prefix.length());
            tableNameWithoutPrefixKebab = this.tableNameWithoutPrefix.replaceAll("_", "-");
        } else {
            throw new RuntimeException("error prefix in table name. tableName=" + tableName + ", prefix=" + prefix);
        }
        valid();
    }

    private String classNameToPathName() {
        String tmpClassName = this.className;
        StringBuilder sb = new StringBuilder();
        for (char ch : tmpClassName.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                sb.append("-");
            }
            sb.append(Character.toLowerCase(ch));
        }
        if (sb.charAt(0) == '-') {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    @Override
    public void valid() {
        Set<String> nameSet = fields.stream().map(Field::getColumnName).collect(Collectors.toSet());
        AssertUtils.assertTrue(nameSet.size() == fields.size(), "table " + tableName + " has same column name!");
        List<Field> pkList = fields.stream().filter(Field::isPrimaryKey).toList();
        AssertUtils.assertTrue(CollectionUtils.isNotEmpty(pkList), "table " + tableName + " must have primary key!");

    }
}
