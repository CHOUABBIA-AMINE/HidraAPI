/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyQuantityCalculationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyQuantityCalculation.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.entity;

import dz.sh.hidra.modules.custody.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for CustodyQuantityCalculation.
     */
    @Entity
    @Table(name = "hidra_custody_quantity_calculation")
    public class CustodyQuantityCalculationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "calculation_number", nullable = false, length = 80)
    private String calculationNumber;

    @Column(name = "measurement_period_id", nullable = false, length = 80)
    private String measurementPeriodId;

    @Column(name = "batch_id", nullable = true, length = 80)
    private String batchId;

    @Enumerated(EnumType.STRING)
    @Column(name = "quantity_basis", nullable = false, length = 80)
    private CustodyQuantityBasis quantityBasis;

    @Column(name = "gross_observed_quantity", nullable = true, precision = 18, scale = 6)
    private BigDecimal grossObservedQuantity;

    @Column(name = "gross_standard_quantity", nullable = true, precision = 18, scale = 6)
    private BigDecimal grossStandardQuantity;

    @Column(name = "net_standard_quantity", nullable = true, precision = 18, scale = 6)
    private BigDecimal netStandardQuantity;

    @Column(name = "mass_quantity", nullable = true, precision = 18, scale = 6)
    private BigDecimal massQuantity;

    @Column(name = "quantity_unit_id", nullable = false, length = 80)
    private String quantityUnitId;

    @Column(name = "calculation_method_id", nullable = true, length = 80)
    private String calculationMethodId;

    @Column(name = "calculation_details_json", nullable = true, columnDefinition = "jsonb")
    private String calculationDetailsJson;

    @Column(name = "calculated_by_actor_id", nullable = true, length = 80)
    private String calculatedByActorId;

    @Column(name = "calculated_at", nullable = false)
    private Instant calculatedAt;

    @Column(name = "official", nullable = false)
    private boolean official;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected CustodyQuantityCalculationJpaEntity() {
            // Required by JPA.
        }

        public CustodyQuantityCalculationJpaEntity(
                String id,
            String calculationNumber,
            String measurementPeriodId,
            String batchId,
            CustodyQuantityBasis quantityBasis,
            BigDecimal grossObservedQuantity,
            BigDecimal grossStandardQuantity,
            BigDecimal netStandardQuantity,
            BigDecimal massQuantity,
            String quantityUnitId,
            String calculationMethodId,
            String calculationDetailsJson,
            String calculatedByActorId,
            Instant calculatedAt,
            boolean official,
            Instant createdAt
        ) {
            this.id = id;
        this.calculationNumber = calculationNumber;
        this.measurementPeriodId = measurementPeriodId;
        this.batchId = batchId;
        this.quantityBasis = quantityBasis;
        this.grossObservedQuantity = grossObservedQuantity;
        this.grossStandardQuantity = grossStandardQuantity;
        this.netStandardQuantity = netStandardQuantity;
        this.massQuantity = massQuantity;
        this.quantityUnitId = quantityUnitId;
        this.calculationMethodId = calculationMethodId;
        this.calculationDetailsJson = calculationDetailsJson;
        this.calculatedByActorId = calculatedByActorId;
        this.calculatedAt = calculatedAt;
        this.official = official;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String calculationNumber() {
        return calculationNumber;
    }


    public String measurementPeriodId() {
        return measurementPeriodId;
    }


    public String batchId() {
        return batchId;
    }


    public CustodyQuantityBasis quantityBasis() {
        return quantityBasis;
    }


    public BigDecimal grossObservedQuantity() {
        return grossObservedQuantity;
    }


    public BigDecimal grossStandardQuantity() {
        return grossStandardQuantity;
    }


    public BigDecimal netStandardQuantity() {
        return netStandardQuantity;
    }


    public BigDecimal massQuantity() {
        return massQuantity;
    }


    public String quantityUnitId() {
        return quantityUnitId;
    }


    public String calculationMethodId() {
        return calculationMethodId;
    }


    public String calculationDetailsJson() {
        return calculationDetailsJson;
    }


    public String calculatedByActorId() {
        return calculatedByActorId;
    }


    public Instant calculatedAt() {
        return calculatedAt;
    }


    public boolean official() {
        return official;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
