/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinition
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Workflow definition aggregate root.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowLocalizedName;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowStepId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTransitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTypeReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowVersion;

/**
 * Workflow definition aggregate root.
 *
 * <p>Business role:
 * Describes the versioned process model used to start workflow instances.
 *
 * <p>Architecture rule:
 * Workflow definitions are pure workflow domain models. They do not know telemetry, topology,
 * planning, monitoring, incident, audit, integration, analytics, reporting, or notification entities.
 */
public final class WorkflowDefinition implements AggregateRoot<WorkflowDefinitionId> {

    private final WorkflowDefinitionId id;
    private final WorkflowCode code;
    private final WorkflowLocalizedName name;
    private final WorkflowTypeReference type;
    private final WorkflowDefinitionStatus status;
    private final WorkflowVersion version;
    private final List<WorkflowStep> steps;
    private final List<WorkflowTransition> transitions;
    private final Instant createdAt;
    private final Instant updatedAt;

    private WorkflowDefinition(
            WorkflowDefinitionId id,
            WorkflowCode code,
            WorkflowLocalizedName name,
            WorkflowTypeReference type,
            WorkflowDefinitionStatus status,
            WorkflowVersion version,
            List<WorkflowStep> steps,
            List<WorkflowTransition> transitions,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Workflow definition id must not be null.");
        this.code = Objects.requireNonNull(code, "Workflow definition code must not be null.");
        this.name = Objects.requireNonNull(name, "Workflow definition name must not be null.");
        this.type = Objects.requireNonNull(type, "Workflow definition type must not be null.");
        this.status = Objects.requireNonNull(status, "Workflow definition status must not be null.");
        this.version = Objects.requireNonNull(version, "Workflow definition version must not be null.");
        this.steps = steps == null ? List.of() : List.copyOf(steps);
        this.transitions = transitions == null ? List.of() : List.copyOf(transitions);
        this.createdAt = Objects.requireNonNull(createdAt, "Workflow definition createdAt must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Workflow definition updatedAt must not be null.");

        if (this.updatedAt.isBefore(this.createdAt)) {
            throw new BusinessRuleViolationException("Workflow definition updatedAt must not be before createdAt.");
        }

        validateChildrenBelongToDefinition();
    }

    public static WorkflowDefinition create(
            WorkflowCode code,
            WorkflowLocalizedName name,
            WorkflowTypeReference type) {

        Instant now = Instant.now();
        return new WorkflowDefinition(
                WorkflowDefinitionId.newId(),
                code,
                name,
                type,
                WorkflowDefinitionStatus.DRAFT,
                WorkflowVersion.initial(),
                List.of(),
                List.of(),
                now,
                now);
    }

    public static WorkflowDefinition restore(
            WorkflowDefinitionId id,
            WorkflowCode code,
            WorkflowLocalizedName name,
            WorkflowTypeReference type,
            WorkflowDefinitionStatus status,
            WorkflowVersion version,
            List<WorkflowStep> steps,
            List<WorkflowTransition> transitions,
            Instant createdAt,
            Instant updatedAt) {

        return new WorkflowDefinition(
                id,
                code,
                name,
                type,
                status,
                version,
                steps,
                transitions,
                createdAt,
                updatedAt);
    }

    @Override
    public WorkflowDefinitionId id() {
        return id;
    }

    public WorkflowCode code() {
        return code;
    }

    public WorkflowLocalizedName name() {
        return name;
    }

    public WorkflowTypeReference type() {
        return type;
    }

    public WorkflowDefinitionStatus status() {
        return status;
    }

    public WorkflowVersion version() {
        return version;
    }

    public List<WorkflowStep> steps() {
        return steps;
    }

    public List<WorkflowTransition> transitions() {
        return transitions;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public Optional<WorkflowStep> firstStep() {
        return steps.stream()
                .min(Comparator.comparingInt(WorkflowStep::stepOrder));
    }

    public Optional<WorkflowStep> stepById(WorkflowStepId stepId) {
        return steps.stream()
                .filter(step -> step.id().equals(stepId))
                .findFirst();
    }

    public Optional<WorkflowTransition> transitionById(WorkflowTransitionId transitionId) {
        return transitions.stream()
                .filter(transition -> transition.id().equals(transitionId))
                .findFirst();
    }

    public WorkflowDefinition addStep(WorkflowStep step) {
        Objects.requireNonNull(step, "Workflow definition step must not be null.");
        if (!id.equals(step.definitionId())) {
            throw new BusinessRuleViolationException("Workflow step must belong to this definition.");
        }
        if (steps.stream().anyMatch(existing -> existing.id().equals(step.id()))) {
            throw new BusinessRuleViolationException("Workflow definition already contains this step.");
        }
        if (steps.stream().anyMatch(existing -> existing.code().equals(step.code()))) {
            throw new BusinessRuleViolationException("Workflow definition already contains a step with the same code.");
        }

        List<WorkflowStep> nextSteps = new java.util.ArrayList<>(steps);
        nextSteps.add(step);

        return withChildren(nextSteps, transitions);
    }

    public WorkflowDefinition addTransition(WorkflowTransition transition) {
        Objects.requireNonNull(transition, "Workflow definition transition must not be null.");
        if (!id.equals(transition.definitionId())) {
            throw new BusinessRuleViolationException("Workflow transition must belong to this definition.");
        }
        if (transitions.stream().anyMatch(existing -> existing.id().equals(transition.id()))) {
            throw new BusinessRuleViolationException("Workflow definition already contains this transition.");
        }

        boolean fromExists = steps.stream().anyMatch(step -> step.id().equals(transition.fromStepId()));
        boolean toExists = steps.stream().anyMatch(step -> step.id().equals(transition.toStepId()));
        if (!fromExists || !toExists) {
            throw new BusinessRuleViolationException("Workflow transition steps must exist in the definition.");
        }

        List<WorkflowTransition> nextTransitions = new java.util.ArrayList<>(transitions);
        nextTransitions.add(transition);

        return withChildren(steps, nextTransitions);
    }

    public WorkflowDefinition activate() {
        if (status == WorkflowDefinitionStatus.ACTIVE) {
            return this;
        }
        if (steps.isEmpty()) {
            throw new BusinessRuleViolationException("Workflow definition requires at least one step before activation.");
        }

        return withStatus(WorkflowDefinitionStatus.ACTIVE);
    }

    public WorkflowDefinition deactivate() {
        return withStatus(WorkflowDefinitionStatus.INACTIVE);
    }

    public WorkflowDefinition retire() {
        return withStatus(WorkflowDefinitionStatus.RETIRED);
    }

    private WorkflowDefinition withStatus(WorkflowDefinitionStatus newStatus) {
        return new WorkflowDefinition(
                id,
                code,
                name,
                type,
                newStatus,
                version,
                steps,
                transitions,
                createdAt,
                Instant.now());
    }

    private WorkflowDefinition withChildren(
            List<WorkflowStep> nextSteps,
            List<WorkflowTransition> nextTransitions) {

        return new WorkflowDefinition(
                id,
                code,
                name,
                type,
                status,
                version,
                nextSteps,
                nextTransitions,
                createdAt,
                Instant.now());
    }

    private void validateChildrenBelongToDefinition() {
        boolean foreignStep = steps.stream().anyMatch(step -> !id.equals(step.definitionId()));
        boolean foreignTransition = transitions.stream().anyMatch(transition -> !id.equals(transition.definitionId()));

        if (foreignStep || foreignTransition) {
            throw new BusinessRuleViolationException("Workflow definition children must belong to the same definition.");
        }
    }
}
