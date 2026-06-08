/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDelegationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : JPA entity for workflow delegations.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * JPA entity for workflow delegations.
 *
 * <p>Architecture role:
 * Persistence-only workflow representation. It must not be exposed to domain, application, or REST
 * layers and must not contain business behavior.
 */
@Entity
@Table(name = "hidra_workflow_delegation")
public class WorkflowDelegationJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "task_id", nullable = false)
    private String taskId;

    @Column(name = "from_actor_id", nullable = false)
    private String fromActorId;

    @Column(name = "from_actor_username_snapshot", nullable = true)
    private String fromActorUsernameSnapshot;

    @Column(name = "from_actor_display_name_snapshot", nullable = false)
    private String fromActorDisplayNameSnapshot;

    @Column(name = "from_actor_role_code_snapshot", nullable = true)
    private String fromActorRoleCodeSnapshot;

    @Column(name = "to_actor_id", nullable = true)
    private String toActorId;

    @Column(name = "to_actor_username_snapshot", nullable = true)
    private String toActorUsernameSnapshot;

    @Column(name = "to_actor_display_name_snapshot", nullable = true)
    private String toActorDisplayNameSnapshot;

    @Column(name = "to_actor_role_code_snapshot", nullable = true)
    private String toActorRoleCodeSnapshot;

    @Column(name = "to_organization_unit_id", nullable = true)
    private String toOrganizationUnitId;

    @Column(name = "to_organization_unit_name_snapshot", nullable = true)
    private String toOrganizationUnitNameSnapshot;

    @Column(name = "to_organization_role_code_snapshot", nullable = true)
    private String toOrganizationRoleCodeSnapshot;

    @Column(name = "reason_id", nullable = false)
    private String reasonId;

    @Column(name = "delegated_at", nullable = false)
    private Instant delegatedAt;

    protected WorkflowDelegationJpaEntity() {
        // Required by JPA.
    }

    public WorkflowDelegationJpaEntity(
            String id,
            String taskId,
            String fromActorId,
            String fromActorUsernameSnapshot,
            String fromActorDisplayNameSnapshot,
            String fromActorRoleCodeSnapshot,
            String toActorId,
            String toActorUsernameSnapshot,
            String toActorDisplayNameSnapshot,
            String toActorRoleCodeSnapshot,
            String toOrganizationUnitId,
            String toOrganizationUnitNameSnapshot,
            String toOrganizationRoleCodeSnapshot,
            String reasonId,
            Instant delegatedAt) {
        this.id = id;
        this.taskId = taskId;
        this.fromActorId = fromActorId;
        this.fromActorUsernameSnapshot = fromActorUsernameSnapshot;
        this.fromActorDisplayNameSnapshot = fromActorDisplayNameSnapshot;
        this.fromActorRoleCodeSnapshot = fromActorRoleCodeSnapshot;
        this.toActorId = toActorId;
        this.toActorUsernameSnapshot = toActorUsernameSnapshot;
        this.toActorDisplayNameSnapshot = toActorDisplayNameSnapshot;
        this.toActorRoleCodeSnapshot = toActorRoleCodeSnapshot;
        this.toOrganizationUnitId = toOrganizationUnitId;
        this.toOrganizationUnitNameSnapshot = toOrganizationUnitNameSnapshot;
        this.toOrganizationRoleCodeSnapshot = toOrganizationRoleCodeSnapshot;
        this.reasonId = reasonId;
        this.delegatedAt = delegatedAt;
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

    public String getFromActorId() {
        return fromActorId;
    }

    public void setFromActorId(String fromActorId) {
        this.fromActorId = fromActorId;
    }

    public String getFromActorUsernameSnapshot() {
        return fromActorUsernameSnapshot;
    }

    public void setFromActorUsernameSnapshot(String fromActorUsernameSnapshot) {
        this.fromActorUsernameSnapshot = fromActorUsernameSnapshot;
    }

    public String getFromActorDisplayNameSnapshot() {
        return fromActorDisplayNameSnapshot;
    }

    public void setFromActorDisplayNameSnapshot(String fromActorDisplayNameSnapshot) {
        this.fromActorDisplayNameSnapshot = fromActorDisplayNameSnapshot;
    }

    public String getFromActorRoleCodeSnapshot() {
        return fromActorRoleCodeSnapshot;
    }

    public void setFromActorRoleCodeSnapshot(String fromActorRoleCodeSnapshot) {
        this.fromActorRoleCodeSnapshot = fromActorRoleCodeSnapshot;
    }

    public String getToActorId() {
        return toActorId;
    }

    public void setToActorId(String toActorId) {
        this.toActorId = toActorId;
    }

    public String getToActorUsernameSnapshot() {
        return toActorUsernameSnapshot;
    }

    public void setToActorUsernameSnapshot(String toActorUsernameSnapshot) {
        this.toActorUsernameSnapshot = toActorUsernameSnapshot;
    }

    public String getToActorDisplayNameSnapshot() {
        return toActorDisplayNameSnapshot;
    }

    public void setToActorDisplayNameSnapshot(String toActorDisplayNameSnapshot) {
        this.toActorDisplayNameSnapshot = toActorDisplayNameSnapshot;
    }

    public String getToActorRoleCodeSnapshot() {
        return toActorRoleCodeSnapshot;
    }

    public void setToActorRoleCodeSnapshot(String toActorRoleCodeSnapshot) {
        this.toActorRoleCodeSnapshot = toActorRoleCodeSnapshot;
    }

    public String getToOrganizationUnitId() {
        return toOrganizationUnitId;
    }

    public void setToOrganizationUnitId(String toOrganizationUnitId) {
        this.toOrganizationUnitId = toOrganizationUnitId;
    }

    public String getToOrganizationUnitNameSnapshot() {
        return toOrganizationUnitNameSnapshot;
    }

    public void setToOrganizationUnitNameSnapshot(String toOrganizationUnitNameSnapshot) {
        this.toOrganizationUnitNameSnapshot = toOrganizationUnitNameSnapshot;
    }

    public String getToOrganizationRoleCodeSnapshot() {
        return toOrganizationRoleCodeSnapshot;
    }

    public void setToOrganizationRoleCodeSnapshot(String toOrganizationRoleCodeSnapshot) {
        this.toOrganizationRoleCodeSnapshot = toOrganizationRoleCodeSnapshot;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public Instant getDelegatedAt() {
        return delegatedAt;
    }

    public void setDelegatedAt(Instant delegatedAt) {
        this.delegatedAt = delegatedAt;
    }
}
