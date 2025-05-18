//package cn.valuetodays.api2.web;
//
//import cn.valuetodays.common.core.orm.PopulatedEntity;
//import cn.valuetodays.common.jpa.support.entity.JpaAccountableLongIdEntity;
//import cn.valuetodays.common.jpa.support.entity.JpaBaseEntity;
//import cn.valuetodays.common.jpa.support.entity.JpaEntity;
//import cn.valuetodays.common.jpa.support.query.PageQueryReqIO;
//import cn.valuetodays.common.jpa.support.query.SortableQuerySearchIO;
//import cn.valuetodays.common.jpa.support.query.vo.EntityEnumConfig;
//import cn.valuetodays.common.jpa.support.service.IJpaService;
//import cn.valuetodays.common.web.rest.entity.vo.IdReqVo;
//import cn.valuetodays.common.web.rest.save.SaveReq;
//import cn.valuetodays.common.web.rest.save.SaveResp;
//import cn.valuetodays.quarkus.commons.base.BaseService;
//import cn.valuetodays.quarkus.commons.base.jpa.JpaBasePersist;
//import cn.valuetodays.util.ConvertUtils;
//import cn.vt.exception.AssertUtils;
//import jakarta.annotation.PostConstruct;
//import jakarta.validation.ConstraintViolationException;
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.io.Serializable;
//import java.lang.reflect.ParameterizedType;
//import java.util.List;
//import java.util.Objects;
//
///**
// * 负责crud操作
// *
// * @author lei.liu
// * @since 2019-12-03 17:20
// *
// * @param <ID> ID
// * @param <T> T
// * @param <S> Service
// *
// */
//@Slf4j
//public abstract class BaseCrudController<
//    ID extends Serializable,
//    T extends JpaBasePersist<ID>,
//    S extends BaseService<ID, T, JpaRepository<T, ID>>
//    >  {
//
//    private S service;
//
//    @Autowired
//    public void setService(S service) {
//        this.service = service;
//    }
//
//    protected S getService() {
//        return service;
//    }
//
//    // 实体类的真实类型
//    private Class<JpaBasePersist<ID>> entityClass;
//
//    @PostConstruct
//    public void init() {
//        initEntityClass();
//    }
//
//    private void initEntityClass() {
//        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
//        entityClass = (Class<JpaBasePersist<ID>>) pt.getActualTypeArguments()[1];
//    }
//
//    /////////////////////////////////
//
//    /**
//     * 查询之前可以进行的操作
//     * @param pageQueryReqIO pageQueryReqIO
//     */
//    protected Page<T> beforeQuery(PageQueryReqIO pageQueryReqIO) {
//        return null;
//    }
//    /**
//     * 查询之前可以进行的操作
//     * @param sortableQuerySearchIO sortableQuerySearchIO
//     */
//    protected Page<T> beforeQuery(SortableQuerySearchIO sortableQuerySearchIO) {
//        return null;
//    }
//
//    ////////////////////////////////////
//
//    @PostMapping({"/findAll", "/findAll.do"})
//    @ResponseBody
//    public final List<T> findAll() {
//        return service.list();
//    }
//
//    @PostMapping({"/list", "/list.do"})
//    @ResponseBody
//    public final List<T> list(@RequestBody List<ID> ids) {
//        return service.list(ids);
//    }
//
//    @PostMapping({"/query", "/query.do"})
//    @ResponseBody
//    public Page<T> query(@RequestBody SortableQuerySearchIO sortableQuerySearchIO) {
//        Page<T> ts = beforeQuery(sortableQuerySearchIO);
//        if (Objects.nonNull(ts)) {
//            return ts;
//        }
//        Page<T> pagedData = service.query(sortableQuerySearchIO);
//        return afterQuery(pagedData, sortableQuerySearchIO);
//    }
//
//    @PostMapping({"/pageQuery", "/pageQuery.do"})
//    @ResponseBody
//    public Page<T> pageQuery(@RequestBody PageQueryReqIO pageQueryReqIO) {
//        Page<T> ts = beforeQuery(pageQueryReqIO);
//        if (Objects.nonNull(ts)) {
//            return ts;
//        }
//        Page<T> pagedData = service.query(pageQueryReqIO);
//        return afterQuery(pagedData, pageQueryReqIO);
//    }
//
//    protected Page<T> afterQuery(Page<T> pagedData, SortableQuerySearchIO sortableQuerySearchIO) {
//        return pagedData;
//    }
//    protected Page<T> afterQuery(Page<T> pagedData, PageQueryReqIO pageQueryReqIO) {
//        return pagedData;
//    }
//
//    @PostMapping({"/get", "/get.do"})
//    @ResponseBody
//    public T get(ID id) {
//        return service.findById(id);
//    }
//
//    @PostMapping({"/findById", "/findById.do"})
//    @ResponseBody
//    public T findById(@RequestBody IdReqVo<ID> idReq) {
//        return service.findById(idReq.getId());
//    }
//
//    @PostMapping("checkUnique")
//    @ResponseBody
//    public long checkUnique(SortableQuerySearchIO sortableQuerySearchIO) {
//        return service.checkUnique(sortableQuerySearchIO);
//    }
//
//    @PostMapping({"/save", "/save.do"})
//    @ResponseBody
//    public SAVERESP save(@RequestBody @Valid SAVEREQ req) {
//        T t = (T) ConvertUtils.convertObj(req, entityClass);
//        fillCreateTimeAndUpdateTime(t);
//        beforeSave(t);
//        beforeSave2(t, req);
//        T saved = service.save(t);
//        if (saved instanceof PopulatedEntity pe) {
//            pe.populate(t);
//        }
//        afterSave(saved);
//        SAVERESP resp = (SAVERESP) ConvertUtils.convertObj(t, saveRespClass);
//        postBuildSaveResp(resp, req, saved);
//        return resp;
//    }
//
//    protected void beforeSave2(T t, SAVEREQ req) {
//
//    }
//
//    protected void postBuildSaveResp(SAVERESP resp, SAVEREQ req, T saved) {
//    }
//
//    private void fillCreateTimeAndUpdateTime(T t) {
//        if (t instanceof JpaEntity<?> je) {
//            je.initCreateTimeAndUpdateTime();
//        }
//    }
//
//    protected void beforeSave(T t) {
//        final Long currentAccountId = getCurrentAccountId();
//        if (t instanceof JpaEntity<?> je) {
//            je.setCreateUserId(currentAccountId);
//            je.setUpdateUserId(currentAccountId);
//        }
//        if (t instanceof JpaAccountableLongIdEntity jalie) {
//            jalie.setAccountId(currentAccountId);
//        }
//    }
//
//    protected void afterSave(final T saved) {
//    }
//
//    @PostMapping({"/delById", "/delById.do"})
//    @ResponseBody
//    public void delById(@RequestBody ID id) {
//        IdReqVo<ID> idReqVo = new IdReqVo<>();
//        idReqVo.setId(id);
//        delete(idReqVo);
//    }
//
//    @PostMapping({"/delete", "/delete.do"})
//    @ResponseBody
//    public void delete(@RequestBody IdReqVo<ID> idReqVo) {
//        AssertUtils.assertNotNull(idReqVo, "idReqVo is null!");
//        ID id = idReqVo.getId();
//        AssertUtils.assertNotNull(id, "id is null!");
//        try {
//            T old = service.findById(id);
//            if (Objects.nonNull(old)) {
//                beforeDelete(old);
//                service.deleteById(id);
//                afterDelete(old);
//            }
//        } catch (ConstraintViolationException e) {
//            log.error("exception when delete()", e);
//        }
//    }
//
//    protected void beforeDelete(T t) {
//        checkCreateUserId(t);
//    }
//
//    private void checkCreateUserId(T entity) {
//        if (!(entity instanceof JpaEntity<?> jpaEntity)) {
//            return;
//        }
//        if (!Objects.equals(jpaEntity.getCreateUserId(), getCurrentAccountId())) {
//            AssertUtils.fail("不能删除别人的数据~");
//        }
//    }
//
//    protected void afterDelete(T t) {
//
//    }
//
//    protected EntityEnumConfig enumConfig(Class<? extends JpaBaseEntity<?>> entityClass) {
//        return EntityEnumConfig.of(entityClass);
//    }
//
//    @PostMapping({"enumConfig", "enumConfig.do"})
//    @ResponseBody
//    public EntityEnumConfig enumConfig() {
//        return enumConfig(entityClass);
//    }
//
//}
