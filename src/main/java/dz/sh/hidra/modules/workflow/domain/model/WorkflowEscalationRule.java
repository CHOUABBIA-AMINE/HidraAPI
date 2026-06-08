/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowEscalationRule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Workflow escalation rule domain entity.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowEscalationRuleId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowStepId;

/**
 * Workflow escalation rule domain entity.
 */
public final class WorkflowEscalationRule implements Entity<WorkflowEscalationRuleId> {

    private final WorkflowEscalationRuleId id;
    private final WorkflowDefinitionId definitionId;
    private final WorkflowStepId stepId;
    private final Duration afterDuration;
    private final WorkflowActorReference escalateToActor;
    private final WorkflowOrganizationReference escalateToOrganization;
    private final boolean active;
    private final Instant createdAt;
    private final Instant updatedAt;

    private WorkflowEscalationRule(
            WorkflowEscalationRuleId id,
            WorkflowDefinitionId definitionId,
            WorkflowStepId stepId,
            Duration afterDuration,
            WorkflowActorReference escalateToActor,
            WorkflowOrganizationReference escalateToOrganization,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Workflow escalation rule id must not be null.");
        this.definitionId = Objects.requireNonNull(definitionId, "Workflow escalation rule definitionId must not be null.");
        this.stepId = Objects.requireNonNull(stepId, "Workflow escalation rule stepId must not be null.");
        this.afterDuration = Objects.requireNonNull(afterDuration, "Workflow escalation rule afterDuration must not be null.");
        this.escalateToActor = escalateToActor;
        this.escalateToOrganization = escalateToOrganization;
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "Workflow escalation rule createdAt must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Workflow escalation rule updatedAt must not be null.");

        if (afterDuration.isNegative() || afterDuration.isZero()) {
            throw new BusinessRuleViolationException("Workflow escalation rule duration must be positive.");
        }
        if (escalateToActor == null && escalateToOrganization == null) {
            throw new BusinessRuleViolationException("Workflow escalation rule requires target actor or organization.");
        }
        if (updatedAt.isBefore(createdAt)) {
            throw new BusinessRuleViolationException("Workflow escalation rule updatedAt must not be before createdAt.");
        }
    }

    public static WorkflowEscalationRule create(
            WorkflowDefinitionId definitionId,
            WorkflowStepId stepId,
            Duration afterDuration,
            WorkflowActorReference escalateToActor,
            WorkflowOrganizationReference escalateToOrganization) {

        Instant now = Instant.now();
        return new WorkflowEscalationRule(
                WorkflowEscalationRuleId.newId(),
                definitionId,
                stepId,
                afterDuration,
                escalateToActor,
                escalateToOrganization,
                true,
                now,
                now);
    }

    public static WorkflowEscalationRule restore(
            WorkflowEscalationRuleId id,
            WorkflowDefinitionId definitionId,
            WorkflowStepId stepId,
            Duration afterDuration,
            WorkflowActorReference escalateToActor,
            WorkflowOrganizationReference escalateToOrganization,
            boolean active,
            Instant createdAt,
            Instant updatedAt) {

        return new WorkflowEscalationRule(
                id,
                definitionId,
                stepId,
                afterDuration,
                escalateToActor,
                escalateToOrganization,
                active,
                createdAt,
                updatedAt);
    }

    @Override
    public WorkflowEscalationRuleId id() {
        return id;
    }

    public WorkflowDefinitionId definitionId() {
        return definitionId;
    }

    public WorkflowStepId stepId() {
        return stepId;
    }

    public Duration afterDuration() {
        return afterDuration;
    }

    public WorkflowActorReference escalateToActor() {
        return escalateToActor;
    }

    public WorkflowOrganizationReference escalateToOrganization() {
        return escalateToOrganization;
    }

    public boolean active() {
        return active;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}
