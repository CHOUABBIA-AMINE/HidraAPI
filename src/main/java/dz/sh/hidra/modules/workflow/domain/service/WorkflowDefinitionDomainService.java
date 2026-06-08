/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.service
 *
 * @Description : Domain service for workflow definition lifecycle orchestration.
 *
 */
package dz.sh.hidra.modules.workflow.domain.service;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStep;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTransition;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowDefinitionPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowTransitionPolicy;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowLocalizedName;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowStepId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTypeReference;

/**
 * Domain service for workflow definition lifecycle orchestration.
 *
 * <p>Business role:
 * Coordinates definition creation, step registration, transition registration, activation,
 * deactivation, and retirement.
 *
 * <p>Architecture role:
 * Pure domain service. It does not persist definitions, expose REST endpoints, or call external
 * modules.
 */
public final class WorkflowDefinitionDomainService {

    private final WorkflowDefinitionPolicy definitionPolicy;
    private final WorkflowTransitionPolicy transitionPolicy;

    public WorkflowDefinitionDomainService(
            WorkflowDefinitionPolicy definitionPolicy,
            WorkflowTransitionPolicy transitionPolicy) {

        this.definitionPolicy = Objects.requireNonNull(definitionPolicy, "WorkflowDefinitionPolicy must not be null.");
        this.transitionPolicy = Objects.requireNonNull(transitionPolicy, "WorkflowTransitionPolicy must not be null.");
    }

    public WorkflowDefinition createDefinition(
            WorkflowCode code,
            WorkflowLocalizedName name,
            WorkflowTypeReference type) {

        return WorkflowDefinition.create(code, name, type);
    }

    public WorkflowDefinition addStep(
            WorkflowDefinition definition,
            String stepCode,
            WorkflowLocalizedName stepName,
            int stepOrder,
            boolean mandatory) {

        definitionPolicy.requireDefinitionCanBeModified(definition);

        WorkflowStep step = WorkflowStep.create(
                definition.id(),
                stepCode,
                stepName,
                stepOrder,
                mandatory);

        return definition.addStep(step);
    }

    public WorkflowDefinition addTransition(
            WorkflowDefinition definition,
            WorkflowStepId fromStepId,
            WorkflowStepId toStepId,
            WorkflowDecision decision,
            boolean reasonRequired,
            boolean commentRequired) {

        definitionPolicy.requireDefinitionCanBeModified(definition);

        WorkflowTransition transition = WorkflowTransition.create(
                definition.id(),
                fromStepId,
                toStepId,
                decision,
                reasonRequired,
                commentRequired);

        WorkflowDefinition updatedDefinition = definition.addTransition(transition);
        transitionPolicy.requireTransitionBelongsToDefinition(updatedDefinition, transition);

        return updatedDefinition;
    }

    public WorkflowDefinition activate(WorkflowDefinition definition) {
        definitionPolicy.requireDefinitionCanBeActivated(definition);
        return definition.activate();
    }

    public WorkflowDefinition deactivate(WorkflowDefinition definition) {
        Objects.requireNonNull(definition, "Workflow definition must not be null.");
        return definition.deactivate();
    }

    public WorkflowDefinition retire(WorkflowDefinition definition) {
        definitionPolicy.requireDefinitionCanBeRetired(definition);
        return definition.retire();
    }

    public WorkflowStep requireInitialStep(WorkflowDefinition definition) {
        definitionPolicy.requireDefinitionCanStartInstance(definition);

        return definition.firstStep()
                .orElseThrow(() -> new BusinessRuleViolationException("Workflow definition has no initial step."));
    }
}
