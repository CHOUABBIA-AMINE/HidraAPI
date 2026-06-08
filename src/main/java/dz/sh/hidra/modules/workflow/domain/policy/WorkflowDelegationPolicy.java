/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDelegationPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.policy
 *
 * @Description : Domain policy for workflow delegation.
 *
 */
package dz.sh.hidra.modules.workflow.domain.policy;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;

/**
 * Domain policy for workflow delegation.
 *
 * <p>Business role:
 * Verifies whether a task can be delegated and whether delegation target/reason are present.
 */
public final class WorkflowDelegationPolicy {

    /**
     * Ensures a task can be delegated.
     *
     * @param task workflow task
     * @param fromActor actor delegating the task
     * @param toActor target actor
     * @param toOrganization target organization
     * @param reason delegation reason
     */
    public void requireTaskCanBeDelegated(
            WorkflowTask task,
            WorkflowActorReference fromActor,
            WorkflowActorReference toActor,
            WorkflowOrganizationReference toOrganization,
            WorkflowReasonReference reason) {

        Objects.requireNonNull(task, "Workflow task must not be null.");
        Objects.requireNonNull(fromActor, "Delegating actor must not be null.");
        Objects.requireNonNull(reason, "Workflow delegation requires a reason.");

        if (task.status().isClosed()) {
            throw new BusinessRuleViolationException("Closed workflow task cannot be delegated.");
        }

        if (toActor == null && toOrganization == null) {
            throw new BusinessRuleViolationException("Workflow delegation requires target actor or organization.");
        }

        if (toActor != null && toActor.equals(fromActor)) {
            throw new BusinessRuleViolationException("Workflow task cannot be delegated to the same actor.");
        }
    }
}
