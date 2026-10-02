/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyDiscrepancyJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyDiscrepancy.
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
     * Database-backed JPA entity for CustodyDiscrepancy.
     */
    @Entity
    @Table(name = "hidra_custody_discrepancy")
    public class CustodyDiscrepancyJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "discrepancy_number", nullable = false, length = 80)
    private String discrepancyNumber;

    @Column(name = "reconciliation_id", nullable = false, length = 80)
    private String reconciliationId;

    @Column(name = "discrepancy_type_id", nullable = false, length = 80)
    private String discrepancyTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private CustodyDiscrepancyStatus status;

    @Column(name = "difference_quantity", nullable = true, precision = 18, scale = 6)
    private BigDecimal differenceQuantity;

    @Column(name = "quantity_unit_id", nullable = true, length = 80)
    private String quantityUnitId;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "root_cause_text", nullable = true, columnDefinition = "text")
    private String rootCauseText;

    @Column(name = "resolution_text", nullable = true, columnDefinition = "text")
    private String resolutionText;

    @Column(name = "assigned_actor_id", nullable = true, length = 80)
    private String assignedActorId;

    @Column(name = "opened_at", nullable = false)
    private Instant openedAt;

    @Column(name = "resolved_at", nullable = true)
    private Instant resolvedAt;

    @Column(name = "closed_at", nullable = true)
    private Instant closedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected CustodyDiscrepancyJpaEntity() {
            // Required by JPA.
        }

        public CustodyDiscrepancyJpaEntity(
                String id,
            String discrepancyNumber,
            String reconciliationId,
            String discrepancyTypeId,
            CustodyDiscrepancyStatus status,
            BigDecimal differenceQuantity,
            String quantityUnitId,
            String description,
            String rootCauseText,
            String resolutionText,
            String assignedActorId,
            Instant openedAt,
            Instant resolvedAt,
            Instant closedAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.discrepancyNumber = discrepancyNumber;
        this.reconciliationId = reconciliationId;
        this.discrepancyTypeId = discrepancyTypeId;
        this.status = status;
        this.differenceQuantity = differenceQuantity;
        this.quantityUnitId = quantityUnitId;
        this.description = description;
        this.rootCauseText = rootCauseText;
        this.resolutionText = resolutionText;
        this.assignedActorId = assignedActorId;
        this.openedAt = openedAt;
        this.resolvedAt = resolvedAt;
        this.closedAt = closedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String discrepancyNumber() {
        return discrepancyNumber;
    }


    public String reconciliationId() {
        return reconciliationId;
    }


    public String discrepancyTypeId() {
        return discrepancyTypeId;
    }


    public CustodyDiscrepancyStatus status() {
        return status;
    }


    public BigDecimal differenceQuantity() {
        return differenceQuantity;
    }


    public String quantityUnitId() {
        return quantityUnitId;
    }


    public String description() {
        return description;
    }


    public String rootCauseText() {
        return rootCauseText;
    }


    public String resolutionText() {
        return resolutionText;
    }


    public String assignedActorId() {
        return assignedActorId;
    }


    public Instant openedAt() {
        return openedAt;
    }


    public Instant resolvedAt() {
        return resolvedAt;
    }


    public Instant closedAt() {
        return closedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
