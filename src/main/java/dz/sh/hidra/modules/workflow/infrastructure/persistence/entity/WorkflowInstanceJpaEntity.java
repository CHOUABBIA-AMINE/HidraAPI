/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for WorkflowInstance.
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
     * Database-backed JPA entity for WorkflowInstance.
     */
    @Entity
    @Table(name = "hidra_workflow_instance")
    public class WorkflowInstanceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "definition_id", nullable = false, length = 80)
    private String definitionId;

    @Column(name = "definition_version", nullable = false)
    private int definitionVersion;

    @Column(name = "workflow_purpose_id", nullable = true, length = 80)
    private String workflowPurposeId;

    @Column(name = "target_module", nullable = false, length = 80)
    private String targetModule;

    @Column(name = "target_type_id", nullable = false, length = 80)
    private String targetTypeId;

    @Column(name = "target_id", nullable = false, length = 120)
    private String targetId;

    @Column(name = "target_code_snapshot", nullable = true, length = 120)
    private String targetCodeSnapshot;

    @Column(name = "target_label_snapshot", nullable = true, length = 240)
    private String targetLabelSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private WorkflowInstanceStatus status;

    @Column(name = "current_step_id", nullable = true, length = 80)
    private String currentStepId;

    @Column(name = "started_by_actor_id", nullable = false, length = 80)
    private String startedByActorId;

    @Column(name = "started_by_username_snapshot", nullable = true, length = 120)
    private String startedByUsernameSnapshot;

    @Column(name = "started_by_display_name_snapshot", nullable = false, length = 160)
    private String startedByDisplayNameSnapshot;

    @Column(name = "started_by_role_code_snapshot", nullable = true, length = 80)
    private String startedByRoleCodeSnapshot;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "cancelled_at", nullable = true)
    private Instant cancelledAt;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected WorkflowInstanceJpaEntity() {
            // Required by JPA.
        }

        public WorkflowInstanceJpaEntity(
                String id,
            String definitionId,
            int definitionVersion,
            String workflowPurposeId,
            String targetModule,
            String targetTypeId,
            String targetId,
            String targetCodeSnapshot,
            String targetLabelSnapshot,
            WorkflowInstanceStatus status,
            String currentStepId,
            String startedByActorId,
            String startedByUsernameSnapshot,
            String startedByDisplayNameSnapshot,
            String startedByRoleCodeSnapshot,
            Instant startedAt,
            Instant completedAt,
            Instant cancelledAt,
            String correlationId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.definitionId = definitionId;
        this.definitionVersion = definitionVersion;
        this.workflowPurposeId = workflowPurposeId;
        this.targetModule = targetModule;
        this.targetTypeId = targetTypeId;
        this.targetId = targetId;
        this.targetCodeSnapshot = targetCodeSnapshot;
        this.targetLabelSnapshot = targetLabelSnapshot;
        this.status = status;
        this.currentStepId = currentStepId;
        this.startedByActorId = startedByActorId;
        this.startedByUsernameSnapshot = startedByUsernameSnapshot;
        this.startedByDisplayNameSnapshot = startedByDisplayNameSnapshot;
        this.startedByRoleCodeSnapshot = startedByRoleCodeSnapshot;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.cancelledAt = cancelledAt;
        this.correlationId = correlationId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String definitionId() {
        return definitionId;
    }


    public int definitionVersion() {
        return definitionVersion;
    }


    public String workflowPurposeId() {
        return workflowPurposeId;
    }


    public String targetModule() {
        return targetModule;
    }


    public String targetTypeId() {
        return targetTypeId;
    }


    public String targetId() {
        return targetId;
    }


    public String targetCodeSnapshot() {
        return targetCodeSnapshot;
    }


    public String targetLabelSnapshot() {
        return targetLabelSnapshot;
    }


    public WorkflowInstanceStatus status() {
        return status;
    }


    public String currentStepId() {
        return currentStepId;
    }


    public String startedByActorId() {
        return startedByActorId;
    }


    public String startedByUsernameSnapshot() {
        return startedByUsernameSnapshot;
    }


    public String startedByDisplayNameSnapshot() {
        return startedByDisplayNameSnapshot;
    }


    public String startedByRoleCodeSnapshot() {
        return startedByRoleCodeSnapshot;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public Instant cancelledAt() {
        return cancelledAt;
    }


    public String correlationId() {
        return correlationId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
