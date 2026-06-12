/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyBatchJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyBatch.
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
     * Database-backed JPA entity for CustodyBatch.
     */
    @Entity
    @Table(name = "hidra_custody_batch")
    public class CustodyBatchJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "batch_number", nullable = false, length = 80)
    private String batchNumber;

    @Column(name = "measurement_period_id", nullable = false, length = 80)
    private String measurementPeriodId;

    @Column(name = "agreement_id", nullable = false, length = 80)
    private String agreementId;

    @Column(name = "product_type_id", nullable = false, length = 80)
    private String productTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private CustodyBatchStatus status;

    @Column(name = "batch_start", nullable = true)
    private Instant batchStart;

    @Column(name = "batch_end", nullable = true)
    private Instant batchEnd;

    @Column(name = "expected_quantity", nullable = true, precision = 18, scale = 6)
    private BigDecimal expectedQuantity;

    @Column(name = "expected_quantity_unit_id", nullable = true, length = 80)
    private String expectedQuantityUnitId;

    @Column(name = "source_plan_target_id", nullable = true, length = 80)
    private String sourcePlanTargetId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected CustodyBatchJpaEntity() {
            // Required by JPA.
        }

        public CustodyBatchJpaEntity(
                String id,
            String batchNumber,
            String measurementPeriodId,
            String agreementId,
            String productTypeId,
            CustodyBatchStatus status,
            Instant batchStart,
            Instant batchEnd,
            BigDecimal expectedQuantity,
            String expectedQuantityUnitId,
            String sourcePlanTargetId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.batchNumber = batchNumber;
        this.measurementPeriodId = measurementPeriodId;
        this.agreementId = agreementId;
        this.productTypeId = productTypeId;
        this.status = status;
        this.batchStart = batchStart;
        this.batchEnd = batchEnd;
        this.expectedQuantity = expectedQuantity;
        this.expectedQuantityUnitId = expectedQuantityUnitId;
        this.sourcePlanTargetId = sourcePlanTargetId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String batchNumber() {
        return batchNumber;
    }


    public String measurementPeriodId() {
        return measurementPeriodId;
    }


    public String agreementId() {
        return agreementId;
    }


    public String productTypeId() {
        return productTypeId;
    }


    public CustodyBatchStatus status() {
        return status;
    }


    public Instant batchStart() {
        return batchStart;
    }


    public Instant batchEnd() {
        return batchEnd;
    }


    public BigDecimal expectedQuantity() {
        return expectedQuantity;
    }


    public String expectedQuantityUnitId() {
        return expectedQuantityUnitId;
    }


    public String sourcePlanTargetId() {
        return sourcePlanTargetId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
