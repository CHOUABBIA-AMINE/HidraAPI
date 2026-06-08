/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : JPA entity for workflow tasks.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * JPA entity for workflow tasks.
 *
 * <p>Architecture role:
 * Persistence-only workflow representation. It must not be exposed to domain, application, or REST
 * layers and must not contain business behavior.
 */
@Entity
@Table(name = "hidra_workflow_task")
public class WorkflowTaskJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "instance_id", nullable = false)
    private String instanceId;

    @Column(name = "step_id", nullable = false)
    private String stepId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "assigned_actor_id", nullable = true)
    private String assignedActorId;

    @Column(name = "assigned_actor_username_snapshot", nullable = true)
    private String assignedActorUsernameSnapshot;

    @Column(name = "assigned_actor_display_name_snapshot", nullable = true)
    private String assignedActorDisplayNameSnapshot;

    @Column(name = "assigned_organization_unit_id", nullable = true)
    private String assignedOrganizationUnitId;

    @Column(name = "assigned_organization_unit_name_snapshot", nullable = true)
    private String assignedOrganizationUnitNameSnapshot;

    @Column(name = "assigned_role_code_snapshot", nullable = true)
    private String assignedRoleCodeSnapshot;

    @Column(name = "priority_id", nullable = true)
    private String priorityId;

    @Column(name = "due_at", nullable = true)
    private Instant dueAt;

    @Column(name = "claimed_by_actor_id", nullable = true)
    private String claimedByActorId;

    @Column(name = "claimed_by_username_snapshot", nullable = true)
    private String claimedByUsernameSnapshot;

    @Column(name = "claimed_by_display_name_snapshot", nullable = true)
    private String claimedByDisplayNameSnapshot;

    @Column(name = "claimed_by_role_code_snapshot", nullable = true)
    private String claimedByRoleCodeSnapshot;

    @Column(name = "claimed_at", nullable = true)
    private Instant claimedAt;

    @Column(name = "completed_by_actor_id", nullable = true)
    private String completedByActorId;

    @Column(name = "completed_by_username_snapshot", nullable = true)
    private String completedByUsernameSnapshot;

    @Column(name = "completed_by_display_name_snapshot", nullable = true)
    private String completedByDisplayNameSnapshot;

    @Column(name = "completed_by_role_code_snapshot", nullable = true)
    private String completedByRoleCodeSnapshot;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

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
            String status,
            String assignedActorId,
            String assignedActorUsernameSnapshot,
            String assignedActorDisplayNameSnapshot,
            String assignedOrganizationUnitId,
            String assignedOrganizationUnitNameSnapshot,
            String assignedRoleCodeSnapshot,
            String priorityId,
            Instant dueAt,
            String claimedByActorId,
            String claimedByUsernameSnapshot,
            String claimedByDisplayNameSnapshot,
            String claimedByRoleCodeSnapshot,
            Instant claimedAt,
            String completedByActorId,
            String completedByUsernameSnapshot,
            String completedByDisplayNameSnapshot,
            String completedByRoleCodeSnapshot,
            Instant completedAt,
            Instant createdAt,
            Instant updatedAt) {
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
        this.claimedByUsernameSnapshot = claimedByUsernameSnapshot;
        this.claimedByDisplayNameSnapshot = claimedByDisplayNameSnapshot;
        this.claimedByRoleCodeSnapshot = claimedByRoleCodeSnapshot;
        this.claimedAt = claimedAt;
        this.completedByActorId = completedByActorId;
        this.completedByUsernameSnapshot = completedByUsernameSnapshot;
        this.completedByDisplayNameSnapshot = completedByDisplayNameSnapshot;
        this.completedByRoleCodeSnapshot = completedByRoleCodeSnapshot;
        this.completedAt = completedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedActorId() {
        return assignedActorId;
    }

    public void setAssignedActorId(String assignedActorId) {
        this.assignedActorId = assignedActorId;
    }

    public String getAssignedActorUsernameSnapshot() {
        return assignedActorUsernameSnapshot;
    }

    public void setAssignedActorUsernameSnapshot(String assignedActorUsernameSnapshot) {
        this.assignedActorUsernameSnapshot = assignedActorUsernameSnapshot;
    }

    public String getAssignedActorDisplayNameSnapshot() {
        return assignedActorDisplayNameSnapshot;
    }

    public void setAssignedActorDisplayNameSnapshot(String assignedActorDisplayNameSnapshot) {
        this.assignedActorDisplayNameSnapshot = assignedActorDisplayNameSnapshot;
    }

    public String getAssignedOrganizationUnitId() {
        return assignedOrganizationUnitId;
    }

    public void setAssignedOrganizationUnitId(String assignedOrganizationUnitId) {
        this.assignedOrganizationUnitId = assignedOrganizationUnitId;
    }

    public String getAssignedOrganizationUnitNameSnapshot() {
        return assignedOrganizationUnitNameSnapshot;
    }

    public void setAssignedOrganizationUnitNameSnapshot(String assignedOrganizationUnitNameSnapshot) {
        this.assignedOrganizationUnitNameSnapshot = assignedOrganizationUnitNameSnapshot;
    }

    public String getAssignedRoleCodeSnapshot() {
        return assignedRoleCodeSnapshot;
    }

    public void setAssignedRoleCodeSnapshot(String assignedRoleCodeSnapshot) {
        this.assignedRoleCodeSnapshot = assignedRoleCodeSnapshot;
    }

    public String getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(String priorityId) {
        this.priorityId = priorityId;
    }

    public Instant getDueAt() {
        return dueAt;
    }

    public void setDueAt(Instant dueAt) {
        this.dueAt = dueAt;
    }

    public String getClaimedByActorId() {
        return claimedByActorId;
    }

    public void setClaimedByActorId(String claimedByActorId) {
        this.claimedByActorId = claimedByActorId;
    }

    public String getClaimedByUsernameSnapshot() {
        return claimedByUsernameSnapshot;
    }

    public void setClaimedByUsernameSnapshot(String claimedByUsernameSnapshot) {
        this.claimedByUsernameSnapshot = claimedByUsernameSnapshot;
    }

    public String getClaimedByDisplayNameSnapshot() {
        return claimedByDisplayNameSnapshot;
    }

    public void setClaimedByDisplayNameSnapshot(String claimedByDisplayNameSnapshot) {
        this.claimedByDisplayNameSnapshot = claimedByDisplayNameSnapshot;
    }

    public String getClaimedByRoleCodeSnapshot() {
        return claimedByRoleCodeSnapshot;
    }

    public void setClaimedByRoleCodeSnapshot(String claimedByRoleCodeSnapshot) {
        this.claimedByRoleCodeSnapshot = claimedByRoleCodeSnapshot;
    }

    public Instant getClaimedAt() {
        return claimedAt;
    }

    public void setClaimedAt(Instant claimedAt) {
        this.claimedAt = claimedAt;
    }

    public String getCompletedByActorId() {
        return completedByActorId;
    }

    public void setCompletedByActorId(String completedByActorId) {
        this.completedByActorId = completedByActorId;
    }

    public String getCompletedByUsernameSnapshot() {
        return completedByUsernameSnapshot;
    }

    public void setCompletedByUsernameSnapshot(String completedByUsernameSnapshot) {
        this.completedByUsernameSnapshot = completedByUsernameSnapshot;
    }

    public String getCompletedByDisplayNameSnapshot() {
        return completedByDisplayNameSnapshot;
    }

    public void setCompletedByDisplayNameSnapshot(String completedByDisplayNameSnapshot) {
        this.completedByDisplayNameSnapshot = completedByDisplayNameSnapshot;
    }

    public String getCompletedByRoleCodeSnapshot() {
        return completedByRoleCodeSnapshot;
    }

    public void setCompletedByRoleCodeSnapshot(String completedByRoleCodeSnapshot) {
        this.completedByRoleCodeSnapshot = completedByRoleCodeSnapshot;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
