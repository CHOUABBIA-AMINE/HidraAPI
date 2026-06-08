/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowAssignmentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : JPA entity for workflow task assignments.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * JPA entity for workflow task assignments.
 *
 * <p>Architecture role:
 * Persistence-only workflow representation. It must not be exposed to domain, application, or REST
 * layers and must not contain business behavior.
 */
@Entity
@Table(name = "hidra_workflow_assignment")
public class WorkflowAssignmentJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "task_id", nullable = false)
    private String taskId;

    @Column(name = "actor_id", nullable = true)
    private String actorId;

    @Column(name = "actor_username_snapshot", nullable = true)
    private String actorUsernameSnapshot;

    @Column(name = "actor_display_name_snapshot", nullable = true)
    private String actorDisplayNameSnapshot;

    @Column(name = "role_code_snapshot", nullable = true)
    private String roleCodeSnapshot;

    @Column(name = "organization_unit_id", nullable = true)
    private String organizationUnitId;

    @Column(name = "organization_unit_name_snapshot", nullable = true)
    private String organizationUnitNameSnapshot;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected WorkflowAssignmentJpaEntity() {
        // Required by JPA.
    }

    public WorkflowAssignmentJpaEntity(
            String id,
            String taskId,
            String actorId,
            String actorUsernameSnapshot,
            String actorDisplayNameSnapshot,
            String roleCodeSnapshot,
            String organizationUnitId,
            String organizationUnitNameSnapshot,
            String status,
            Instant assignedAt,
            Instant updatedAt) {
        this.id = id;
        this.taskId = taskId;
        this.actorId = actorId;
        this.actorUsernameSnapshot = actorUsernameSnapshot;
        this.actorDisplayNameSnapshot = actorDisplayNameSnapshot;
        this.roleCodeSnapshot = roleCodeSnapshot;
        this.organizationUnitId = organizationUnitId;
        this.organizationUnitNameSnapshot = organizationUnitNameSnapshot;
        this.status = status;
        this.assignedAt = assignedAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getRoleCodeSnapshot() {
        return roleCodeSnapshot;
    }

    public void setRoleCodeSnapshot(String roleCodeSnapshot) {
        this.roleCodeSnapshot = roleCodeSnapshot;
    }

    public String getOrganizationUnitId() {
        return organizationUnitId;
    }

    public void setOrganizationUnitId(String organizationUnitId) {
        this.organizationUnitId = organizationUnitId;
    }

    public String getOrganizationUnitNameSnapshot() {
        return organizationUnitNameSnapshot;
    }

    public void setOrganizationUnitNameSnapshot(String organizationUnitNameSnapshot) {
        this.organizationUnitNameSnapshot = organizationUnitNameSnapshot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Instant assignedAt) {
        this.assignedAt = assignedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
