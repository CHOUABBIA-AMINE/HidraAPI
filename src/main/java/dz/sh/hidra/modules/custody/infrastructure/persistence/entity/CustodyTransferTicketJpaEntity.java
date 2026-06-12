/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTransferTicketJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyTransferTicket.
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
import java.time.Instant;

    /**
     * Database-backed JPA entity for CustodyTransferTicket.
     */
    @Entity
    @Table(name = "hidra_custody_transfer_ticket")
    public class CustodyTransferTicketJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "ticket_number", nullable = false, length = 80)
    private String ticketNumber;

    @Column(name = "measurement_period_id", nullable = false, length = 80)
    private String measurementPeriodId;

    @Column(name = "agreement_id", nullable = false, length = 80)
    private String agreementId;

    @Column(name = "transfer_point_id", nullable = false, length = 80)
    private String transferPointId;

    @Column(name = "batch_id", nullable = true, length = 80)
    private String batchId;

    @Column(name = "quantity_calculation_id", nullable = true, length = 80)
    private String quantityCalculationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private CustodyTicketStatus status;

    @Column(name = "ticket_date", nullable = false)
    private Instant ticketDate;

    @Column(name = "issued_by_actor_id", nullable = true, length = 80)
    private String issuedByActorId;

    @Column(name = "approved_by_actor_id", nullable = true, length = 80)
    private String approvedByActorId;

    @Column(name = "approved_at", nullable = true)
    private Instant approvedAt;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

    @Column(name = "audit_reference_id", nullable = true, length = 80)
    private String auditReferenceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected CustodyTransferTicketJpaEntity() {
            // Required by JPA.
        }

        public CustodyTransferTicketJpaEntity(
                String id,
            String ticketNumber,
            String measurementPeriodId,
            String agreementId,
            String transferPointId,
            String batchId,
            String quantityCalculationId,
            CustodyTicketStatus status,
            Instant ticketDate,
            String issuedByActorId,
            String approvedByActorId,
            Instant approvedAt,
            String workflowInstanceId,
            String auditReferenceId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.ticketNumber = ticketNumber;
        this.measurementPeriodId = measurementPeriodId;
        this.agreementId = agreementId;
        this.transferPointId = transferPointId;
        this.batchId = batchId;
        this.quantityCalculationId = quantityCalculationId;
        this.status = status;
        this.ticketDate = ticketDate;
        this.issuedByActorId = issuedByActorId;
        this.approvedByActorId = approvedByActorId;
        this.approvedAt = approvedAt;
        this.workflowInstanceId = workflowInstanceId;
        this.auditReferenceId = auditReferenceId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String ticketNumber() {
        return ticketNumber;
    }


    public String measurementPeriodId() {
        return measurementPeriodId;
    }


    public String agreementId() {
        return agreementId;
    }


    public String transferPointId() {
        return transferPointId;
    }


    public String batchId() {
        return batchId;
    }


    public String quantityCalculationId() {
        return quantityCalculationId;
    }


    public CustodyTicketStatus status() {
        return status;
    }


    public Instant ticketDate() {
        return ticketDate;
    }


    public String issuedByActorId() {
        return issuedByActorId;
    }


    public String approvedByActorId() {
        return approvedByActorId;
    }


    public Instant approvedAt() {
        return approvedAt;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }


    public String auditReferenceId() {
        return auditReferenceId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
