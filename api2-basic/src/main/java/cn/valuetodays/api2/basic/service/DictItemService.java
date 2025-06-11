package cn.valuetodays.api2.basic.service;

import cn.valuetodays.api2.basic.dao.DictItemDAO;
import cn.valuetodays.api2.basic.persist.DictItemPO;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 字典项
 *
 * @author lei.liu
 * @since 2024-11-13 20:40
 */
@ApplicationScoped
@Slf4j
public class DictItemService
    extends BaseCrudService<Long, DictItemPO, DictItemDAO> {

    public List<DictItemPO> findAllByTypeId(Long typeId) {
        return getRepository().findAllByTypeId(typeId);
    }
}
