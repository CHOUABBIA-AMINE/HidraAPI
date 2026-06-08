/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStateHistory
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Workflow state history domain entity.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowStepId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId;

/**
 * Workflow state history domain entity.
 */
public final class WorkflowStateHistory implements Entity<WorkflowActionId> {

    private final WorkflowActionId id;
    private final WorkflowInstanceId instanceId;
    private final WorkflowTaskId taskId;
    private final WorkflowStepId fromStepId;
    private final WorkflowStepId toStepId;
    private final WorkflowInstanceStatus fromStatus;
    private final WorkflowInstanceStatus toStatus;
    private final WorkflowActorReference actor;
    private final Instant changedAt;

    private WorkflowStateHistory(
            WorkflowActionId id,
            WorkflowInstanceId instanceId,
            WorkflowTaskId taskId,
            WorkflowStepId fromStepId,
            WorkflowStepId toStepId,
            WorkflowInstanceStatus fromStatus,
            WorkflowInstanceStatus toStatus,
            WorkflowActorReference actor,
            Instant changedAt) {

        this.id = Objects.requireNonNull(id, "Workflow state history id must not be null.");
        this.instanceId = Objects.requireNonNull(instanceId, "Workflow state history instanceId must not be null.");
        this.taskId = taskId;
        this.fromStepId = fromStepId;
        this.toStepId = toStepId;
        this.fromStatus = fromStatus;
        this.toStatus = Objects.requireNonNull(toStatus, "Workflow state history toStatus must not be null.");
        this.actor = Objects.requireNonNull(actor, "Workflow state history actor must not be null.");
        this.changedAt = Objects.requireNonNull(changedAt, "Workflow state history changedAt must not be null.");
    }

    public static WorkflowStateHistory record(
            WorkflowInstanceId instanceId,
            WorkflowTaskId taskId,
            WorkflowStepId fromStepId,
            WorkflowStepId toStepId,
            WorkflowInstanceStatus fromStatus,
            WorkflowInstanceStatus toStatus,
            WorkflowActorReference actor) {

        return new WorkflowStateHistory(
                WorkflowActionId.newId(),
                instanceId,
                taskId,
                fromStepId,
                toStepId,
                fromStatus,
                toStatus,
                actor,
                Instant.now());
    }

    public static WorkflowStateHistory restore(
            WorkflowActionId id,
            WorkflowInstanceId instanceId,
            WorkflowTaskId taskId,
            WorkflowStepId fromStepId,
            WorkflowStepId toStepId,
            WorkflowInstanceStatus fromStatus,
            WorkflowInstanceStatus toStatus,
            WorkflowActorReference actor,
            Instant changedAt) {

        return new WorkflowStateHistory(
                id,
                instanceId,
                taskId,
                fromStepId,
                toStepId,
                fromStatus,
                toStatus,
                actor,
                changedAt);
    }

    @Override
    public WorkflowActionId id() {
        return id;
    }

    public WorkflowInstanceId instanceId() {
        return instanceId;
    }

    public WorkflowTaskId taskId() {
        return taskId;
    }

    public WorkflowStepId fromStepId() {
        return fromStepId;
    }

    public WorkflowStepId toStepId() {
        return toStepId;
    }

    public WorkflowInstanceStatus fromStatus() {
        return fromStatus;
    }

    public WorkflowInstanceStatus toStatus() {
        return toStatus;
    }

    public WorkflowActorReference actor() {
        return actor;
    }

    public Instant changedAt() {
        return changedAt;
    }
}
