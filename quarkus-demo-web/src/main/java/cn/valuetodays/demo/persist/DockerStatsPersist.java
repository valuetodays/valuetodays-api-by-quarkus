package cn.valuetodays.demo.persist;

import cn.valuetodays.quarkus.commons.base.jpa.JpaLongIdBasePersist;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "metric_docker_stats")
public class DockerStatsPersist extends JpaLongIdBasePersist {
    @Column(name = "stat_datetime")
    private LocalDateTime statDatetime;
    private String name;
    @Column(name = "block_io")
    private String blockIo;
    @Column(name = "net_io")
    private String netIo;
    @Column(name = "cpu_perc")
    private String cpuPerc;
    @Column(name = "container_id")
    private String containerId;
    @Column(name = "mem_perc")
    private String memPerc;
    @Column(name = "mem_usage")
    private String memUsage;
}
