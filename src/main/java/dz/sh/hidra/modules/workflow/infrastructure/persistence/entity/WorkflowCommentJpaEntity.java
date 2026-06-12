/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCommentJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for WorkflowComment.
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
     * Database-backed JPA entity for WorkflowComment.
     */
    @Entity
    @Table(name = "hidra_workflow_comment")
    public class WorkflowCommentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "instance_id", nullable = false, length = 80)
    private String instanceId;

    @Column(name = "task_id", nullable = true, length = 80)
    private String taskId;

    @Column(name = "actor_id", nullable = false, length = 80)
    private String actorId;

    @Column(name = "actor_username_snapshot", nullable = true, length = 120)
    private String actorUsernameSnapshot;

    @Column(name = "actor_display_name_snapshot", nullable = false, length = 160)
    private String actorDisplayNameSnapshot;

    @Column(name = "actor_role_code_snapshot", nullable = true, length = 80)
    private String actorRoleCodeSnapshot;

    @Column(name = "comment_text", nullable = false, columnDefinition = "text")
    private String commentText;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = true, length = 40)
    private WorkflowCommentVisibility visibility;

    @Column(name = "parent_comment_id", nullable = true, length = 80)
    private String parentCommentId;

    @Column(name = "commented_at", nullable = false)
    private Instant commentedAt;

    @Column(name = "edited_at", nullable = true)
    private Instant editedAt;

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
            WorkflowCommentVisibility visibility,
            String parentCommentId,
            Instant commentedAt,
            Instant editedAt
        ) {
            this.id = id;
        this.instanceId = instanceId;
        this.taskId = taskId;
        this.actorId = actorId;
        this.actorUsernameSnapshot = actorUsernameSnapshot;
        this.actorDisplayNameSnapshot = actorDisplayNameSnapshot;
        this.actorRoleCodeSnapshot = actorRoleCodeSnapshot;
        this.commentText = commentText;
        this.visibility = visibility;
        this.parentCommentId = parentCommentId;
        this.commentedAt = commentedAt;
        this.editedAt = editedAt;
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


    public String commentText() {
        return commentText;
    }


    public WorkflowCommentVisibility visibility() {
        return visibility;
    }


    public String parentCommentId() {
        return parentCommentId;
    }


    public Instant commentedAt() {
        return commentedAt;
    }


    public Instant editedAt() {
        return editedAt;
    }

    }
