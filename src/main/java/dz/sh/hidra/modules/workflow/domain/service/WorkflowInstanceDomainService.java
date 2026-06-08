/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.service
 *
 * @Description : Domain service for workflow instance lifecycle orchestration.
 *
 */
package dz.sh.hidra.modules.workflow.domain.service;

import java.util.Objects;

import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStateHistory;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStep;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowDefinitionPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowTargetPolicy;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;

/**
 * Domain service for workflow instance lifecycle orchestration.
 *
 * <p>Business role:
 * Starts, moves, completes, and cancels workflow instances over neutral workflow targets.
 *
 * <p>Architecture role:
 * Pure domain service. It validates neutral target references but does not load or mutate telemetry
 * readings.
 */
public final class WorkflowInstanceDomainService {

    private final WorkflowDefinitionPolicy definitionPolicy;
    private final WorkflowTargetPolicy targetPolicy;

    public WorkflowInstanceDomainService(
            WorkflowDefinitionPolicy definitionPolicy,
            WorkflowTargetPolicy targetPolicy) {

        this.definitionPolicy = Objects.requireNonNull(definitionPolicy, "WorkflowDefinitionPolicy must not be null.");
        this.targetPolicy = Objects.requireNonNull(targetPolicy, "WorkflowTargetPolicy must not be null.");
    }

    public WorkflowInstance startInstance(
            WorkflowDefinition definition,
            WorkflowTargetReference target,
            WorkflowActorReference startedBy,
            WorkflowCorrelationId correlationId) {

        Objects.requireNonNull(definition, "Workflow definition must not be null.");
        Objects.requireNonNull(startedBy, "Workflow starter actor must not be null.");

        definitionPolicy.requireDefinitionCanStartInstance(definition);
        targetPolicy.requireTargetCanStartWorkflow(target);

        WorkflowStep firstStep = definition.firstStep()
                .orElseThrow(() -> new IllegalStateException("Workflow definition has no initial step."));

        return WorkflowInstance.start(
                definition.id(),
                definition.version(),
                target,
                firstStep.id(),
                startedBy,
                correlationId);
    }

    public WorkflowInstance startTelemetryReadingValidation(
            WorkflowDefinition definition,
            WorkflowTargetReference target,
            WorkflowActorReference startedBy,
            WorkflowCorrelationId correlationId) {

        targetPolicy.requireTelemetryReadingTarget(target);
        return startInstance(definition, target, startedBy, correlationId);
    }

    public WorkflowInstance moveToNextStep(
            WorkflowInstance instance,
            WorkflowStateHistory stateHistory) {

        Objects.requireNonNull(instance, "Workflow instance must not be null.");
        Objects.requireNonNull(stateHistory, "Workflow state history must not be null.");
        Objects.requireNonNull(stateHistory.toStepId(), "Workflow state history target step must not be null.");

        return instance.moveTo(stateHistory.toStepId(), stateHistory);
    }

    public WorkflowInstance complete(WorkflowInstance instance) {
        Objects.requireNonNull(instance, "Workflow instance must not be null.");
        return instance.complete();
    }

    public WorkflowInstance cancel(WorkflowInstance instance) {
        Objects.requireNonNull(instance, "Workflow instance must not be null.");
        return instance.cancel();
    }

    public WorkflowStateHistory recordStateChange(
            WorkflowInstance instance,
            WorkflowInstanceStatus toStatus,
            WorkflowActorReference actor) {

        Objects.requireNonNull(instance, "Workflow instance must not be null.");
        Objects.requireNonNull(toStatus, "Workflow target status must not be null.");
        Objects.requireNonNull(actor, "Workflow actor must not be null.");

        return WorkflowStateHistory.record(
                instance.id(),
                null,
                instance.currentStepId(),
                instance.currentStepId(),
                instance.status(),
                toStatus,
                actor);
    }
}
