/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringBoundaryPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.policy
 *
 * @Description : Validates monitoring boundary rules.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.policy;

/**
 * Monitoring boundary policy.
 */
public final class MonitoringBoundaryPolicy {

    private MonitoringBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenTablePrefix(String tableName) {
        if (tableName == null) {
            return false;
        }
        return tableName.startsWith("hidra_alarm_")
                || tableName.startsWith("hidra_incident_")
                || tableName.startsWith("hidra_leak_detection_")
                || tableName.startsWith("hidra_telemetry_")
                || tableName.startsWith("hidra_planning_");
    }
}
