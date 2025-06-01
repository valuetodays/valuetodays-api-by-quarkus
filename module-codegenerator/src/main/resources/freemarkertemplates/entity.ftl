package ${properties.basePackage}.biz.api.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cn.valuetodays.common.jpa.support.entity.JpaLongIdEntity;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
<#if pojo.hasEnumType>
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
</#if>
<#if pojo.hasDateType>
import java.util.Date;
</#if>
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
* ${pojo.comment!''}
*
* @author ${properties.author}
* @since ${.now?string("yyyy-MM-dd HH:mm")}
*/
@Cacheable
@Table(name = "${pojo.tableName}")
@Cache(region = "orm.${pojo.className}", usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@JsonIgnoreProperties(ignoreUnknown = true /*, value = {"hibernateLazyInitializer", "handler"}*/)
@EqualsAndHashCode(callSuper = true)
@Data
public class ${pojo.className}PO extends JpaLongIdEntity {

<#list pojo.fields as field>
<#if field.fieldType.name() == 'ENUM'>
public enum ${field.fieldName?cap_first} {
<#if field.columnOption2??>
<#list field.columnOption2 as option2>
${option2}("${field.columnOption3[option2_index]}")<#if option2_has_next>,</#if>
</#list>
</#if>
;

private String value;

${field.fieldName?cap_first}(String value) {
this.value = value;
}

public String getValue() {
return this.value;
}
}
</#if>
</#list>

<#list pojo.fields as field>
<#if field.fieldName != 'id' && field.fieldName != 'createTime' && field.fieldName != 'updateTime' && field.fieldName != 'createUserId' && field.fieldName != 'updateUserId'>
<#if field.fieldType.name() == 'ENUM'>
@Column(name = "${field.columnName}")
@Enumerated(EnumType.STRING)
private ${field.fieldName?cap_first} ${field.fieldName};
<#else>
@Column(name = "${field.columnName}")
private ${field.fieldJavaType} ${field.fieldName};
</#if>
</#if>
</#list>
}
