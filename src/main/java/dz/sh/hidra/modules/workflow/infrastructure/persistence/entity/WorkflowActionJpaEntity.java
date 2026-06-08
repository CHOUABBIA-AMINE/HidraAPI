/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : JPA entity for workflow actions.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * JPA entity for workflow actions.
 *
 * <p>Architecture role:
 * Persistence-only workflow representation. It must not be exposed to domain, application, or REST
 * layers and must not contain business behavior.
 */
@Entity
@Table(name = "hidra_workflow_action")
public class WorkflowActionJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "instance_id", nullable = false)
    private String instanceId;

    @Column(name = "task_id", nullable = true)
    private String taskId;

    @Column(name = "action_type", nullable = false)
    private String actionType;

    @Column(name = "decision", nullable = true)
    private String decision;

    @Column(name = "reason_id", nullable = true)
    private String reasonId;

    @Column(name = "decision_note", nullable = true)
    private String decisionNote;

    @Column(name = "comment_text", nullable = true)
    private String commentText;

    @Column(name = "actor_id", nullable = false)
    private String actorId;

    @Column(name = "actor_username_snapshot", nullable = true)
    private String actorUsernameSnapshot;

    @Column(name = "actor_display_name_snapshot", nullable = false)
    private String actorDisplayNameSnapshot;

    @Column(name = "actor_role_code_snapshot", nullable = true)
    private String actorRoleCodeSnapshot;

    @Column(name = "organization_unit_id", nullable = true)
    private String organizationUnitId;

    @Column(name = "organization_unit_name_snapshot", nullable = true)
    private String organizationUnitNameSnapshot;

    @Column(name = "organization_role_code_snapshot", nullable = true)
    private String organizationRoleCodeSnapshot;

    @Column(name = "correlation_id", nullable = true)
    private String correlationId;

    @Column(name = "acted_at", nullable = false)
    private Instant actedAt;

    protected WorkflowActionJpaEntity() {
        // Required by JPA.
    }

    public WorkflowActionJpaEntity(
            String id,
            String instanceId,
            String taskId,
            String actionType,
            String decision,
            String reasonId,
            String decisionNote,
            String commentText,
            String actorId,
            String actorUsernameSnapshot,
            String actorDisplayNameSnapshot,
            String actorRoleCodeSnapshot,
            String organizationUnitId,
            String organizationUnitNameSnapshot,
            String organizationRoleCodeSnapshot,
            String correlationId,
            Instant actedAt) {
        this.id = id;
        this.instanceId = instanceId;
        this.taskId = taskId;
        this.actionType = actionType;
        this.decision = decision;
        this.reasonId = reasonId;
        this.decisionNote = decisionNote;
        this.commentText = commentText;
        this.actorId = actorId;
        this.actorUsernameSnapshot = actorUsernameSnapshot;
        this.actorDisplayNameSnapshot = actorDisplayNameSnapshot;
        this.actorRoleCodeSnapshot = actorRoleCodeSnapshot;
        this.organizationUnitId = organizationUnitId;
        this.organizationUnitNameSnapshot = organizationUnitNameSnapshot;
        this.organizationRoleCodeSnapshot = organizationRoleCodeSnapshot;
        this.correlationId = correlationId;
        this.actedAt = actedAt;
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

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getDecisionNote() {
        return decisionNote;
    }

    public void setDecisionNote(String decisionNote) {
        this.decisionNote = decisionNote;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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

    public String getOrganizationRoleCodeSnapshot() {
        return organizationRoleCodeSnapshot;
    }

    public void setOrganizationRoleCodeSnapshot(String organizationRoleCodeSnapshot) {
        this.organizationRoleCodeSnapshot = organizationRoleCodeSnapshot;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Instant getActedAt() {
        return actedAt;
    }

    public void setActedAt(Instant actedAt) {
        this.actedAt = actedAt;
    }
}
