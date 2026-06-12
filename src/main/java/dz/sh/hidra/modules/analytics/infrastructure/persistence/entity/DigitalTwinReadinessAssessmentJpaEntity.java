/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DigitalTwinReadinessAssessmentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for DigitalTwinReadinessAssessment.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.entity;

import dz.sh.hidra.modules.analytics.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for DigitalTwinReadinessAssessment.
     */
    @Entity
    @Table(name = "hidra_analytics_digital_twin_readiness_assessment")
    public class DigitalTwinReadinessAssessmentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "scope_type", nullable = false, length = 80)
    private String scopeType;

    @Column(name = "scope_id", nullable = true, length = 120)
    private String scopeId;

    @Column(name = "topology_snapshot_id", nullable = false, length = 120)
    private String topologySnapshotId;

    @Column(name = "assessment_period_start", nullable = false)
    private Instant assessmentPeriodStart;

    @Column(name = "assessment_period_end", nullable = false)
    private Instant assessmentPeriodEnd;

    @Column(name = "telemetry_completeness_score", nullable = true, precision = 10, scale = 6)
    private BigDecimal telemetryCompletenessScore;

    @Column(name = "telemetry_quality_score", nullable = true, precision = 10, scale = 6)
    private BigDecimal telemetryQualityScore;

    @Column(name = "topology_completeness_score", nullable = true, precision = 10, scale = 6)
    private BigDecimal topologyCompletenessScore;

    @Column(name = "model_availability_score", nullable = true, precision = 10, scale = 6)
    private BigDecimal modelAvailabilityScore;

    @Column(name = "lineage_completeness_score", nullable = true, precision = 10, scale = 6)
    private BigDecimal lineageCompletenessScore;

    @Column(name = "overall_readiness_score", nullable = true, precision = 10, scale = 6)
    private BigDecimal overallReadinessScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "readiness_status", nullable = false, length = 40)
    private DigitalTwinReadinessStatus readinessStatus;

    @Column(name = "assessed_at", nullable = false)
    private Instant assessedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected DigitalTwinReadinessAssessmentJpaEntity() {
            // Required by JPA.
        }

        public DigitalTwinReadinessAssessmentJpaEntity(
                String id,
            String scopeType,
            String scopeId,
            String topologySnapshotId,
            Instant assessmentPeriodStart,
            Instant assessmentPeriodEnd,
            BigDecimal telemetryCompletenessScore,
            BigDecimal telemetryQualityScore,
            BigDecimal topologyCompletenessScore,
            BigDecimal modelAvailabilityScore,
            BigDecimal lineageCompletenessScore,
            BigDecimal overallReadinessScore,
            DigitalTwinReadinessStatus readinessStatus,
            Instant assessedAt,
            Instant createdAt
        ) {
            this.id = id;
        this.scopeType = scopeType;
        this.scopeId = scopeId;
        this.topologySnapshotId = topologySnapshotId;
        this.assessmentPeriodStart = assessmentPeriodStart;
        this.assessmentPeriodEnd = assessmentPeriodEnd;
        this.telemetryCompletenessScore = telemetryCompletenessScore;
        this.telemetryQualityScore = telemetryQualityScore;
        this.topologyCompletenessScore = topologyCompletenessScore;
        this.modelAvailabilityScore = modelAvailabilityScore;
        this.lineageCompletenessScore = lineageCompletenessScore;
        this.overallReadinessScore = overallReadinessScore;
        this.readinessStatus = readinessStatus;
        this.assessedAt = assessedAt;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String scopeType() {
        return scopeType;
    }


    public String scopeId() {
        return scopeId;
    }


    public String topologySnapshotId() {
        return topologySnapshotId;
    }


    public Instant assessmentPeriodStart() {
        return assessmentPeriodStart;
    }


    public Instant assessmentPeriodEnd() {
        return assessmentPeriodEnd;
    }


    public BigDecimal telemetryCompletenessScore() {
        return telemetryCompletenessScore;
    }


    public BigDecimal telemetryQualityScore() {
        return telemetryQualityScore;
    }


    public BigDecimal topologyCompletenessScore() {
        return topologyCompletenessScore;
    }


    public BigDecimal modelAvailabilityScore() {
        return modelAvailabilityScore;
    }


    public BigDecimal lineageCompletenessScore() {
        return lineageCompletenessScore;
    }


    public BigDecimal overallReadinessScore() {
        return overallReadinessScore;
    }


    public DigitalTwinReadinessStatus readinessStatus() {
        return readinessStatus;
    }


    public Instant assessedAt() {
        return assessedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
