/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyReconciliationJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyReconciliation.
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
     * Database-backed JPA entity for CustodyReconciliation.
     */
    @Entity
    @Table(name = "hidra_custody_reconciliation")
    public class CustodyReconciliationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "reconciliation_number", nullable = false, length = 80)
    private String reconciliationNumber;

    @Column(name = "measurement_period_id", nullable = false, length = 80)
    private String measurementPeriodId;

    @Column(name = "agreement_id", nullable = false, length = 80)
    private String agreementId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private CustodyReconciliationStatus status;

    @Column(name = "total_ticket_quantity", nullable = true, precision = 18, scale = 6)
    private BigDecimal totalTicketQuantity;

    @Column(name = "total_measured_quantity", nullable = true, precision = 18, scale = 6)
    private BigDecimal totalMeasuredQuantity;

    @Column(name = "difference_quantity", nullable = true, precision = 18, scale = 6)
    private BigDecimal differenceQuantity;

    @Column(name = "quantity_unit_id", nullable = true, length = 80)
    private String quantityUnitId;

    @Column(name = "difference_percent", nullable = true, precision = 10, scale = 6)
    private BigDecimal differencePercent;

    @Column(name = "reconciled_by_actor_id", nullable = true, length = 80)
    private String reconciledByActorId;

    @Column(name = "reconciled_at", nullable = true)
    private Instant reconciledAt;

    @Column(name = "approved_by_actor_id", nullable = true, length = 80)
    private String approvedByActorId;

    @Column(name = "approved_at", nullable = true)
    private Instant approvedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected CustodyReconciliationJpaEntity() {
            // Required by JPA.
        }

        public CustodyReconciliationJpaEntity(
                String id,
            String reconciliationNumber,
            String measurementPeriodId,
            String agreementId,
            CustodyReconciliationStatus status,
            BigDecimal totalTicketQuantity,
            BigDecimal totalMeasuredQuantity,
            BigDecimal differenceQuantity,
            String quantityUnitId,
            BigDecimal differencePercent,
            String reconciledByActorId,
            Instant reconciledAt,
            String approvedByActorId,
            Instant approvedAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.reconciliationNumber = reconciliationNumber;
        this.measurementPeriodId = measurementPeriodId;
        this.agreementId = agreementId;
        this.status = status;
        this.totalTicketQuantity = totalTicketQuantity;
        this.totalMeasuredQuantity = totalMeasuredQuantity;
        this.differenceQuantity = differenceQuantity;
        this.quantityUnitId = quantityUnitId;
        this.differencePercent = differencePercent;
        this.reconciledByActorId = reconciledByActorId;
        this.reconciledAt = reconciledAt;
        this.approvedByActorId = approvedByActorId;
        this.approvedAt = approvedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String reconciliationNumber() {
        return reconciliationNumber;
    }


    public String measurementPeriodId() {
        return measurementPeriodId;
    }


    public String agreementId() {
        return agreementId;
    }


    public CustodyReconciliationStatus status() {
        return status;
    }


    public BigDecimal totalTicketQuantity() {
        return totalTicketQuantity;
    }


    public BigDecimal totalMeasuredQuantity() {
        return totalMeasuredQuantity;
    }


    public BigDecimal differenceQuantity() {
        return differenceQuantity;
    }


    public String quantityUnitId() {
        return quantityUnitId;
    }


    public BigDecimal differencePercent() {
        return differencePercent;
    }


    public String reconciledByActorId() {
        return reconciledByActorId;
    }


    public Instant reconciledAt() {
        return reconciledAt;
    }


    public String approvedByActorId() {
        return approvedByActorId;
    }


    public Instant approvedAt() {
        return approvedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
