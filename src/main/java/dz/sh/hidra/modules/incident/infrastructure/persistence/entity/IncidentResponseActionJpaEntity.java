/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentResponseActionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IncidentResponseAction.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.entity;

import dz.sh.hidra.modules.incident.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for IncidentResponseAction.
     */
    @Entity
    @Table(name = "hidra_incident_response_action")
    public class IncidentResponseActionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "incident_id", nullable = false, length = 80)
    private String incidentId;

    @Column(name = "action_type_id", nullable = false, length = 80)
    private String actionTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_status", nullable = false, length = 40)
    private ResponseActionStatus actionStatus;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = true, length = 80)
    private ResponseTargetType targetType;

    @Column(name = "target_reference_id", nullable = true, length = 80)
    private String targetReferenceId;

    @Column(name = "target_reference_code", nullable = true, length = 160)
    private String targetReferenceCode;

    @Column(name = "planned_start_at", nullable = true)
    private Instant plannedStartAt;

    @Column(name = "planned_end_at", nullable = true)
    private Instant plannedEndAt;

    @Column(name = "started_at", nullable = true)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "performed_by_actor_id", nullable = true, length = 80)
    private String performedByActorId;

    @Column(name = "performed_by_actor_name_snapshot", nullable = true, length = 255)
    private String performedByActorNameSnapshot;

    @Column(name = "organization_unit_id", nullable = true, length = 80)
    private String organizationUnitId;

    @Column(name = "result_summary", nullable = true, columnDefinition = "text")
    private String resultSummary;

    @Column(name = "failure_reason", nullable = true, columnDefinition = "text")
    private String failureReason;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected IncidentResponseActionJpaEntity() {
            // Required by JPA.
        }

        public IncidentResponseActionJpaEntity(
                String id,
            String incidentId,
            String actionTypeId,
            ResponseActionStatus actionStatus,
            String description,
            ResponseTargetType targetType,
            String targetReferenceId,
            String targetReferenceCode,
            Instant plannedStartAt,
            Instant plannedEndAt,
            Instant startedAt,
            Instant completedAt,
            String performedByActorId,
            String performedByActorNameSnapshot,
            String organizationUnitId,
            String resultSummary,
            String failureReason,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.incidentId = incidentId;
        this.actionTypeId = actionTypeId;
        this.actionStatus = actionStatus;
        this.description = description;
        this.targetType = targetType;
        this.targetReferenceId = targetReferenceId;
        this.targetReferenceCode = targetReferenceCode;
        this.plannedStartAt = plannedStartAt;
        this.plannedEndAt = plannedEndAt;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.performedByActorId = performedByActorId;
        this.performedByActorNameSnapshot = performedByActorNameSnapshot;
        this.organizationUnitId = organizationUnitId;
        this.resultSummary = resultSummary;
        this.failureReason = failureReason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String incidentId() {
        return incidentId;
    }


    public String actionTypeId() {
        return actionTypeId;
    }


    public ResponseActionStatus actionStatus() {
        return actionStatus;
    }


    public String description() {
        return description;
    }


    public ResponseTargetType targetType() {
        return targetType;
    }


    public String targetReferenceId() {
        return targetReferenceId;
    }


    public String targetReferenceCode() {
        return targetReferenceCode;
    }


    public Instant plannedStartAt() {
        return plannedStartAt;
    }


    public Instant plannedEndAt() {
        return plannedEndAt;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public String performedByActorId() {
        return performedByActorId;
    }


    public String performedByActorNameSnapshot() {
        return performedByActorNameSnapshot;
    }


    public String organizationUnitId() {
        return organizationUnitId;
    }


    public String resultSummary() {
        return resultSummary;
    }


    public String failureReason() {
        return failureReason;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
