/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowAssignment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Workflow assignment domain entity.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowAssignmentId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowAssignmentStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId;

/**
 * Workflow assignment domain entity.
 */
public final class WorkflowAssignment implements Entity<WorkflowAssignmentId> {

    private final WorkflowAssignmentId id;
    private final WorkflowTaskId taskId;
    private final WorkflowActorReference actor;
    private final WorkflowOrganizationReference organization;
    private final WorkflowAssignmentStatus status;
    private final Instant assignedAt;
    private final Instant updatedAt;

    private WorkflowAssignment(
            WorkflowAssignmentId id,
            WorkflowTaskId taskId,
            WorkflowActorReference actor,
            WorkflowOrganizationReference organization,
            WorkflowAssignmentStatus status,
            Instant assignedAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Workflow assignment id must not be null.");
        this.taskId = Objects.requireNonNull(taskId, "Workflow assignment taskId must not be null.");
        this.actor = actor;
        this.organization = organization;
        this.status = Objects.requireNonNull(status, "Workflow assignment status must not be null.");
        this.assignedAt = Objects.requireNonNull(assignedAt, "Workflow assignment assignedAt must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Workflow assignment updatedAt must not be null.");

        if (actor == null && organization == null) {
            throw new BusinessRuleViolationException("Workflow assignment requires actor or organization.");
        }
        if (updatedAt.isBefore(assignedAt)) {
            throw new BusinessRuleViolationException("Workflow assignment updatedAt must not be before assignedAt.");
        }
    }

    public static WorkflowAssignment assign(
            WorkflowTaskId taskId,
            WorkflowActorReference actor,
            WorkflowOrganizationReference organization) {

        Instant now = Instant.now();
        return new WorkflowAssignment(
                WorkflowAssignmentId.newId(),
                taskId,
                actor,
                organization,
                WorkflowAssignmentStatus.ASSIGNED,
                now,
                now);
    }

    public static WorkflowAssignment restore(
            WorkflowAssignmentId id,
            WorkflowTaskId taskId,
            WorkflowActorReference actor,
            WorkflowOrganizationReference organization,
            WorkflowAssignmentStatus status,
            Instant assignedAt,
            Instant updatedAt) {

        return new WorkflowAssignment(id, taskId, actor, organization, status, assignedAt, updatedAt);
    }

    @Override
    public WorkflowAssignmentId id() {
        return id;
    }

    public WorkflowTaskId taskId() {
        return taskId;
    }

    public WorkflowActorReference actor() {
        return actor;
    }

    public WorkflowOrganizationReference organization() {
        return organization;
    }

    public WorkflowAssignmentStatus status() {
        return status;
    }

    public Instant assignedAt() {
        return assignedAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}
