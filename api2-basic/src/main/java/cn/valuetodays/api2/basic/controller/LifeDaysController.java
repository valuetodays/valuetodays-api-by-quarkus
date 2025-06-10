package cn.valuetodays.api2.basic.controller;

import cn.valuetodays.api2.basic.vo.LeftDaysInLifeReq;
import cn.valuetodays.api2.basic.vo.LeftDaysInLifeResp;
import cn.vt.core.Title;
import cn.vt.util.DateUtils;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-04-27 16:36
 */
@Path("/lifeDays")
public class LifeDaysController {
    @Title({"生命倒计时", "生命中剩余天数"})
    @Path("/defaultLeftDaysInLife")
    @POST
    public List<LeftDaysInLifeResp> defaultLeftDaysInLife() {
        LeftDaysInLifeReq req = new LeftDaysInLifeReq();
        req.setUserName("Billy");
        req.setExpectedAge(65);
        req.setDayOfBirth(LocalDate.of(1992, 11, 1).format(DateUtils.DEFAULT_DATE_FORMATTER));
        return this.leftDaysInLifeBatch(List.of(req));
    }

    @Title({"生命倒计时", "生命中剩余天数"})
    @Path("/leftDaysInLife")
    @POST
    public LeftDaysInLifeResp leftDaysInLife(@Valid LeftDaysInLifeReq req) {
        LocalDate now = LocalDate.now();
        return leftDaysInLife0(req, now);
    }

    public LeftDaysInLifeResp leftDaysInLife0(LeftDaysInLifeReq req, LocalDate targetDate) {
        String dayOfBirth = req.getDayOfBirth();
        int expectedAge = req.getExpectedAge();

        DateTimeFormatter defaultDateFormatter = DateUtils.DEFAULT_DATE_FORMATTER;
        LocalDate brithLdt = LocalDate.parse(dayOfBirth, defaultDateFormatter);
        long hadDays = DateUtils.intervalDays(brithLdt, targetDate);
        long hadYears = ChronoUnit.YEARS.between(brithLdt, targetDate);

        LocalDate deadLdt = brithLdt.plusYears(expectedAge);
        long leftDays = DateUtils.intervalDays(targetDate, deadLdt);
        LeftDaysInLifeResp resp = new LeftDaysInLifeResp();
        resp.setHadDays((int) hadDays);
        resp.setDayOfBirth(dayOfBirth);
        resp.setToday(targetDate.format(defaultDateFormatter));
        resp.setExpectedAge(expectedAge);
        resp.setAge((int) hadYears);
        resp.setLeftDays((int) leftDays);
        resp.setHadDaysPctg((int) (100.0 * hadDays / (hadDays + leftDays)));
        resp.setUserName(req.getUserName());
        return resp;
    }

    @Title({"生命倒计时(批量)", "生命中剩余天数(批量)"})
    @Path("/leftDaysInLifeBatch")
    @POST
    public List<LeftDaysInLifeResp> leftDaysInLifeBatch(List<LeftDaysInLifeReq> reqList) {
        if (CollectionUtils.isEmpty(reqList)) {
            return List.of();
        }
        return reqList.stream()
            .map(this::leftDaysInLife)
            .toList();
    }

}
