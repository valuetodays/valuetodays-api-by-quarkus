package ${properties.basePackage}.biz.application.dao;

import ${properties.basePackage}.biz.api.entity.po.${pojo.className}PO;
import cn.valuetodays.common.jpa.support.dao.MyRepository;

/**
*
* @author ${properties.author}
* @since ${.now?string("yyyy-MM-dd HH:mm")}
*/
public interface ${pojo.className}DAO extends MyRepository<${pojo.primaryKeyFieldJavaType}, ${pojo.className}PO> {

}
