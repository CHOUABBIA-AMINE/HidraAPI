/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RemainingLifeEstimateJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RemainingLifeEstimate.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for RemainingLifeEstimate.
     */
    @Entity
    @Table(name = "hidra_integrity_remaining_life_estimate")
    public class RemainingLifeEstimateJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "defect_id", nullable = true, length = 80)
    private String defectId;

    @Column(name = "assessment_id", nullable = true, length = 80)
    private String assessmentId;

    @Column(name = "method_id", nullable = false, length = 80)
    private String methodId;

    @Column(name = "remaining_life_value", nullable = false, precision = 18, scale = 6)
    private BigDecimal remainingLifeValue;

    @Column(name = "remaining_life_unit_id", nullable = false, length = 80)
    private String remainingLifeUnitId;

    @Column(name = "corrosion_rate", nullable = true, precision = 18, scale = 6)
    private BigDecimal corrosionRate;

    @Column(name = "corrosion_rate_unit_id", nullable = true, length = 80)
    private String corrosionRateUnitId;

    @Column(name = "estimated_at", nullable = false)
    private Instant estimatedAt;

    @Column(name = "estimated_by_actor_id", nullable = true, length = 80)
    private String estimatedByActorId;

    @Column(name = "confidence_level_id", nullable = true, length = 80)
    private String confidenceLevelId;

    @Column(name = "notes", nullable = true, columnDefinition = "text")
    private String notes;

        protected RemainingLifeEstimateJpaEntity() {
            // Required by JPA.
        }

        public RemainingLifeEstimateJpaEntity(
                String id,
            String defectId,
            String assessmentId,
            String methodId,
            BigDecimal remainingLifeValue,
            String remainingLifeUnitId,
            BigDecimal corrosionRate,
            String corrosionRateUnitId,
            Instant estimatedAt,
            String estimatedByActorId,
            String confidenceLevelId,
            String notes
        ) {
            this.id = id;
        this.defectId = defectId;
        this.assessmentId = assessmentId;
        this.methodId = methodId;
        this.remainingLifeValue = remainingLifeValue;
        this.remainingLifeUnitId = remainingLifeUnitId;
        this.corrosionRate = corrosionRate;
        this.corrosionRateUnitId = corrosionRateUnitId;
        this.estimatedAt = estimatedAt;
        this.estimatedByActorId = estimatedByActorId;
        this.confidenceLevelId = confidenceLevelId;
        this.notes = notes;
        }


    public String id() {
        return id;
    }


    public String defectId() {
        return defectId;
    }


    public String assessmentId() {
        return assessmentId;
    }


    public String methodId() {
        return methodId;
    }


    public BigDecimal remainingLifeValue() {
        return remainingLifeValue;
    }


    public String remainingLifeUnitId() {
        return remainingLifeUnitId;
    }


    public BigDecimal corrosionRate() {
        return corrosionRate;
    }


    public String corrosionRateUnitId() {
        return corrosionRateUnitId;
    }


    public Instant estimatedAt() {
        return estimatedAt;
    }


    public String estimatedByActorId() {
        return estimatedByActorId;
    }


    public String confidenceLevelId() {
        return confidenceLevelId;
    }


    public String notes() {
        return notes;
    }

    }
