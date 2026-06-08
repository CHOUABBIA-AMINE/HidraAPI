/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstance
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Workflow instance aggregate root.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowStepId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowVersion;

/**
 * Workflow instance aggregate root.
 *
 * <p>Business role:
 * Represents one process execution for a neutral workflow target, such as a telemetry reading.
 */
public final class WorkflowInstance implements AggregateRoot<WorkflowInstanceId> {

    private final WorkflowInstanceId id;
    private final WorkflowDefinitionId definitionId;
    private final WorkflowVersion definitionVersion;
    private final WorkflowTargetReference target;
    private final WorkflowInstanceStatus status;
    private final WorkflowStepId currentStepId;
    private final WorkflowActorReference startedBy;
    private final Instant startedAt;
    private final Instant completedAt;
    private final Instant cancelledAt;
    private final WorkflowCorrelationId correlationId;
    private final List<WorkflowTask> tasks;
    private final List<WorkflowAction> actions;
    private final List<WorkflowStateHistory> stateHistory;
    private final Instant createdAt;
    private final Instant updatedAt;

    private WorkflowInstance(
            WorkflowInstanceId id,
            WorkflowDefinitionId definitionId,
            WorkflowVersion definitionVersion,
            WorkflowTargetReference target,
            WorkflowInstanceStatus status,
            WorkflowStepId currentStepId,
            WorkflowActorReference startedBy,
            Instant startedAt,
            Instant completedAt,
            Instant cancelledAt,
            WorkflowCorrelationId correlationId,
            List<WorkflowTask> tasks,
            List<WorkflowAction> actions,
            List<WorkflowStateHistory> stateHistory,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Workflow instance id must not be null.");
        this.definitionId = Objects.requireNonNull(definitionId, "Workflow instance definitionId must not be null.");
        this.definitionVersion = Objects.requireNonNull(definitionVersion, "Workflow instance definitionVersion must not be null.");
        this.target = Objects.requireNonNull(target, "Workflow instance target must not be null.");
        this.status = Objects.requireNonNull(status, "Workflow instance status must not be null.");
        this.currentStepId = currentStepId;
        this.startedBy = Objects.requireNonNull(startedBy, "Workflow instance startedBy must not be null.");
        this.startedAt = Objects.requireNonNull(startedAt, "Workflow instance startedAt must not be null.");
        this.completedAt = completedAt;
        this.cancelledAt = cancelledAt;
        this.correlationId = correlationId;
        this.tasks = tasks == null ? List.of() : List.copyOf(tasks);
        this.actions = actions == null ? List.of() : List.copyOf(actions);
        this.stateHistory = stateHistory == null ? List.of() : List.copyOf(stateHistory);
        this.createdAt = Objects.requireNonNull(createdAt, "Workflow instance createdAt must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Workflow instance updatedAt must not be null.");

        if (status.isTerminal() && completedAt == null && cancelledAt == null) {
            throw new BusinessRuleViolationException("Terminal workflow instance requires completion or cancellation timestamp.");
        }
        if (updatedAt.isBefore(createdAt)) {
            throw new BusinessRuleViolationException("Workflow instance updatedAt must not be before createdAt.");
        }

        validateChildrenBelongToInstance();
    }

    public static WorkflowInstance start(
            WorkflowDefinitionId definitionId,
            WorkflowVersion definitionVersion,
            WorkflowTargetReference target,
            WorkflowStepId firstStepId,
            WorkflowActorReference startedBy,
            WorkflowCorrelationId correlationId) {

        Instant now = Instant.now();
        return new WorkflowInstance(
                WorkflowInstanceId.newId(),
                definitionId,
                definitionVersion,
                target,
                WorkflowInstanceStatus.STARTED,
                firstStepId,
                startedBy,
                now,
                null,
                null,
                correlationId,
                List.of(),
                List.of(),
                List.of(),
                now,
                now);
    }

    public static WorkflowInstance restore(
            WorkflowInstanceId id,
            WorkflowDefinitionId definitionId,
            WorkflowVersion definitionVersion,
            WorkflowTargetReference target,
            WorkflowInstanceStatus status,
            WorkflowStepId currentStepId,
            WorkflowActorReference startedBy,
            Instant startedAt,
            Instant completedAt,
            Instant cancelledAt,
            WorkflowCorrelationId correlationId,
            List<WorkflowTask> tasks,
            List<WorkflowAction> actions,
            List<WorkflowStateHistory> stateHistory,
            Instant createdAt,
            Instant updatedAt) {

        return new WorkflowInstance(
                id,
                definitionId,
                definitionVersion,
                target,
                status,
                currentStepId,
                startedBy,
                startedAt,
                completedAt,
                cancelledAt,
                correlationId,
                tasks,
                actions,
                stateHistory,
                createdAt,
                updatedAt);
    }

    @Override
    public WorkflowInstanceId id() {
        return id;
    }

    public WorkflowDefinitionId definitionId() {
        return definitionId;
    }

    public WorkflowVersion definitionVersion() {
        return definitionVersion;
    }

    public WorkflowTargetReference target() {
        return target;
    }

    public WorkflowInstanceStatus status() {
        return status;
    }

    public WorkflowStepId currentStepId() {
        return currentStepId;
    }

    public WorkflowActorReference startedBy() {
        return startedBy;
    }

    public Instant startedAt() {
        return startedAt;
    }

    public Instant completedAt() {
        return completedAt;
    }

    public Instant cancelledAt() {
        return cancelledAt;
    }

    public WorkflowCorrelationId correlationId() {
        return correlationId;
    }

    public List<WorkflowTask> tasks() {
        return tasks;
    }

    public List<WorkflowAction> actions() {
        return actions;
    }

    public List<WorkflowStateHistory> stateHistory() {
        return stateHistory;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public WorkflowInstance addTask(WorkflowTask task) {
        Objects.requireNonNull(task, "Workflow instance task must not be null.");
        if (!id.equals(task.instanceId())) {
            throw new BusinessRuleViolationException("Workflow task must belong to this instance.");
        }

        List<WorkflowTask> nextTasks = new java.util.ArrayList<>(tasks);
        nextTasks.add(task);
        return withChildren(status, currentStepId, nextTasks, actions, stateHistory, null, null);
    }

    public WorkflowInstance recordAction(WorkflowAction action) {
        Objects.requireNonNull(action, "Workflow instance action must not be null.");
        if (!id.equals(action.instanceId())) {
            throw new BusinessRuleViolationException("Workflow action must belong to this instance.");
        }

        List<WorkflowAction> nextActions = new java.util.ArrayList<>(actions);
        nextActions.add(action);
        return withChildren(status, currentStepId, tasks, nextActions, stateHistory, null, null);
    }

    public WorkflowInstance moveTo(
            WorkflowStepId nextStepId,
            WorkflowStateHistory history) {

        Objects.requireNonNull(nextStepId, "Workflow instance nextStepId must not be null.");
        Objects.requireNonNull(history, "Workflow instance state history must not be null.");

        if (!id.equals(history.instanceId())) {
            throw new BusinessRuleViolationException("Workflow state history must belong to this instance.");
        }

        List<WorkflowStateHistory> nextHistory = new java.util.ArrayList<>(stateHistory);
        nextHistory.add(history);
        return withChildren(WorkflowInstanceStatus.IN_PROGRESS, nextStepId, tasks, actions, nextHistory, null, null);
    }

    public WorkflowInstance complete() {
        if (status.isTerminal()) {
            return this;
        }

        Instant now = Instant.now();
        return withChildren(WorkflowInstanceStatus.COMPLETED, currentStepId, tasks, actions, stateHistory, now, null);
    }

    public WorkflowInstance cancel() {
        if (status.isTerminal()) {
            return this;
        }

        Instant now = Instant.now();
        return withChildren(WorkflowInstanceStatus.CANCELLED, currentStepId, tasks, actions, stateHistory, null, now);
    }

    private WorkflowInstance withChildren(
            WorkflowInstanceStatus nextStatus,
            WorkflowStepId nextCurrentStepId,
            List<WorkflowTask> nextTasks,
            List<WorkflowAction> nextActions,
            List<WorkflowStateHistory> nextStateHistory,
            Instant nextCompletedAt,
            Instant nextCancelledAt) {

        return new WorkflowInstance(
                id,
                definitionId,
                definitionVersion,
                target,
                nextStatus,
                nextCurrentStepId,
                startedBy,
                startedAt,
                nextCompletedAt == null ? completedAt : nextCompletedAt,
                nextCancelledAt == null ? cancelledAt : nextCancelledAt,
                correlationId,
                nextTasks,
                nextActions,
                nextStateHistory,
                createdAt,
                Instant.now());
    }

    private void validateChildrenBelongToInstance() {
        boolean foreignTask = tasks.stream().anyMatch(task -> !id.equals(task.instanceId()));
        boolean foreignAction = actions.stream().anyMatch(action -> !id.equals(action.instanceId()));
        boolean foreignHistory = stateHistory.stream().anyMatch(history -> !id.equals(history.instanceId()));

        if (foreignTask || foreignAction || foreignHistory) {
            throw new BusinessRuleViolationException("Workflow instance children must belong to the same instance.");
        }
    }
}
