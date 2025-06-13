package cn.valuetodays.api.account.service;

import java.util.Objects;

import cn.valuetodays.api.account.dao.UserOptionDAO;
import cn.valuetodays.api.account.persist.UserOptionPO;
import cn.valuetodays.quarkus.commons.base.BaseCrudService;
import cn.vt.exception.AssertUtils;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author lei.liu
 * @since 2019-10-22 16:36
 */
@ApplicationScoped
public class UserOptionServiceImpl
    extends BaseCrudService<Long, UserOptionPO, UserOptionDAO> {

    @Override
    public UserOptionPO save(UserOptionPO userOption) {
        AssertUtils.assertNotNull(userOption, "illegal id");
        AssertUtils.assertTrue(userOption.getId() > 0, "illegal id");
        int monthExpenseLimit = userOption.getMonthExpenseLimit();
        AssertUtils.assertTrue(monthExpenseLimit > 0, "illegal monthExpenseLimit");

        UserOptionPO option = getRepository().findById(userOption.getId());
        if (Objects.nonNull(option)) {
            option.setMonthExpenseLimit(monthExpenseLimit);
            getRepository().persist(option);
            return option;
        }

        return super.save(userOption);
    }
}
