/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringThresholdJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MonitoringThreshold.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity;

import dz.sh.hidra.modules.monitoring.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for MonitoringThreshold.
     */
    @Entity
    @Table(name = "hidra_monitoring_threshold")
    public class MonitoringThresholdJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "rule_id", nullable = false, length = 80)
    private String ruleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "threshold_direction", nullable = false, length = 80)
    private MonitoringThresholdDirection thresholdDirection;

    @Column(name = "low_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal lowValue;

    @Column(name = "high_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal highValue;

    @Column(name = "expected_text_value", nullable = true, length = 160)
    private String expectedTextValue;

    @Column(name = "unit_id", nullable = true, length = 80)
    private String unitId;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 40)
    private DeviationSeverity severity;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected MonitoringThresholdJpaEntity() {
            // Required by JPA.
        }

        public MonitoringThresholdJpaEntity(
                String id,
            String ruleId,
            MonitoringThresholdDirection thresholdDirection,
            BigDecimal lowValue,
            BigDecimal highValue,
            String expectedTextValue,
            String unitId,
            DeviationSeverity severity,
            Instant validFrom,
            Instant validTo,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.ruleId = ruleId;
        this.thresholdDirection = thresholdDirection;
        this.lowValue = lowValue;
        this.highValue = highValue;
        this.expectedTextValue = expectedTextValue;
        this.unitId = unitId;
        this.severity = severity;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String ruleId() {
        return ruleId;
    }


    public MonitoringThresholdDirection thresholdDirection() {
        return thresholdDirection;
    }


    public BigDecimal lowValue() {
        return lowValue;
    }


    public BigDecimal highValue() {
        return highValue;
    }


    public String expectedTextValue() {
        return expectedTextValue;
    }


    public String unitId() {
        return unitId;
    }


    public DeviationSeverity severity() {
        return severity;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public boolean active() {
        return active;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
