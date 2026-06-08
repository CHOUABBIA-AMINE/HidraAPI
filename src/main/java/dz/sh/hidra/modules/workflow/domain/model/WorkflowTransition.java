/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTransition
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Workflow definition transition domain entity.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowStepId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTransitionId;

/**
 * Workflow transition domain entity.
 *
 * <p>Business role:
 * Defines a decision-driven move from one workflow step to another.
 */
public final class WorkflowTransition implements Entity<WorkflowTransitionId> {

    private final WorkflowTransitionId id;
    private final WorkflowDefinitionId definitionId;
    private final WorkflowStepId fromStepId;
    private final WorkflowStepId toStepId;
    private final WorkflowDecision decision;
    private final boolean reasonRequired;
    private final boolean commentRequired;
    private final Instant createdAt;
    private final Instant updatedAt;

    private WorkflowTransition(
            WorkflowTransitionId id,
            WorkflowDefinitionId definitionId,
            WorkflowStepId fromStepId,
            WorkflowStepId toStepId,
            WorkflowDecision decision,
            boolean reasonRequired,
            boolean commentRequired,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Workflow transition id must not be null.");
        this.definitionId = Objects.requireNonNull(definitionId, "Workflow transition definitionId must not be null.");
        this.fromStepId = Objects.requireNonNull(fromStepId, "Workflow transition fromStepId must not be null.");
        this.toStepId = Objects.requireNonNull(toStepId, "Workflow transition toStepId must not be null.");
        this.decision = Objects.requireNonNull(decision, "Workflow transition decision must not be null.");
        this.reasonRequired = reasonRequired || decision.requiresReason();
        this.commentRequired = commentRequired;
        this.createdAt = Objects.requireNonNull(createdAt, "Workflow transition createdAt must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Workflow transition updatedAt must not be null.");

        if (this.fromStepId.equals(this.toStepId)) {
            throw new BusinessRuleViolationException("Workflow transition cannot point to the same step.");
        }
        if (this.updatedAt.isBefore(this.createdAt)) {
            throw new BusinessRuleViolationException("Workflow transition updatedAt must not be before createdAt.");
        }
    }

    public static WorkflowTransition create(
            WorkflowDefinitionId definitionId,
            WorkflowStepId fromStepId,
            WorkflowStepId toStepId,
            WorkflowDecision decision,
            boolean reasonRequired,
            boolean commentRequired) {

        Instant now = Instant.now();
        return new WorkflowTransition(
                WorkflowTransitionId.newId(),
                definitionId,
                fromStepId,
                toStepId,
                decision,
                reasonRequired,
                commentRequired,
                now,
                now);
    }

    public static WorkflowTransition restore(
            WorkflowTransitionId id,
            WorkflowDefinitionId definitionId,
            WorkflowStepId fromStepId,
            WorkflowStepId toStepId,
            WorkflowDecision decision,
            boolean reasonRequired,
            boolean commentRequired,
            Instant createdAt,
            Instant updatedAt) {

        return new WorkflowTransition(
                id,
                definitionId,
                fromStepId,
                toStepId,
                decision,
                reasonRequired,
                commentRequired,
                createdAt,
                updatedAt);
    }

    @Override
    public WorkflowTransitionId id() {
        return id;
    }

    public WorkflowDefinitionId definitionId() {
        return definitionId;
    }

    public WorkflowStepId fromStepId() {
        return fromStepId;
    }

    public WorkflowStepId toStepId() {
        return toStepId;
    }

    public WorkflowDecision decision() {
        return decision;
    }

    public boolean reasonRequired() {
        return reasonRequired;
    }

    public boolean commentRequired() {
        return commentRequired;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}
