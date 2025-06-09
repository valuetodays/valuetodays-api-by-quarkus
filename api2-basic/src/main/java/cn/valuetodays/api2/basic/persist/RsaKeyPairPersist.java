package cn.valuetodays.api2.basic.persist;

import cn.valuetodays.api2.basic.enums.CommonEnums;
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
 * rsa公钥私钥对
 *
 * @author lei.liu
 * @since 2025-06-09 19:57
 */
@Table(name = "basic_rsa_key_pair")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true /*, value = {"hibernateLazyInitializer", "handler"}*/)
@EqualsAndHashCode(callSuper = true)
@Data
public class RsaKeyPairPersist extends JpaCrudLongIdBasePersist {

    @Column(name = "pri_key")
    private String priKey;
    @Column(name = "pub_key")
    private String pubKey;
    @Column(name = "key_id")
    private String keyId;
    @Column(name = "enable_status")
    @Enumerated(EnumType.STRING)
    private CommonEnums.EnableStatus enableStatus;
}
