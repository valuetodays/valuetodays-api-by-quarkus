package cn.valuetodays.api2.extra.persist;

import cn.valuetodays.quarkus.commons.base.jpa.JpaCrudLongIdBasePersist;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 企业微信群组批次
 *
 * @author lei.liu
 * @since 2023-06-20 19:45
 */
@Table(name = "extra_wework_group", schema = "eblog")
@Data
@EqualsAndHashCode(callSuper = true)
public class WeworkGroupBatchPersist extends JpaCrudLongIdBasePersist {
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "member_count")
    private Integer memberCount;
    @Column(name = "stat_datetime")
    private String statDatetime;
    @Column(name = "json_str")
    private String jsonStr;
}
