/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakSeverityAssessmentJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for LeakSeverityAssessment.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity;

import dz.sh.hidra.modules.leakdetection.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for LeakSeverityAssessment.
     */
    @Entity
    @Table(name = "hidra_leak_detection_severity_assessment")
    public class LeakSeverityAssessmentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "candidate_id", nullable = false, length = 80)
    private String candidateId;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity_level", nullable = false, length = 40)
    private LeakSeverityLevel severityLevel;

    @Column(name = "confidence_score", nullable = false, precision = 10, scale = 6)
    private BigDecimal confidenceScore;

    @Column(name = "estimated_leak_rate", nullable = true, precision = 18, scale = 6)
    private BigDecimal estimatedLeakRate;

    @Column(name = "leak_rate_unit_id", nullable = true, length = 80)
    private String leakRateUnitId;

    @Column(name = "estimated_volume_loss", nullable = true, precision = 18, scale = 6)
    private BigDecimal estimatedVolumeLoss;

    @Column(name = "volume_unit_id", nullable = true, length = 80)
    private String volumeUnitId;

    @Column(name = "assessment_reason", nullable = true, columnDefinition = "text")
    private String assessmentReason;

    @Column(name = "assessed_by_actor_id", nullable = true, length = 80)
    private String assessedByActorId;

    @Column(name = "assessed_at", nullable = false)
    private Instant assessedAt;

        protected LeakSeverityAssessmentJpaEntity() {
            // Required by JPA.
        }

        public LeakSeverityAssessmentJpaEntity(
                String id,
            String candidateId,
            LeakSeverityLevel severityLevel,
            BigDecimal confidenceScore,
            BigDecimal estimatedLeakRate,
            String leakRateUnitId,
            BigDecimal estimatedVolumeLoss,
            String volumeUnitId,
            String assessmentReason,
            String assessedByActorId,
            Instant assessedAt
        ) {
            this.id = id;
        this.candidateId = candidateId;
        this.severityLevel = severityLevel;
        this.confidenceScore = confidenceScore;
        this.estimatedLeakRate = estimatedLeakRate;
        this.leakRateUnitId = leakRateUnitId;
        this.estimatedVolumeLoss = estimatedVolumeLoss;
        this.volumeUnitId = volumeUnitId;
        this.assessmentReason = assessmentReason;
        this.assessedByActorId = assessedByActorId;
        this.assessedAt = assessedAt;
        }


    public String id() {
        return id;
    }


    public String candidateId() {
        return candidateId;
    }


    public LeakSeverityLevel severityLevel() {
        return severityLevel;
    }


    public BigDecimal confidenceScore() {
        return confidenceScore;
    }


    public BigDecimal estimatedLeakRate() {
        return estimatedLeakRate;
    }


    public String leakRateUnitId() {
        return leakRateUnitId;
    }


    public BigDecimal estimatedVolumeLoss() {
        return estimatedVolumeLoss;
    }


    public String volumeUnitId() {
        return volumeUnitId;
    }


    public String assessmentReason() {
        return assessmentReason;
    }


    public String assessedByActorId() {
        return assessedByActorId;
    }


    public Instant assessedAt() {
        return assessedAt;
    }

    }
