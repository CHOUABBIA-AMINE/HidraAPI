/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStateHistoryJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : JPA entity for workflow state history.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * JPA entity for workflow state history.
 *
 * <p>Architecture role:
 * Persistence-only workflow representation. It must not be exposed to domain, application, or REST
 * layers and must not contain business behavior.
 */
@Entity
@Table(name = "hidra_workflow_state_history")
public class WorkflowStateHistoryJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "instance_id", nullable = false)
    private String instanceId;

    @Column(name = "task_id", nullable = true)
    private String taskId;

    @Column(name = "from_step_id", nullable = true)
    private String fromStepId;

    @Column(name = "to_step_id", nullable = true)
    private String toStepId;

    @Column(name = "from_status", nullable = true)
    private String fromStatus;

    @Column(name = "to_status", nullable = false)
    private String toStatus;

    @Column(name = "actor_id", nullable = false)
    private String actorId;

    @Column(name = "actor_username_snapshot", nullable = true)
    private String actorUsernameSnapshot;

    @Column(name = "actor_display_name_snapshot", nullable = false)
    private String actorDisplayNameSnapshot;

    @Column(name = "actor_role_code_snapshot", nullable = true)
    private String actorRoleCodeSnapshot;

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
            Instant changedAt) {
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
        this.changedAt = changedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFromStepId() {
        return fromStepId;
    }

    public void setFromStepId(String fromStepId) {
        this.fromStepId = fromStepId;
    }

    public String getToStepId() {
        return toStepId;
    }

    public void setToStepId(String toStepId) {
        this.toStepId = toStepId;
    }

    public String getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus;
    }

    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getActorUsernameSnapshot() {
        return actorUsernameSnapshot;
    }

    public void setActorUsernameSnapshot(String actorUsernameSnapshot) {
        this.actorUsernameSnapshot = actorUsernameSnapshot;
    }

    public String getActorDisplayNameSnapshot() {
        return actorDisplayNameSnapshot;
    }

    public void setActorDisplayNameSnapshot(String actorDisplayNameSnapshot) {
        this.actorDisplayNameSnapshot = actorDisplayNameSnapshot;
    }

    public String getActorRoleCodeSnapshot() {
        return actorRoleCodeSnapshot;
    }

    public void setActorRoleCodeSnapshot(String actorRoleCodeSnapshot) {
        this.actorRoleCodeSnapshot = actorRoleCodeSnapshot;
    }

    public Instant getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(Instant changedAt) {
        this.changedAt = changedAt;
    }
}
