package cn.valuetodays.api2.extra.persist;

import cn.valuetodays.api2.web.common.CommonEnums;
import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lei.liu
 * @since 2024-04-28 14:07
 */
@Table(name = "extra_weibo_user")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class WeiboUserPO extends JpaCrudLongIdBasePersist {
    @Column(name = "screen_name")
    private String screenName;
    @Column(name = "maintain")
    @Enumerated(EnumType.STRING)
    private CommonEnums.YNEnum maintain;
}
