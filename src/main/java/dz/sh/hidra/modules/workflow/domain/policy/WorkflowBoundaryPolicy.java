/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowBoundaryPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.policy
 *
 * @Description : Validates workflow ownership boundaries.
 *
 */
package dz.sh.hidra.modules.workflow.domain.policy;

import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;

/**
 * Validates workflow ownership boundaries.
 */
public final class WorkflowBoundaryPolicy {

    private WorkflowBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean ownsBusinessFact(String targetModule) {
        return false;
    }

    public static boolean isForbiddenOperation(String operationName) {
        if (operationName == null) {
            return false;
        }
        String normalized = operationName.toLowerCase();
        return normalized.contains("scada")
                || normalized.contains("plc")
                || normalized.contains("rtu")
                || normalized.contains("telemetry_value")
                || normalized.contains("incident_status")
                || normalized.contains("alarm_status")
                || normalized.contains("audit_storage")
                || normalized.contains("notification_delivery");
    }

    public static boolean isNonTerminalInstanceStatus(WorkflowInstanceStatus status) {
        return status == WorkflowInstanceStatus.DRAFT
                || status == WorkflowInstanceStatus.STARTED
                || status == WorkflowInstanceStatus.IN_PROGRESS
                || status == WorkflowInstanceStatus.WAITING;
    }
}
