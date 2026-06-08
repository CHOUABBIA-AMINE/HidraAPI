/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTask
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Workflow task domain entity.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDueDate;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowPriorityReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowStepId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;

/**
 * Workflow task domain entity.
 *
 * <p>Business role:
 * Represents actionable work assigned to an actor or organization unit.
 */
public final class WorkflowTask implements Entity<WorkflowTaskId> {

    private final WorkflowTaskId id;
    private final WorkflowInstanceId instanceId;
    private final WorkflowStepId stepId;
    private final WorkflowTaskStatus status;
    private final WorkflowActorReference assignedActor;
    private final WorkflowOrganizationReference assignedOrganization;
    private final WorkflowPriorityReference priority;
    private final WorkflowDueDate dueDate;
    private final WorkflowActorReference claimedBy;
    private final Instant claimedAt;
    private final WorkflowActorReference completedBy;
    private final Instant completedAt;
    private final Instant createdAt;
    private final Instant updatedAt;

    private WorkflowTask(
            WorkflowTaskId id,
            WorkflowInstanceId instanceId,
            WorkflowStepId stepId,
            WorkflowTaskStatus status,
            WorkflowActorReference assignedActor,
            WorkflowOrganizationReference assignedOrganization,
            WorkflowPriorityReference priority,
            WorkflowDueDate dueDate,
            WorkflowActorReference claimedBy,
            Instant claimedAt,
            WorkflowActorReference completedBy,
            Instant completedAt,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Workflow task id must not be null.");
        this.instanceId = Objects.requireNonNull(instanceId, "Workflow task instanceId must not be null.");
        this.stepId = Objects.requireNonNull(stepId, "Workflow task stepId must not be null.");
        this.status = Objects.requireNonNull(status, "Workflow task status must not be null.");
        this.assignedActor = assignedActor;
        this.assignedOrganization = assignedOrganization;
        this.priority = priority;
        this.dueDate = dueDate;
        this.claimedBy = claimedBy;
        this.claimedAt = claimedAt;
        this.completedBy = completedBy;
        this.completedAt = completedAt;
        this.createdAt = Objects.requireNonNull(createdAt, "Workflow task createdAt must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Workflow task updatedAt must not be null.");

        if (assignedActor == null && assignedOrganization == null) {
            throw new BusinessRuleViolationException("Workflow task must be assigned to an actor or organization unit.");
        }
        if ((claimedBy == null) != (claimedAt == null)) {
            throw new BusinessRuleViolationException("Workflow task claimedBy and claimedAt must be provided together.");
        }
        if ((completedBy == null) != (completedAt == null)) {
            throw new BusinessRuleViolationException("Workflow task completedBy and completedAt must be provided together.");
        }
        if (updatedAt.isBefore(createdAt)) {
            throw new BusinessRuleViolationException("Workflow task updatedAt must not be before createdAt.");
        }
    }

    public static WorkflowTask open(
            WorkflowInstanceId instanceId,
            WorkflowStepId stepId,
            WorkflowActorReference assignedActor,
            WorkflowOrganizationReference assignedOrganization,
            WorkflowPriorityReference priority,
            WorkflowDueDate dueDate) {

        Instant now = Instant.now();
        return new WorkflowTask(
                WorkflowTaskId.newId(),
                instanceId,
                stepId,
                WorkflowTaskStatus.OPEN,
                assignedActor,
                assignedOrganization,
                priority,
                dueDate,
                null,
                null,
                null,
                null,
                now,
                now);
    }

    public static WorkflowTask restore(
            WorkflowTaskId id,
            WorkflowInstanceId instanceId,
            WorkflowStepId stepId,
            WorkflowTaskStatus status,
            WorkflowActorReference assignedActor,
            WorkflowOrganizationReference assignedOrganization,
            WorkflowPriorityReference priority,
            WorkflowDueDate dueDate,
            WorkflowActorReference claimedBy,
            Instant claimedAt,
            WorkflowActorReference completedBy,
            Instant completedAt,
            Instant createdAt,
            Instant updatedAt) {

        return new WorkflowTask(
                id,
                instanceId,
                stepId,
                status,
                assignedActor,
                assignedOrganization,
                priority,
                dueDate,
                claimedBy,
                claimedAt,
                completedBy,
                completedAt,
                createdAt,
                updatedAt);
    }

    @Override
    public WorkflowTaskId id() {
        return id;
    }

    public WorkflowInstanceId instanceId() {
        return instanceId;
    }

    public WorkflowStepId stepId() {
        return stepId;
    }

    public WorkflowTaskStatus status() {
        return status;
    }

    public WorkflowActorReference assignedActor() {
        return assignedActor;
    }

    public WorkflowOrganizationReference assignedOrganization() {
        return assignedOrganization;
    }

    public WorkflowPriorityReference priority() {
        return priority;
    }

    public WorkflowDueDate dueDate() {
        return dueDate;
    }

    public WorkflowActorReference claimedBy() {
        return claimedBy;
    }

    public Instant claimedAt() {
        return claimedAt;
    }

    public WorkflowActorReference completedBy() {
        return completedBy;
    }

    public Instant completedAt() {
        return completedAt;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public WorkflowTask claim(WorkflowActorReference actor) {
        if (status.isClosed()) {
            throw new BusinessRuleViolationException("Closed workflow task cannot be claimed.");
        }

        Instant now = Instant.now();
        return withStatus(WorkflowTaskStatus.CLAIMED, actor, now, completedBy, completedAt);
    }

    public WorkflowTask complete(WorkflowActorReference actor, WorkflowTaskStatus completionStatus) {
        if (completionStatus == null || !completionStatus.isClosed()) {
            throw new BusinessRuleViolationException("Workflow task completion status must be closed.");
        }
        if (status.isClosed()) {
            throw new BusinessRuleViolationException("Workflow task is already closed.");
        }

        Instant now = Instant.now();
        return withStatus(completionStatus, claimedBy, claimedAt, actor, now);
    }

    public WorkflowTask delegateTo(
            WorkflowActorReference nextActor,
            WorkflowOrganizationReference nextOrganization) {

        return new WorkflowTask(
                id,
                instanceId,
                stepId,
                WorkflowTaskStatus.DELEGATED,
                nextActor,
                nextOrganization,
                priority,
                dueDate,
                claimedBy,
                claimedAt,
                completedBy,
                completedAt,
                createdAt,
                Instant.now());
    }

    private WorkflowTask withStatus(
            WorkflowTaskStatus newStatus,
            WorkflowActorReference newClaimedBy,
            Instant newClaimedAt,
            WorkflowActorReference newCompletedBy,
            Instant newCompletedAt) {

        return new WorkflowTask(
                id,
                instanceId,
                stepId,
                newStatus,
                assignedActor,
                assignedOrganization,
                priority,
                dueDate,
                newClaimedBy,
                newClaimedAt,
                newCompletedBy,
                newCompletedAt,
                createdAt,
                Instant.now());
    }
}
