/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyCorrectionFactorJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyCorrectionFactor.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for CustodyCorrectionFactor.
     */
    @Entity
    @Table(name = "hidra_custody_correction_factor")
    public class CustodyCorrectionFactorJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "quantity_calculation_id", nullable = false, length = 80)
    private String quantityCalculationId;

    @Column(name = "factor_type_id", nullable = false, length = 80)
    private String factorTypeId;

    @Column(name = "factor_code", nullable = false, length = 80)
    private String factorCode;

    @Column(name = "factor_value", nullable = false, precision = 18, scale = 8)
    private BigDecimal factorValue;

    @Column(name = "basis_description", nullable = true, columnDefinition = "text")
    private String basisDescription;

    @Column(name = "source_reference_id", nullable = true, length = 80)
    private String sourceReferenceId;

    @Column(name = "applied_at", nullable = false)
    private Instant appliedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected CustodyCorrectionFactorJpaEntity() {
            // Required by JPA.
        }

        public CustodyCorrectionFactorJpaEntity(
                String id,
            String quantityCalculationId,
            String factorTypeId,
            String factorCode,
            BigDecimal factorValue,
            String basisDescription,
            String sourceReferenceId,
            Instant appliedAt,
            Instant createdAt
        ) {
            this.id = id;
        this.quantityCalculationId = quantityCalculationId;
        this.factorTypeId = factorTypeId;
        this.factorCode = factorCode;
        this.factorValue = factorValue;
        this.basisDescription = basisDescription;
        this.sourceReferenceId = sourceReferenceId;
        this.appliedAt = appliedAt;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String quantityCalculationId() {
        return quantityCalculationId;
    }


    public String factorTypeId() {
        return factorTypeId;
    }


    public String factorCode() {
        return factorCode;
    }


    public BigDecimal factorValue() {
        return factorValue;
    }


    public String basisDescription() {
        return basisDescription;
    }


    public String sourceReferenceId() {
        return sourceReferenceId;
    }


    public Instant appliedAt() {
        return appliedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
