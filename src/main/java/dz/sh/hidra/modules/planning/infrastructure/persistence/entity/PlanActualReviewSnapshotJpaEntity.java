/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanActualReviewSnapshotJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PlanActualReviewSnapshot.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.entity;

import dz.sh.hidra.modules.planning.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for PlanActualReviewSnapshot.
     */
    @Entity
    @Table(name = "hidra_planning_actual_review_snapshot")
    public class PlanActualReviewSnapshotJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "plan_target_id", nullable = false, length = 80)
    private String planTargetId;

    @Column(name = "monitoring_deviation_id", nullable = true, length = 80)
    private String monitoringDeviationId;

    @Column(name = "trusted_telemetry_reading_id", nullable = true, length = 80)
    private String trustedTelemetryReadingId;

    @Column(name = "actual_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal actualValue;

    @Column(name = "planned_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal plannedValue;

    @Column(name = "difference_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal differenceValue;

    @Column(name = "difference_percent", nullable = true, precision = 10, scale = 4)
    private BigDecimal differencePercent;

    @Column(name = "unit_id", nullable = true, length = 80)
    private String unitId;

    @Column(name = "reviewed_at", nullable = false)
    private Instant reviewedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_source", nullable = false, length = 40)
    private ReviewSource reviewSource;

    @Column(name = "notes", nullable = true, length = 500)
    private String notes;

        protected PlanActualReviewSnapshotJpaEntity() {
            // Required by JPA.
        }

        public PlanActualReviewSnapshotJpaEntity(
                String id,
            String planTargetId,
            String monitoringDeviationId,
            String trustedTelemetryReadingId,
            BigDecimal actualValue,
            BigDecimal plannedValue,
            BigDecimal differenceValue,
            BigDecimal differencePercent,
            String unitId,
            Instant reviewedAt,
            ReviewSource reviewSource,
            String notes
        ) {
            this.id = id;
        this.planTargetId = planTargetId;
        this.monitoringDeviationId = monitoringDeviationId;
        this.trustedTelemetryReadingId = trustedTelemetryReadingId;
        this.actualValue = actualValue;
        this.plannedValue = plannedValue;
        this.differenceValue = differenceValue;
        this.differencePercent = differencePercent;
        this.unitId = unitId;
        this.reviewedAt = reviewedAt;
        this.reviewSource = reviewSource;
        this.notes = notes;
        }


    public String id() {
        return id;
    }


    public String planTargetId() {
        return planTargetId;
    }


    public String monitoringDeviationId() {
        return monitoringDeviationId;
    }


    public String trustedTelemetryReadingId() {
        return trustedTelemetryReadingId;
    }


    public BigDecimal actualValue() {
        return actualValue;
    }


    public BigDecimal plannedValue() {
        return plannedValue;
    }


    public BigDecimal differenceValue() {
        return differenceValue;
    }


    public BigDecimal differencePercent() {
        return differencePercent;
    }


    public String unitId() {
        return unitId;
    }


    public Instant reviewedAt() {
        return reviewedAt;
    }


    public ReviewSource reviewSource() {
        return reviewSource;
    }


    public String notes() {
        return notes;
    }

    }
