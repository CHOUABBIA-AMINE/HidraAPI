/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CathodicProtectionMeasurementJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CathodicProtectionMeasurement.
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
     * Database-backed JPA entity for CathodicProtectionMeasurement.
     */
    @Entity
    @Table(name = "hidra_integrity_cathodic_protection_measurement")
    public class CathodicProtectionMeasurementJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "survey_id", nullable = false, length = 80)
    private String surveyId;

    @Column(name = "kilometer_point", nullable = true, precision = 14, scale = 4)
    private BigDecimal kilometerPoint;

    @Column(name = "pipe_to_soil_potential", nullable = true, precision = 18, scale = 6)
    private BigDecimal pipeToSoilPotential;

    @Column(name = "potential_unit_id", nullable = true, length = 80)
    private String potentialUnitId;

    @Column(name = "current_density", nullable = true, precision = 18, scale = 6)
    private BigDecimal currentDensity;

    @Column(name = "current_unit_id", nullable = true, length = 80)
    private String currentUnitId;

    @Column(name = "measurement_method_id", nullable = true, length = 80)
    private String measurementMethodId;

    @Column(name = "measured_at", nullable = false)
    private Instant measuredAt;

    @Column(name = "notes", nullable = true, columnDefinition = "text")
    private String notes;

        protected CathodicProtectionMeasurementJpaEntity() {
            // Required by JPA.
        }

        public CathodicProtectionMeasurementJpaEntity(
                String id,
            String surveyId,
            BigDecimal kilometerPoint,
            BigDecimal pipeToSoilPotential,
            String potentialUnitId,
            BigDecimal currentDensity,
            String currentUnitId,
            String measurementMethodId,
            Instant measuredAt,
            String notes
        ) {
            this.id = id;
        this.surveyId = surveyId;
        this.kilometerPoint = kilometerPoint;
        this.pipeToSoilPotential = pipeToSoilPotential;
        this.potentialUnitId = potentialUnitId;
        this.currentDensity = currentDensity;
        this.currentUnitId = currentUnitId;
        this.measurementMethodId = measurementMethodId;
        this.measuredAt = measuredAt;
        this.notes = notes;
        }


    public String id() {
        return id;
    }


    public String surveyId() {
        return surveyId;
    }


    public BigDecimal kilometerPoint() {
        return kilometerPoint;
    }


    public BigDecimal pipeToSoilPotential() {
        return pipeToSoilPotential;
    }


    public String potentialUnitId() {
        return potentialUnitId;
    }


    public BigDecimal currentDensity() {
        return currentDensity;
    }


    public String currentUnitId() {
        return currentUnitId;
    }


    public String measurementMethodId() {
        return measurementMethodId;
    }


    public Instant measuredAt() {
        return measuredAt;
    }


    public String notes() {
        return notes;
    }

    }
