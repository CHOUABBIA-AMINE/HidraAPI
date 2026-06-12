/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCorrectivePreventiveActionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for HseCorrectivePreventiveAction.
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
     * Database-backed JPA entity for HseCorrectivePreventiveAction.
     */
    @Entity
    @Table(name = "hidra_hse_capa")
    public class HseCorrectivePreventiveActionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "hse_case_id", nullable = false, length = 80)
    private String hseCaseId;

    @Column(name = "action_number", nullable = false, length = 80)
    private String actionNumber;

    @Column(name = "action_type_id", nullable = false, length = 80)
    private String actionTypeId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "owner_actor_id", nullable = true, length = 80)
    private String ownerActorId;

    @Column(name = "owner_display_name_snapshot", nullable = true, length = 255)
    private String ownerDisplayNameSnapshot;

    @Column(name = "owner_organization_unit_id", nullable = true, length = 80)
    private String ownerOrganizationUnitId;

    @Column(name = "owner_organization_unit_name_snapshot", nullable = true, length = 500)
    private String ownerOrganizationUnitNameSnapshot;

    @Column(name = "target_date", nullable = true)
    private Instant targetDate;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "verification_required", nullable = false)
    private boolean verificationRequired;

    @Column(name = "verified_by_actor_id", nullable = true, length = 80)
    private String verifiedByActorId;

    @Column(name = "verified_at", nullable = true)
    private Instant verifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private CapaStatus status;

    @Column(name = "linked_work_order_id", nullable = true, length = 80)
    private String linkedWorkOrderId;

    @Column(name = "workflow_task_id", nullable = true, length = 80)
    private String workflowTaskId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected HseCorrectivePreventiveActionJpaEntity() {
            // Required by JPA.
        }

        public HseCorrectivePreventiveActionJpaEntity(
                String id,
            String hseCaseId,
            String actionNumber,
            String actionTypeId,
            String title,
            String description,
            String ownerActorId,
            String ownerDisplayNameSnapshot,
            String ownerOrganizationUnitId,
            String ownerOrganizationUnitNameSnapshot,
            Instant targetDate,
            Instant completedAt,
            boolean verificationRequired,
            String verifiedByActorId,
            Instant verifiedAt,
            CapaStatus status,
            String linkedWorkOrderId,
            String workflowTaskId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.hseCaseId = hseCaseId;
        this.actionNumber = actionNumber;
        this.actionTypeId = actionTypeId;
        this.title = title;
        this.description = description;
        this.ownerActorId = ownerActorId;
        this.ownerDisplayNameSnapshot = ownerDisplayNameSnapshot;
        this.ownerOrganizationUnitId = ownerOrganizationUnitId;
        this.ownerOrganizationUnitNameSnapshot = ownerOrganizationUnitNameSnapshot;
        this.targetDate = targetDate;
        this.completedAt = completedAt;
        this.verificationRequired = verificationRequired;
        this.verifiedByActorId = verifiedByActorId;
        this.verifiedAt = verifiedAt;
        this.status = status;
        this.linkedWorkOrderId = linkedWorkOrderId;
        this.workflowTaskId = workflowTaskId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String hseCaseId() {
        return hseCaseId;
    }


    public String actionNumber() {
        return actionNumber;
    }


    public String actionTypeId() {
        return actionTypeId;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public String ownerActorId() {
        return ownerActorId;
    }


    public String ownerDisplayNameSnapshot() {
        return ownerDisplayNameSnapshot;
    }


    public String ownerOrganizationUnitId() {
        return ownerOrganizationUnitId;
    }


    public String ownerOrganizationUnitNameSnapshot() {
        return ownerOrganizationUnitNameSnapshot;
    }


    public Instant targetDate() {
        return targetDate;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public boolean verificationRequired() {
        return verificationRequired;
    }


    public String verifiedByActorId() {
        return verifiedByActorId;
    }


    public Instant verifiedAt() {
        return verifiedAt;
    }


    public CapaStatus status() {
        return status;
    }


    public String linkedWorkOrderId() {
        return linkedWorkOrderId;
    }


    public String workflowTaskId() {
        return workflowTaskId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
