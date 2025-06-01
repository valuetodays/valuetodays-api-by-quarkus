package cn.valuetodays.module.codegenerator.pojo;

import cn.valuetodays.module.codegenerator.advanced.AfterPropertiesSet;
import cn.valuetodays.module.codegenerator.advanced.Validation;
import cn.valuetodays.module.codegenerator.db.DBMySql;
import cn.vt.exception.AssertUtils;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Field implements AfterPropertiesSet, Validation {
    public static final List<String> BASIC_COLUMNS = List.of("createUserId", "updateUserId", "createTime", "updateTime");
    @Setter
    private TableClass tableClass;
    private String columnName;
    private String columnType;
    private Object columnOption1;
    private Object columnOption2;
    private Object columnOption3;
    private String fieldName;  // java字段名称
    private DataType fieldType;
    private String fieldJavaType; // 如Integer,String
    private String fieldGoType; // 如int,string
    private String comment; // 注释
    private String defaultValue; // 默认值
    private int length1; // 数据长度1
    private int length2; // 数据长度2（小数点）
    private boolean nullable; // 是否为空
    private boolean primaryKey; // 是否为主键
    private boolean dateType; // 是否为时间类型
    private boolean enumType; // 是否为枚举类型
    private boolean basicColumn; // 是否为基本字段：create_user_id，update_user_id, create_time, update_time

    @Override
    public void afterPropertiesSet() {
        dateType = DataType.DATE == fieldType;
        enumType = DataType.ENUM == fieldType;

        this.setFieldName(DBMySql.columnOrTableName2JavaCamelStyleString(columnName));
        DBMySql.TypeForMultiLanguage typeForMultiLanguage = DBMySql.getMultiLanguageTypesByMysqlType(columnType, null);
        this.setFieldJavaType(typeForMultiLanguage.getJava());
        this.setFieldGoType(typeForMultiLanguage.getGo());
        this.setFieldType(DataType.valueOf(fieldJavaType.toUpperCase()));
        basicColumn = BASIC_COLUMNS.contains(fieldName);
        if (DataType.ENUM != this.fieldType && columnType.contains("(")) {
            String lengthWithParentheses = columnType.substring(columnType.indexOf("("));
            String lengthWithoutParentheses = lengthWithParentheses.substring(1, lengthWithParentheses.indexOf(")"));
            String[] lengthArr = lengthWithoutParentheses.split(",");
            if (lengthArr.length > 0) {
                length1 = Integer.parseInt(lengthArr[0]);
            }
            if (lengthArr.length > 1) {
                length2 = Integer.parseInt(lengthArr[1]);
            }
        }

        if (columnType.startsWith("enum")) {
            this.setColumnOption1(columnType.substring("enum".length()));
            String tmp = this.getColumnOption1().toString()
                .substring(1, this.getColumnOption1().toString().length() - 1);
            String[] tmpArr = tmp.split(",");
            List<String> optionList = new ArrayList<>();
            for (String tmpStr : tmpArr) {
                optionList.add(tmpStr.substring(1, tmpStr.length() - 1));
            }
            this.setColumnOption2(optionList);
            String rawComment = this.getComment();
            String formattedComment = rawComment
                .replaceAll("（", "(")
                .replaceAll("，", ",")
                .replaceAll("、", ",")
                .replaceAll("）", ")");
            String[] formattedCommentArr = StringUtils.tokenizeToStringArray(formattedComment, "(");
            AssertUtils.assertTrue(
                ArrayUtils.isNotEmpty(formattedCommentArr),
                "表" + tableClass.getTableName() + "的字段" + columnName + "为枚举类型，但注释不合法"
            );
            String enumCommentStr = formattedCommentArr[0];
            String enumTextStr = formattedCommentArr[1].substring(0, formattedCommentArr[1].length() - 1);
            this.setColumnOption3(Arrays.asList(StringUtils.tokenizeToStringArray(enumTextStr, ",")));
            this.setComment(enumCommentStr);
        }

        valid();
    }

    @Override
    public void valid() {

    }

    public enum DataType {
        INTEGER,
        LONG,
        DOUBLE,
        BOOLEAN,
        DATE,
        STRING,
        ENUM,
        OBJECT,
        LOCALDATETIME,
        LOCALDATE,
        LOCALTIME,
        BIGDECIMAL
    }
}
