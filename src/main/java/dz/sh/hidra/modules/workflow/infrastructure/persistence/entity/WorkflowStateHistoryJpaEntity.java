/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStateHistoryJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for WorkflowStateHistory.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for WorkflowStateHistory.
     */
    @Entity
    @Table(name = "hidra_workflow_state_history")
    public class WorkflowStateHistoryJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "instance_id", nullable = false, length = 80)
    private String instanceId;

    @Column(name = "task_id", nullable = true, length = 80)
    private String taskId;

    @Column(name = "from_step_id", nullable = true, length = 80)
    private String fromStepId;

    @Column(name = "to_step_id", nullable = true, length = 80)
    private String toStepId;

    @Column(name = "from_status", nullable = true, length = 40)
    private String fromStatus;

    @Column(name = "to_status", nullable = false, length = 40)
    private String toStatus;

    @Column(name = "actor_id", nullable = false, length = 80)
    private String actorId;

    @Column(name = "actor_username_snapshot", nullable = true, length = 120)
    private String actorUsernameSnapshot;

    @Column(name = "actor_display_name_snapshot", nullable = false, length = 160)
    private String actorDisplayNameSnapshot;

    @Column(name = "actor_role_code_snapshot", nullable = true, length = 80)
    private String actorRoleCodeSnapshot;

    @Column(name = "action_id", nullable = true, length = 80)
    private String actionId;

    @Column(name = "reason_id", nullable = true, length = 80)
    private String reasonId;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "changed_at", nullable = false)
    private Instant changedAt;

        protected WorkflowStateHistoryJpaEntity() {
            // Required by JPA.
        }

        public WorkflowStateHistoryJpaEntity(
                String id,
            String instanceId,
            String taskId,
            String fromStepId,
            String toStepId,
            String fromStatus,
            String toStatus,
            String actorId,
            String actorUsernameSnapshot,
            String actorDisplayNameSnapshot,
            String actorRoleCodeSnapshot,
            String actionId,
            String reasonId,
            String correlationId,
            Instant changedAt
        ) {
            this.id = id;
        this.instanceId = instanceId;
        this.taskId = taskId;
        this.fromStepId = fromStepId;
        this.toStepId = toStepId;
        this.fromStatus = fromStatus;
        this.toStatus = toStatus;
        this.actorId = actorId;
        this.actorUsernameSnapshot = actorUsernameSnapshot;
        this.actorDisplayNameSnapshot = actorDisplayNameSnapshot;
        this.actorRoleCodeSnapshot = actorRoleCodeSnapshot;
        this.actionId = actionId;
        this.reasonId = reasonId;
        this.correlationId = correlationId;
        this.changedAt = changedAt;
        }


    public String id() {
        return id;
    }


    public String instanceId() {
        return instanceId;
    }


    public String taskId() {
        return taskId;
    }


    public String fromStepId() {
        return fromStepId;
    }


    public String toStepId() {
        return toStepId;
    }


    public String fromStatus() {
        return fromStatus;
    }


    public String toStatus() {
        return toStatus;
    }


    public String actorId() {
        return actorId;
    }


    public String actorUsernameSnapshot() {
        return actorUsernameSnapshot;
    }


    public String actorDisplayNameSnapshot() {
        return actorDisplayNameSnapshot;
    }


    public String actorRoleCodeSnapshot() {
        return actorRoleCodeSnapshot;
    }


    public String actionId() {
        return actionId;
    }


    public String reasonId() {
        return reasonId;
    }


    public String correlationId() {
        return correlationId;
    }


    public Instant changedAt() {
        return changedAt;
    }

    }
