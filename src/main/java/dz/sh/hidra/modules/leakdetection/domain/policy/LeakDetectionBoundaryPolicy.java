/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionBoundaryPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.policy
 *
 * @Description : Validates leak detection boundary and safety rules.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.policy;

/**
 * Leak detection boundary and safety policy.
 */
public final class LeakDetectionBoundaryPolicy {

    private LeakDetectionBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenTablePrefix(String tableName) {
        if (tableName == null) {
            return false;
        }
        return tableName.startsWith("hidra_leakdetection_")
                || tableName.startsWith("hidra_alarm_")
                || tableName.startsWith("hidra_incident_")
                || tableName.startsWith("hidra_monitoring_")
                || tableName.startsWith("hidra_topology_")
                || tableName.startsWith("hidra_telemetry_");
    }

    public static boolean isForbiddenControlOperation(String operationName) {
        if (operationName == null) {
            return false;
        }
        String normalized = operationName.toLowerCase();
        return normalized.contains("valve")
                || normalized.contains("pump")
                || normalized.contains("compressor")
                || normalized.contains("plc")
                || normalized.contains("rtu")
                || normalized.contains("scada")
                || normalized.contains("esd")
                || normalized.contains("sis");
    }
}
