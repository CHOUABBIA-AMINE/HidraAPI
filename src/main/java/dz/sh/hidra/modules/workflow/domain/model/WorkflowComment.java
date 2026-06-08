/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowComment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Workflow comment domain entity.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCommentId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCommentText;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId;

/**
 * Workflow comment domain entity.
 */
public final class WorkflowComment implements Entity<WorkflowCommentId> {

    private final WorkflowCommentId id;
    private final WorkflowInstanceId instanceId;
    private final WorkflowTaskId taskId;
    private final WorkflowActorReference actor;
    private final WorkflowCommentText text;
    private final Instant commentedAt;

    private WorkflowComment(
            WorkflowCommentId id,
            WorkflowInstanceId instanceId,
            WorkflowTaskId taskId,
            WorkflowActorReference actor,
            WorkflowCommentText text,
            Instant commentedAt) {

        this.id = Objects.requireNonNull(id, "Workflow comment id must not be null.");
        this.instanceId = Objects.requireNonNull(instanceId, "Workflow comment instanceId must not be null.");
        this.taskId = taskId;
        this.actor = Objects.requireNonNull(actor, "Workflow comment actor must not be null.");
        this.text = Objects.requireNonNull(text, "Workflow comment text must not be null.");
        this.commentedAt = Objects.requireNonNull(commentedAt, "Workflow comment commentedAt must not be null.");
    }

    public static WorkflowComment add(
            WorkflowInstanceId instanceId,
            WorkflowTaskId taskId,
            WorkflowActorReference actor,
            WorkflowCommentText text) {

        return new WorkflowComment(
                WorkflowCommentId.newId(),
                instanceId,
                taskId,
                actor,
                text,
                Instant.now());
    }

    public static WorkflowComment restore(
            WorkflowCommentId id,
            WorkflowInstanceId instanceId,
            WorkflowTaskId taskId,
            WorkflowActorReference actor,
            WorkflowCommentText text,
            Instant commentedAt) {

        return new WorkflowComment(id, instanceId, taskId, actor, text, commentedAt);
    }

    @Override
    public WorkflowCommentId id() {
        return id;
    }

    public WorkflowInstanceId instanceId() {
        return instanceId;
    }

    public WorkflowTaskId taskId() {
        return taskId;
    }

    public WorkflowActorReference actor() {
        return actor;
    }

    public WorkflowCommentText text() {
        return text;
    }

    public Instant commentedAt() {
        return commentedAt;
    }
}
