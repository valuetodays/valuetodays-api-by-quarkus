package valuetodays.demo.commons.base.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * .
 *
 * @author lei.liu
 * @since 2025-04-25
 */
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public class JpaCrudLongIdBasePersist extends JpaLongIdBasePersist {

    @Column(name = "create_time", insertable = false, updatable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @Column(name = "update_time", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    @Column(name = "create_user_id", nullable = false, updatable = false)
    private Long createUserId;
    @Column(name = "update_user_id", nullable = false)
    private Long updateUserId;

    @Transient
    private String createUserName;
    @Transient
    private String updateUserName;

    @JsonIgnore
    public boolean isNew() {
        return Objects.isNull(getId());
    }

    public void initCreateTimeAndUpdateTime() {
        LocalDateTime now = LocalDateTime.now();
        if (isNew()) {
            setCreateTime(now);
        }
        setUpdateTime(now);
    }

    public void initCreateUserIdAndUpdateUserId(Long userId) {
        if (isNew()) {
            setCreateUserId(userId);
        }
        setUpdateUserId(userId);
    }

    public void initUserIdAndTime(Long userId) {
        initCreateUserIdAndUpdateUserId(userId);
        initCreateTimeAndUpdateTime();
    }
}
