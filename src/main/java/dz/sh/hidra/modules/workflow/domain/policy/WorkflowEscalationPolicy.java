/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowEscalationPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.policy
 *
 * @Description : Domain policy for workflow escalation.
 *
 */
package dz.sh.hidra.modules.workflow.domain.policy;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowEscalationRule;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;

/**
 * Domain policy for workflow escalation.
 *
 * <p>Business role:
 * Verifies whether a task can be escalated according to task state, due date, and escalation rule.
 */
public final class WorkflowEscalationPolicy {

    /**
     * Ensures a task can be escalated by an active rule.
     *
     * @param task workflow task
     * @param rule escalation rule
     * @param referenceTime current/reference time
     */
    public void requireTaskCanBeEscalated(
            WorkflowTask task,
            WorkflowEscalationRule rule,
            Instant referenceTime) {

        Objects.requireNonNull(task, "Workflow task must not be null.");
        Objects.requireNonNull(rule, "Workflow escalation rule must not be null.");
        referenceTime = referenceTime == null ? Instant.now() : referenceTime;

        if (task.status().isClosed()) {
            throw new BusinessRuleViolationException("Closed workflow task cannot be escalated.");
        }

        if (!rule.active()) {
            throw new BusinessRuleViolationException("Inactive workflow escalation rule cannot be applied.");
        }

        if (!task.stepId().equals(rule.stepId())) {
            throw new BusinessRuleViolationException("Workflow escalation rule does not apply to the task step.");
        }

        if (task.dueDate() != null && !task.dueDate().isOverdueAt(referenceTime)) {
            throw new BusinessRuleViolationException("Workflow task is not overdue for escalation.");
        }
    }

    /**
     * Ensures escalation duration is valid.
     *
     * @param duration escalation duration
     */
    public void requirePositiveEscalationDuration(Duration duration) {
        Objects.requireNonNull(duration, "Workflow escalation duration must not be null.");

        if (duration.isZero() || duration.isNegative()) {
            throw new BusinessRuleViolationException("Workflow escalation duration must be positive.");
        }
    }
}
