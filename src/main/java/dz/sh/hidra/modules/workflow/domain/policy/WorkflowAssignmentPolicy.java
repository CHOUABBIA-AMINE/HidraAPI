/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowAssignmentPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.policy
 *
 * @Description : Domain policy for workflow assignments.
 *
 */
package dz.sh.hidra.modules.workflow.domain.policy;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;

/**
 * Domain policy for workflow task assignments.
 *
 * <p>Business role:
 * Verifies whether tasks can be assigned, claimed, or completed.
 */
public final class WorkflowAssignmentPolicy {

    /**
     * Ensures a task can be assigned to an actor or organization unit.
     *
     * @param actor actor reference
     * @param organization organization reference
     */
    public void requireValidAssignmentTarget(
            WorkflowActorReference actor,
            WorkflowOrganizationReference organization) {

        if (actor == null && organization == null) {
            throw new BusinessRuleViolationException("Workflow assignment requires actor or organization unit.");
        }
    }

    /**
     * Ensures a task can be claimed by an actor.
     *
     * @param task workflow task
     * @param actor claiming actor
     */
    public void requireTaskCanBeClaimed(
            WorkflowTask task,
            WorkflowActorReference actor) {

        Objects.requireNonNull(task, "Workflow task must not be null.");
        Objects.requireNonNull(actor, "Workflow actor must not be null.");

        if (task.status().isClosed()) {
            throw new BusinessRuleViolationException("Closed workflow task cannot be claimed.");
        }

        if (WorkflowTaskStatus.CLAIMED.equals(task.status())) {
            throw new BusinessRuleViolationException("Workflow task is already claimed.");
        }
    }

    /**
     * Ensures a task can be completed.
     *
     * @param task workflow task
     * @param actor completing actor
     * @param completionStatus completion status
     */
    public void requireTaskCanBeCompleted(
            WorkflowTask task,
            WorkflowActorReference actor,
            WorkflowTaskStatus completionStatus) {

        Objects.requireNonNull(task, "Workflow task must not be null.");
        Objects.requireNonNull(actor, "Workflow actor must not be null.");
        Objects.requireNonNull(completionStatus, "Workflow task completion status must not be null.");

        if (task.status().isClosed()) {
            throw new BusinessRuleViolationException("Workflow task is already closed.");
        }

        if (!completionStatus.isClosed()) {
            throw new BusinessRuleViolationException("Workflow task completion status must be closed.");
        }
    }

    /**
     * Ensures a task can be reassigned.
     *
     * @param task workflow task
     * @param nextActor next actor
     * @param nextOrganization next organization
     */
    public void requireTaskCanBeReassigned(
            WorkflowTask task,
            WorkflowActorReference nextActor,
            WorkflowOrganizationReference nextOrganization) {

        Objects.requireNonNull(task, "Workflow task must not be null.");
        requireValidAssignmentTarget(nextActor, nextOrganization);

        if (task.status().isClosed()) {
            throw new BusinessRuleViolationException("Closed workflow task cannot be reassigned.");
        }
    }
}
