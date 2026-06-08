/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDelegation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Workflow delegation domain entity.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDelegationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId;

/**
 * Workflow delegation domain entity.
 */
public final class WorkflowDelegation implements Entity<WorkflowDelegationId> {

    private final WorkflowDelegationId id;
    private final WorkflowTaskId taskId;
    private final WorkflowActorReference fromActor;
    private final WorkflowActorReference toActor;
    private final WorkflowOrganizationReference toOrganization;
    private final WorkflowReasonReference reason;
    private final Instant delegatedAt;

    private WorkflowDelegation(
            WorkflowDelegationId id,
            WorkflowTaskId taskId,
            WorkflowActorReference fromActor,
            WorkflowActorReference toActor,
            WorkflowOrganizationReference toOrganization,
            WorkflowReasonReference reason,
            Instant delegatedAt) {

        this.id = Objects.requireNonNull(id, "Workflow delegation id must not be null.");
        this.taskId = Objects.requireNonNull(taskId, "Workflow delegation taskId must not be null.");
        this.fromActor = Objects.requireNonNull(fromActor, "Workflow delegation fromActor must not be null.");
        this.toActor = toActor;
        this.toOrganization = toOrganization;
        this.reason = Objects.requireNonNull(reason, "Workflow delegation reason must not be null.");
        this.delegatedAt = Objects.requireNonNull(delegatedAt, "Workflow delegation delegatedAt must not be null.");

        if (toActor == null && toOrganization == null) {
            throw new BusinessRuleViolationException("Workflow delegation requires target actor or organization.");
        }
    }

    public static WorkflowDelegation delegate(
            WorkflowTaskId taskId,
            WorkflowActorReference fromActor,
            WorkflowActorReference toActor,
            WorkflowOrganizationReference toOrganization,
            WorkflowReasonReference reason) {

        return new WorkflowDelegation(
                WorkflowDelegationId.newId(),
                taskId,
                fromActor,
                toActor,
                toOrganization,
                reason,
                Instant.now());
    }

    public static WorkflowDelegation restore(
            WorkflowDelegationId id,
            WorkflowTaskId taskId,
            WorkflowActorReference fromActor,
            WorkflowActorReference toActor,
            WorkflowOrganizationReference toOrganization,
            WorkflowReasonReference reason,
            Instant delegatedAt) {

        return new WorkflowDelegation(id, taskId, fromActor, toActor, toOrganization, reason, delegatedAt);
    }

    @Override
    public WorkflowDelegationId id() {
        return id;
    }

    public WorkflowTaskId taskId() {
        return taskId;
    }

    public WorkflowActorReference fromActor() {
        return fromActor;
    }

    public WorkflowActorReference toActor() {
        return toActor;
    }

    public WorkflowOrganizationReference toOrganization() {
        return toOrganization;
    }

    public WorkflowReasonReference reason() {
        return reason;
    }

    public Instant delegatedAt() {
        return delegatedAt;
    }
}
