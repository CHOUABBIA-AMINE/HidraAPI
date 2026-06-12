/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskExposureJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskExposure.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for RiskExposure.
     */
    @Entity
    @Table(name = "hidra_risk_exposure")
    public class RiskExposureJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "risk_assessment_id", nullable = false, length = 80)
    private String riskAssessmentId;

    @Column(name = "exposure_type_id", nullable = false, length = 80)
    private String exposureTypeId;

    @Column(name = "exposed_object_type", nullable = false, length = 160)
    private String exposedObjectType;

    @Column(name = "exposed_object_id", nullable = false, length = 80)
    private String exposedObjectId;

    @Column(name = "exposed_object_code_snapshot", nullable = true, length = 160)
    private String exposedObjectCodeSnapshot;

    @Column(name = "exposed_object_label_snapshot", nullable = true, length = 500)
    private String exposedObjectLabelSnapshot;

    @Column(name = "exposure_start", nullable = true)
    private Instant exposureStart;

    @Column(name = "exposure_end", nullable = true)
    private Instant exposureEnd;

    @Column(name = "exposure_magnitude", nullable = true, precision = 18, scale = 6)
    private BigDecimal exposureMagnitude;

    @Column(name = "exposure_unit_id", nullable = true, length = 80)
    private String exposureUnitId;

    @Column(name = "population_exposure", nullable = true, precision = 18, scale = 6)
    private BigDecimal populationExposure;

    @Column(name = "environmental_sensitivity_id", nullable = true, length = 80)
    private String environmentalSensitivityId;

    @Column(name = "production_criticality_id", nullable = true, length = 80)
    private String productionCriticalityId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected RiskExposureJpaEntity() {
            // Required by JPA.
        }

        public RiskExposureJpaEntity(
                String id,
            String riskAssessmentId,
            String exposureTypeId,
            String exposedObjectType,
            String exposedObjectId,
            String exposedObjectCodeSnapshot,
            String exposedObjectLabelSnapshot,
            Instant exposureStart,
            Instant exposureEnd,
            BigDecimal exposureMagnitude,
            String exposureUnitId,
            BigDecimal populationExposure,
            String environmentalSensitivityId,
            String productionCriticalityId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.riskAssessmentId = riskAssessmentId;
        this.exposureTypeId = exposureTypeId;
        this.exposedObjectType = exposedObjectType;
        this.exposedObjectId = exposedObjectId;
        this.exposedObjectCodeSnapshot = exposedObjectCodeSnapshot;
        this.exposedObjectLabelSnapshot = exposedObjectLabelSnapshot;
        this.exposureStart = exposureStart;
        this.exposureEnd = exposureEnd;
        this.exposureMagnitude = exposureMagnitude;
        this.exposureUnitId = exposureUnitId;
        this.populationExposure = populationExposure;
        this.environmentalSensitivityId = environmentalSensitivityId;
        this.productionCriticalityId = productionCriticalityId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String riskAssessmentId() {
        return riskAssessmentId;
    }


    public String exposureTypeId() {
        return exposureTypeId;
    }


    public String exposedObjectType() {
        return exposedObjectType;
    }


    public String exposedObjectId() {
        return exposedObjectId;
    }


    public String exposedObjectCodeSnapshot() {
        return exposedObjectCodeSnapshot;
    }


    public String exposedObjectLabelSnapshot() {
        return exposedObjectLabelSnapshot;
    }


    public Instant exposureStart() {
        return exposureStart;
    }


    public Instant exposureEnd() {
        return exposureEnd;
    }


    public BigDecimal exposureMagnitude() {
        return exposureMagnitude;
    }


    public String exposureUnitId() {
        return exposureUnitId;
    }


    public BigDecimal populationExposure() {
        return populationExposure;
    }


    public String environmentalSensitivityId() {
        return environmentalSensitivityId;
    }


    public String productionCriticalityId() {
        return productionCriticalityId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
