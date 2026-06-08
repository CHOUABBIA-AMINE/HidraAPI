/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCommentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : JPA entity for workflow comments.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * JPA entity for workflow comments.
 *
 * <p>Architecture role:
 * Persistence-only workflow representation. It must not be exposed to domain, application, or REST
 * layers and must not contain business behavior.
 */
@Entity
@Table(name = "hidra_workflow_comment")
public class WorkflowCommentJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "instance_id", nullable = false)
    private String instanceId;

    @Column(name = "task_id", nullable = true)
    private String taskId;

    @Column(name = "actor_id", nullable = false)
    private String actorId;

    @Column(name = "actor_username_snapshot", nullable = true)
    private String actorUsernameSnapshot;

    @Column(name = "actor_display_name_snapshot", nullable = false)
    private String actorDisplayNameSnapshot;

    @Column(name = "actor_role_code_snapshot", nullable = true)
    private String actorRoleCodeSnapshot;

    @Column(name = "comment_text", nullable = false)
    private String commentText;

    @Column(name = "commented_at", nullable = false)
    private Instant commentedAt;

    protected WorkflowCommentJpaEntity() {
        // Required by JPA.
    }

    public WorkflowCommentJpaEntity(
            String id,
            String instanceId,
            String taskId,
            String actorId,
            String actorUsernameSnapshot,
            String actorDisplayNameSnapshot,
            String actorRoleCodeSnapshot,
            String commentText,
            Instant commentedAt) {
        this.id = id;
        this.instanceId = instanceId;
        this.taskId = taskId;
        this.actorId = actorId;
        this.actorUsernameSnapshot = actorUsernameSnapshot;
        this.actorDisplayNameSnapshot = actorDisplayNameSnapshot;
        this.actorRoleCodeSnapshot = actorRoleCodeSnapshot;
        this.commentText = commentText;
        this.commentedAt = commentedAt;
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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Instant getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(Instant commentedAt) {
        this.commentedAt = commentedAt;
    }
}
