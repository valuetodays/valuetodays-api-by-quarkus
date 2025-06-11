package cn.valuetodays.api2.basic.service;

import cn.valuetodays.api2.basic.dao.DictTypeDAO;
import cn.valuetodays.api2.basic.persist.DictItemPO;
import cn.valuetodays.api2.basic.persist.DictTypePO;
import cn.valuetodays.api2.basic.vo.BarkDict;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import cn.vt.exception.AssertUtils;
import cn.vt.util.JsonUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典类型
 *
 * @author lei.liu
 * @since 2024-11-13 20:40
 */
@ApplicationScoped
@Slf4j
public class DictTypeService
    extends BaseCrudService<Long, DictTypePO, DictTypeDAO> {
    @Inject
    DictItemService dictItemService;


    //    @Cacheable(value = "getBarkDict#20m", cacheManager = RedisCache2Configuration.CACHE2_MANAGER_NAME)
    @Transactional
    public BarkDict getBarkDict() {
        return getDictItemsAsObject("bark", BarkDict.class);
    }

    private <T> T getDictItemsAsObject(String dictTypeCode, Class<T> clazz) {
        DictTypePO dictType = getRepository().findByCode(dictTypeCode);
        AssertUtils.assertNotNull(dictType);
        DictTypePO.Status status = dictType.getStatus();
        AssertUtils.assertTrue(DictTypePO.Status.NORMAL == status, "deleted dict_type, code=" + dictTypeCode);
        List<DictItemPO> items = dictItemService.findAllByTypeId(dictType.getId());
        Map<String, String> map = items.stream()
            .filter(e -> DictItemPO.Status.NORMAL == e.getStatus())
            .collect(Collectors.toMap(e -> e.getCode().substring((dictTypeCode + "_").length()), DictItemPO::getValue));
        String json = JsonUtils.toJsonString(map);
        return JsonUtils.toObject(json, clazz);
    }

}
