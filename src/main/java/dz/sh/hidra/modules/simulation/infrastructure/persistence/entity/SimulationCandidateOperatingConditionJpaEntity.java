/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationCandidateOperatingConditionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationCandidateOperatingCondition.
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
     * Database-backed JPA entity for SimulationCandidateOperatingCondition.
     */
    @Entity
    @Table(name = "hidra_simulation_candidate_operating_condition")
    public class SimulationCandidateOperatingConditionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "candidate_id", nullable = false, length = 80)
    private String candidateId;

    @Column(name = "target_type", nullable = false, length = 80)
    private String targetType;

    @Column(name = "target_id", nullable = false, length = 120)
    private String targetId;

    @Column(name = "metric_code", nullable = false, length = 120)
    private String metricCode;

    @Column(name = "expected_value", nullable = false, precision = 18, scale = 6)
    private BigDecimal expectedValue;

    @Column(name = "unit_code", nullable = true, length = 40)
    private String unitCode;

    @Column(name = "time_offset_seconds", nullable = true)
    private Long timeOffsetSeconds;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationCandidateOperatingConditionJpaEntity() {
            // Required by JPA.
        }

        public SimulationCandidateOperatingConditionJpaEntity(
                String id,
            String candidateId,
            String targetType,
            String targetId,
            String metricCode,
            BigDecimal expectedValue,
            String unitCode,
            Long timeOffsetSeconds,
            Instant createdAt
        ) {
            this.id = id;
        this.candidateId = candidateId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.metricCode = metricCode;
        this.expectedValue = expectedValue;
        this.unitCode = unitCode;
        this.timeOffsetSeconds = timeOffsetSeconds;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String candidateId() {
        return candidateId;
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


    public BigDecimal expectedValue() {
        return expectedValue;
    }


    public String unitCode() {
        return unitCode;
    }


    public Long timeOffsetSeconds() {
        return timeOffsetSeconds;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
