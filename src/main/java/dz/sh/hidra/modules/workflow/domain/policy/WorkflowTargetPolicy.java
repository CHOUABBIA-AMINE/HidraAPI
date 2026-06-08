/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTargetPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.policy
 *
 * @Description : Domain policy for workflow target references.
 *
 */
package dz.sh.hidra.modules.workflow.domain.policy;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;

/**
 * Domain policy for workflow target references.
 *
 * <p>Business role:
 * Verifies whether a target can be used to start a workflow instance.
 *
 * <p>Architecture role:
 * This policy validates the neutral target reference only. It does not load telemetry, topology,
 * planning, monitoring, incident, audit, integration, analytics, reporting, or notification objects.
 */
public final class WorkflowTargetPolicy {

    /**
     * Ensures a target can start a workflow instance.
     *
     * @param target workflow target
     */
    public void requireTargetCanStartWorkflow(WorkflowTargetReference target) {
        Objects.requireNonNull(target, "Workflow target must not be null.");

        if (target.targetId() == null || target.targetId().isBlank()) {
            throw new BusinessRuleViolationException("Workflow target id must not be blank.");
        }

        if (target.targetModule() == null || target.targetModule().isBlank()) {
            throw new BusinessRuleViolationException("Workflow target module must not be blank.");
        }
    }

    /**
     * Ensures the first workflow baseline targets telemetry readings only.
     *
     * @param target workflow target
     */
    public void requireTelemetryReadingTarget(WorkflowTargetReference target) {
        requireTargetCanStartWorkflow(target);

        if (!target.isTelemetryReading()) {
            throw new BusinessRuleViolationException("Workflow v1 supports TELEMETRY_READING targets only.");
        }
    }
}
