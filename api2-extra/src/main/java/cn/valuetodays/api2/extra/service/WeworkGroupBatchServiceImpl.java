package cn.valuetodays.api2.extra.service;

import cn.valuetodays.api2.extra.dao.WeworkGroupBatchDAO;
import cn.valuetodays.api2.extra.dao.WeworkGroupUserDAO;
import cn.valuetodays.api2.extra.persist.WeworkGroupBatchPersist;
import cn.valuetodays.api2.extra.persist.WeworkGroupUserPersist;
import cn.valuetodays.api2.extra.reqresp.SaveGroupAndMemberResp;
import cn.valuetodays.api2.extra.reqresp.WeworkDiffGroupResp;
import cn.valuetodays.api2.extra.reqresp.WeworkGroupAndMemberSaveReq;
import cn.valuetodays.quarkus.commons.base.BaseService;
import cn.vt.exception.AssertFailException;
import cn.vt.exception.AssertUtils;
import cn.vt.util.DateUtils;
import cn.vt.util.JsonUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 企业微信群组记录
 *
 * @author lei.liu
 * @since 2023-06-20
 */
@ApplicationScoped
@Slf4j
public class WeworkGroupBatchServiceImpl
    extends BaseService<Long, WeworkGroupBatchPersist, WeworkGroupBatchDAO> {
    @Inject
    WeworkGroupUserDAO weworkGroupUserDAO;


    @Transactional
    public WeworkDiffGroupResp diffUser(Long r1, Long r2) {
        List<Long> ids = Arrays.asList(r1, r2);
        List<WeworkGroupBatchPersist> groupRecords = listByIds(ids);
        if (CollectionUtils.isEmpty(groupRecords) || groupRecords.size() != ids.size()) {
            throw new AssertFailException("illegal record: " + r1 + ", " + r2);
        }
        WeworkGroupBatchPersist former = groupRecords.get(0);
        WeworkGroupBatchPersist latter = groupRecords.get(1);
        if (former.getUpdateTime().isAfter(latter.getUpdateTime())) {
            former = groupRecords.get(1);
            latter = groupRecords.get(0);
        }
        final Long formerGroupId = former.getId();
        final Long latterGroupId = latter.getId();

        List<WeworkGroupUserPersist> groupUsersAll = weworkGroupUserDAO.findByGroupIdIn(ids);
        List<WeworkGroupUserPersist> groupUsersFormer = ListUtils.select(groupUsersAll,
            e -> Objects.equals(e.getGroupId(), formerGroupId)
        );
        List<WeworkGroupUserPersist> groupUsersLatter = ListUtils.select(groupUsersAll,
            e -> Objects.equals(e.getGroupId(), latterGroupId)
        );

        Map<String, WeworkGroupUserPersist> userMapFormer = groupUsersFormer.stream()
            .collect(Collectors.toMap(WeworkGroupUserPersist::getEmail, e -> e));
        Map<String, WeworkGroupUserPersist> userMapLatter = groupUsersLatter.stream()
            .collect(Collectors.toMap(WeworkGroupUserPersist::getEmail, e -> e));

        List<String> uniqueIdFormer = groupUsersFormer.stream()
            .map(WeworkGroupUserPersist::getEmail)
            .toList();
        List<String> uniqueIdLatter = groupUsersLatter.stream()
            .map(WeworkGroupUserPersist::getEmail)
            .toList();

        List<String> intersectionUniqueIds = ListUtils.intersection(uniqueIdFormer, uniqueIdLatter);
        List<String> decreaseUniqueIdList = new ArrayList<>(uniqueIdFormer);
        decreaseUniqueIdList.removeAll(intersectionUniqueIds);

        List<String> increaseUniqueIdList = new ArrayList<>(uniqueIdLatter);
        increaseUniqueIdList.removeAll(intersectionUniqueIds);

        List<WeworkDiffGroupResp.GroupUserVo> decreaseUserList = decreaseUniqueIdList.stream()
            .map(userMapFormer::get)
            .map(this::toGroupUserVo)
            .toList();

        List<WeworkDiffGroupResp.GroupUserVo> increaseUserList = increaseUniqueIdList.stream()
            .map(userMapLatter::get)
            .map(this::toGroupUserVo)
            .toList();

        return WeworkDiffGroupResp.of(decreaseUserList, increaseUserList);
    }

    private WeworkDiffGroupResp.GroupUserVo toGroupUserVo(WeworkGroupUserPersist groupUserPO) {
        WeworkGroupUserPersist latest = weworkGroupUserDAO.findTop1ByEmailOrderByCreateTimeDesc(groupUserPO.getEmail());
        WeworkGroupUserPersist oldest = weworkGroupUserDAO.findTop1ByEmailOrderByCreateTimeAsc(groupUserPO.getEmail());
        WeworkDiffGroupResp.GroupUserVo groupUserVo = new WeworkDiffGroupResp.GroupUserVo();
        groupUserVo.setEmail(groupUserPO.getEmail());
        groupUserVo.setName(groupUserPO.getName());
        groupUserVo.setInDateStr(
            Optional.ofNullable(oldest)
                .map(e -> DateUtils.formatDate(e.getCreateTime()))
                .orElse("-")
        );
        groupUserVo.setOutDateStr(
            Optional.ofNullable(latest)
                .map(e -> DateUtils.formatDate(e.getCreateTime()))
                .orElse("-")
        );
        return groupUserVo;
    }

    @Transactional
    public SaveGroupAndMemberResp saveGroupAndMember(WeworkGroupAndMemberSaveReq req) {
        String groupName = req.getGroup();
        String emails = req.getEmails();
        String names = req.getNames();
        String missedEmailsOfNames = req.getMissedEmailsOfNames();

        log.info("process group of [{}]", groupName);
        if (StringUtils.equals("AUTO", groupName)) {
            WeworkGroupBatchPersist lastGroup = getRepository().findLastGroup();
            if (Objects.nonNull(lastGroup)) {
                groupName = lastGroup.getGroupName();
            }
        }
        List<WeworkGroupUserPersist> usersToSave = parseGroupUsers(missedEmailsOfNames, names, emails);
        if (CollectionUtils.isEmpty(usersToSave)) {
            return new SaveGroupAndMemberResp(0);
        }

        WeworkGroupBatchPersist groupPO = new WeworkGroupBatchPersist();
        groupPO.setGroupName(groupName);
        groupPO.setStatDatetime(DateUtils.formatDatetimeToday());
        groupPO.setMemberCount(usersToSave.size());
        groupPO.setJsonStr(JsonUtils.toJson(req));
        groupPO.initUserIdAndTime(1L);
        getRepository().persist(groupPO);
        AssertUtils.assertNotNull(groupPO.getId());

        usersToSave.forEach(e -> e.setGroupId(groupPO.getId()));
        weworkGroupUserDAO.persist(usersToSave);
        return new SaveGroupAndMemberResp(groupPO.getMemberCount());
    }

    private List<WeworkGroupUserPersist> parseGroupUsers(String missedEmailsOfNames, String names, String emails) {
        List<WeworkGroupUserPersist> usersToSave = null;
        if (StringUtils.isBlank(missedEmailsOfNames)) {
            usersToSave = processLegalRecord(names, emails);
        } else {
            String missedEmailsOfNamesToUse = StringUtils.replace(missedEmailsOfNames, ",", ";");
            missedEmailsOfNamesToUse = StringUtils.replace(missedEmailsOfNamesToUse, "，", ";");
            String[] missedEmailsOfNamesToUseArr = StringUtils.split(missedEmailsOfNamesToUse, ";");
            String namesToUse = names;
            List<WeworkGroupUserPersist> usersWithoutEmail = new ArrayList<>(missedEmailsOfNamesToUseArr.length);
            for (String s : missedEmailsOfNamesToUseArr) {
                namesToUse = StringUtils.replace(namesToUse, s + ";", "");
                WeworkGroupUserPersist userWithoutEmail = new WeworkGroupUserPersist();
                userWithoutEmail.setGroupId(0L);
                userWithoutEmail.setEmail(s);
                userWithoutEmail.setName(s);
                userWithoutEmail.initUserIdAndTime(1L);
                usersWithoutEmail.add(userWithoutEmail);
            }
            List<WeworkGroupUserPersist> usersWithEmail = processLegalRecord(namesToUse, emails);
            usersToSave = ListUtils.union(usersWithoutEmail, usersWithEmail);
        }
        return usersToSave;
    }


    private List<WeworkGroupUserPersist> processLegalRecord(String names, String emails) {
        String[] namesArr = StringUtils.split(names, ";");
        String[] emailsArr = StringUtils.split(emails, ";");
        if (namesArr.length != emailsArr.length) {
            log.error("Wrong number of names and emails");
            return new ArrayList<>(0);
        }
        return IntStream.range(0, namesArr.length)
            .mapToObj(e -> {
                String name = namesArr[e];
                String email = emailsArr[e];
                log.info("userObj={}, {}", name, email);
                WeworkGroupUserPersist user = new WeworkGroupUserPersist();
                user.setGroupId(0L);
                user.setName(name);
                user.setEmail(email);
                user.initUserIdAndTime(1L);
                return user;
            }).toList();
    }
}
