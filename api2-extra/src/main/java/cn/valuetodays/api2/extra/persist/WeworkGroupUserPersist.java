package cn.valuetodays.api2.extra.persist;

import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 企业微信群组用户
 *
 * @author lei.liu
 * @since 2023-06-20 19:45
 */
@Table(name = "extra_wework_group_user", schema = "eblog")
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class WeworkGroupUserPersist extends JpaCrudLongIdBasePersist {
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
}
