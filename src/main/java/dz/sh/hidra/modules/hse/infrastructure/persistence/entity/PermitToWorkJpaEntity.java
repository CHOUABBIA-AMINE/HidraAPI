/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermitToWorkJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PermitToWork.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.entity;

import dz.sh.hidra.modules.hse.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for PermitToWork.
     */
    @Entity
    @Table(name = "hidra_hse_permit_to_work")
    public class PermitToWorkJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "permit_number", nullable = false, length = 80)
    private String permitNumber;

    @Column(name = "permit_type_id", nullable = false, length = 80)
    private String permitTypeId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "target_module", nullable = true, length = 80)
    private String targetModule;

    @Column(name = "target_type_code", nullable = true, length = 80)
    private String targetTypeCode;

    @Column(name = "target_id", nullable = true, length = 80)
    private String targetId;

    @Column(name = "requested_by_actor_id", nullable = true, length = 80)
    private String requestedByActorId;

    @Column(name = "approved_by_actor_id", nullable = true, length = 80)
    private String approvedByActorId;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PermitStatus status;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PermitToWorkJpaEntity() {
            // Required by JPA.
        }

        public PermitToWorkJpaEntity(
                String id,
            String permitNumber,
            String permitTypeId,
            String title,
            String description,
            String targetModule,
            String targetTypeCode,
            String targetId,
            String requestedByActorId,
            String approvedByActorId,
            Instant validFrom,
            Instant validTo,
            PermitStatus status,
            String workflowInstanceId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.permitNumber = permitNumber;
        this.permitTypeId = permitTypeId;
        this.title = title;
        this.description = description;
        this.targetModule = targetModule;
        this.targetTypeCode = targetTypeCode;
        this.targetId = targetId;
        this.requestedByActorId = requestedByActorId;
        this.approvedByActorId = approvedByActorId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = status;
        this.workflowInstanceId = workflowInstanceId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String permitNumber() {
        return permitNumber;
    }


    public String permitTypeId() {
        return permitTypeId;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public String targetModule() {
        return targetModule;
    }


    public String targetTypeCode() {
        return targetTypeCode;
    }


    public String targetId() {
        return targetId;
    }


    public String requestedByActorId() {
        return requestedByActorId;
    }


    public String approvedByActorId() {
        return approvedByActorId;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public PermitStatus status() {
        return status;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
