/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmBoundaryPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.policy
 *
 * @Description : Validates alarm boundary rules.
 *
 */
package dz.sh.hidra.modules.alarm.domain.policy;

/**
 * Alarm boundary policy.
 */
public final class AlarmBoundaryPolicy {

    private AlarmBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenTablePrefix(String tableName) {
        if (tableName == null) {
            return false;
        }
        return tableName.startsWith("hidra_incident_")
                || tableName.startsWith("hidra_telemetry_")
                || tableName.startsWith("hidra_monitoring_")
                || tableName.startsWith("hidra_planning_")
                || tableName.startsWith("hidra_topology_")
                || tableName.startsWith("hidra_notification_")
                || tableName.startsWith("hidra_workflow_")
                || tableName.startsWith("hidra_audit_");
    }

    public static boolean isScadaActuationOperation(String operationName) {
        if (operationName == null) {
            return false;
        }
        String normalized = operationName.toLowerCase();
        return normalized.contains("closevalve")
                || normalized.contains("stoppump")
                || normalized.contains("writescada")
                || normalized.contains("triggeresi");
    }
}
