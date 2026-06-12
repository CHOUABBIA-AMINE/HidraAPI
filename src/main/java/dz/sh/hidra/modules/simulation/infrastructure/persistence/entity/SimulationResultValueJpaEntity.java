/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationResultValueJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationResultValue.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for SimulationResultValue.
     */
    @Entity
    @Table(name = "hidra_simulation_result_value")
    public class SimulationResultValueJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "run_id", nullable = false, length = 80)
    private String runId;

    @Column(name = "target_type", nullable = false, length = 80)
    private String targetType;

    @Column(name = "target_id", nullable = false, length = 120)
    private String targetId;

    @Column(name = "metric_code", nullable = false, length = 120)
    private String metricCode;

    @Column(name = "value", nullable = false, precision = 18, scale = 6)
    private BigDecimal value;

    @Column(name = "unit_code", nullable = true, length = 40)
    private String unitCode;

    @Column(name = "time_offset_seconds", nullable = true)
    private Long timeOffsetSeconds;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

        protected SimulationResultValueJpaEntity() {
            // Required by JPA.
        }

        public SimulationResultValueJpaEntity(
                String id,
            String runId,
            String targetType,
            String targetId,
            String metricCode,
            BigDecimal value,
            String unitCode,
            Long timeOffsetSeconds,
            Instant recordedAt
        ) {
            this.id = id;
        this.runId = runId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.metricCode = metricCode;
        this.value = value;
        this.unitCode = unitCode;
        this.timeOffsetSeconds = timeOffsetSeconds;
        this.recordedAt = recordedAt;
        }


    public String id() {
        return id;
    }


    public String runId() {
        return runId;
    }


    public String targetType() {
        return targetType;
    }


    public String targetId() {
        return targetId;
    }


    public String metricCode() {
        return metricCode;
    }


    public BigDecimal value() {
        return value;
    }


    public String unitCode() {
        return unitCode;
    }


    public Long timeOffsetSeconds() {
        return timeOffsetSeconds;
    }


    public Instant recordedAt() {
        return recordedAt;
    }

    }
