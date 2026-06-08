/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.policy
 *
 * @Description : Domain policy for workflow definitions.
 *
 */
package dz.sh.hidra.modules.workflow.domain.policy;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;

/**
 * Domain policy for workflow definitions.
 *
 * <p>Business role:
 * Verifies whether workflow definitions can be activated, changed, retired, or used to start
 * workflow instances.
 *
 * <p>Architecture role:
 * Pure domain policy. It does not persist definitions, start instances, or call external modules.
 */
public final class WorkflowDefinitionPolicy {

    /**
     * Ensures a workflow definition can be activated.
     *
     * @param definition workflow definition
     */
    public void requireDefinitionCanBeActivated(WorkflowDefinition definition) {
        Objects.requireNonNull(definition, "Workflow definition must not be null.");

        if (WorkflowDefinitionStatus.RETIRED.equals(definition.status())) {
            throw new BusinessRuleViolationException("Retired workflow definition cannot be activated.");
        }

        if (definition.steps().isEmpty()) {
            throw new BusinessRuleViolationException("Workflow definition requires at least one step before activation.");
        }
    }

    /**
     * Ensures a workflow definition can be modified.
     *
     * @param definition workflow definition
     */
    public void requireDefinitionCanBeModified(WorkflowDefinition definition) {
        Objects.requireNonNull(definition, "Workflow definition must not be null.");

        if (WorkflowDefinitionStatus.ACTIVE.equals(definition.status())) {
            throw new BusinessRuleViolationException("Active workflow definition cannot be modified directly.");
        }

        if (WorkflowDefinitionStatus.RETIRED.equals(definition.status())) {
            throw new BusinessRuleViolationException("Retired workflow definition cannot be modified.");
        }
    }

    /**
     * Ensures a workflow definition can start a workflow instance.
     *
     * @param definition workflow definition
     */
    public void requireDefinitionCanStartInstance(WorkflowDefinition definition) {
        Objects.requireNonNull(definition, "Workflow definition must not be null.");

        if (!definition.status().canStartInstance()) {
            throw new BusinessRuleViolationException("Only active workflow definitions can start workflow instances.");
        }

        if (definition.firstStep().isEmpty()) {
            throw new BusinessRuleViolationException("Workflow definition requires an initial step before starting an instance.");
        }
    }

    /**
     * Ensures a workflow definition can be retired.
     *
     * @param definition workflow definition
     */
    public void requireDefinitionCanBeRetired(WorkflowDefinition definition) {
        Objects.requireNonNull(definition, "Workflow definition must not be null.");

        if (WorkflowDefinitionStatus.RETIRED.equals(definition.status())) {
            throw new BusinessRuleViolationException("Workflow definition is already retired.");
        }
    }
}
