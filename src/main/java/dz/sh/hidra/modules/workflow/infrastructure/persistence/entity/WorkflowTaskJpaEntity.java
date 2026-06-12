/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for WorkflowTask.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import dz.sh.hidra.modules.workflow.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for WorkflowTask.
     */
    @Entity
    @Table(name = "hidra_workflow_task")
    public class WorkflowTaskJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "instance_id", nullable = false, length = 80)
    private String instanceId;

    @Column(name = "step_id", nullable = false, length = 80)
    private String stepId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private WorkflowTaskStatus status;

    @Column(name = "assigned_actor_id", nullable = true, length = 80)
    private String assignedActorId;

    @Column(name = "assigned_actor_username_snapshot", nullable = true, length = 120)
    private String assignedActorUsernameSnapshot;

    @Column(name = "assigned_actor_display_name_snapshot", nullable = true, length = 160)
    private String assignedActorDisplayNameSnapshot;

    @Column(name = "assigned_organization_unit_id", nullable = true, length = 80)
    private String assignedOrganizationUnitId;

    @Column(name = "assigned_organization_unit_name_snapshot", nullable = true, length = 160)
    private String assignedOrganizationUnitNameSnapshot;

    @Column(name = "assigned_role_code_snapshot", nullable = true, length = 80)
    private String assignedRoleCodeSnapshot;

    @Column(name = "priority_id", nullable = true, length = 80)
    private String priorityId;

    @Column(name = "due_at", nullable = true)
    private Instant dueAt;

    @Column(name = "claimed_by_actor_id", nullable = true, length = 80)
    private String claimedByActorId;

    @Column(name = "claimed_at", nullable = true)
    private Instant claimedAt;

    @Column(name = "completed_by_actor_id", nullable = true, length = 80)
    private String completedByActorId;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "assignment_mode_id", nullable = true, length = 80)
    private String assignmentModeId;

    @Column(name = "task_label_snapshot", nullable = true, length = 240)
    private String taskLabelSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "sla_status", nullable = true, length = 40)
    private WorkflowSlaStatus slaStatus;

    @Column(name = "escalated_at", nullable = true)
    private Instant escalatedAt;

    @Column(name = "delegated_at", nullable = true)
    private Instant delegatedAt;

    @Column(name = "expires_at", nullable = true)
    private Instant expiresAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected WorkflowTaskJpaEntity() {
            // Required by JPA.
        }

        public WorkflowTaskJpaEntity(
                String id,
            String instanceId,
            String stepId,
            WorkflowTaskStatus status,
            String assignedActorId,
            String assignedActorUsernameSnapshot,
            String assignedActorDisplayNameSnapshot,
            String assignedOrganizationUnitId,
            String assignedOrganizationUnitNameSnapshot,
            String assignedRoleCodeSnapshot,
            String priorityId,
            Instant dueAt,
            String claimedByActorId,
            Instant claimedAt,
            String completedByActorId,
            Instant completedAt,
            String assignmentModeId,
            String taskLabelSnapshot,
            WorkflowSlaStatus slaStatus,
            Instant escalatedAt,
            Instant delegatedAt,
            Instant expiresAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.instanceId = instanceId;
        this.stepId = stepId;
        this.status = status;
        this.assignedActorId = assignedActorId;
        this.assignedActorUsernameSnapshot = assignedActorUsernameSnapshot;
        this.assignedActorDisplayNameSnapshot = assignedActorDisplayNameSnapshot;
        this.assignedOrganizationUnitId = assignedOrganizationUnitId;
        this.assignedOrganizationUnitNameSnapshot = assignedOrganizationUnitNameSnapshot;
        this.assignedRoleCodeSnapshot = assignedRoleCodeSnapshot;
        this.priorityId = priorityId;
        this.dueAt = dueAt;
        this.claimedByActorId = claimedByActorId;
        this.claimedAt = claimedAt;
        this.completedByActorId = completedByActorId;
        this.completedAt = completedAt;
        this.assignmentModeId = assignmentModeId;
        this.taskLabelSnapshot = taskLabelSnapshot;
        this.slaStatus = slaStatus;
        this.escalatedAt = escalatedAt;
        this.delegatedAt = delegatedAt;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String instanceId() {
        return instanceId;
    }


    public String stepId() {
        return stepId;
    }


    public WorkflowTaskStatus status() {
        return status;
    }


    public String assignedActorId() {
        return assignedActorId;
    }


    public String assignedActorUsernameSnapshot() {
        return assignedActorUsernameSnapshot;
    }


    public String assignedActorDisplayNameSnapshot() {
        return assignedActorDisplayNameSnapshot;
    }


    public String assignedOrganizationUnitId() {
        return assignedOrganizationUnitId;
    }


    public String assignedOrganizationUnitNameSnapshot() {
        return assignedOrganizationUnitNameSnapshot;
    }


    public String assignedRoleCodeSnapshot() {
        return assignedRoleCodeSnapshot;
    }


    public String priorityId() {
        return priorityId;
    }


    public Instant dueAt() {
        return dueAt;
    }


    public String claimedByActorId() {
        return claimedByActorId;
    }


    public Instant claimedAt() {
        return claimedAt;
    }


    public String completedByActorId() {
        return completedByActorId;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public String assignmentModeId() {
        return assignmentModeId;
    }


    public String taskLabelSnapshot() {
        return taskLabelSnapshot;
    }


    public WorkflowSlaStatus slaStatus() {
        return slaStatus;
    }


    public Instant escalatedAt() {
        return escalatedAt;
    }


    public Instant delegatedAt() {
        return delegatedAt;
    }


    public Instant expiresAt() {
        return expiresAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
