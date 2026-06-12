/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationSafetyPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.policy
 *
 * @Description : Validates simulation decision-support and safety boundaries.
 *
 */
package dz.sh.hidra.modules.simulation.domain.policy;

import java.util.Locale;

/**
 * Validates simulation decision-support and safety boundaries.
 */
public final class SimulationSafetyPolicy {

    private SimulationSafetyPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenActuation(String operationName) {
        if (operationName == null) {
            return false;
        }
        String normalized = operationName.toLowerCase(Locale.ROOT);
        return normalized.contains("scada")
                || normalized.contains("plc")
                || normalized.contains("rtu")
                || normalized.contains("sis")
                || normalized.contains("esd")
                || normalized.contains("open_valve")
                || normalized.contains("close_valve")
                || normalized.contains("start_pump")
                || normalized.contains("stop_pump")
                || normalized.contains("start_compressor")
                || normalized.contains("stop_compressor");
    }

    public static boolean isForeignTableWrite(String tableName) {
        if (tableName == null) {
            return false;
        }
        String normalized = tableName.toLowerCase(Locale.ROOT);
        return normalized.startsWith("hidra_topology_")
                || normalized.startsWith("hidra_telemetry_")
                || normalized.startsWith("hidra_planning_")
                || normalized.startsWith("hidra_monitoring_")
                || normalized.startsWith("hidra_alarm_")
                || normalized.startsWith("hidra_incident_")
                || normalized.startsWith("hidra_asset_")
                || normalized.startsWith("hidra_integrity_")
                || normalized.startsWith("hidra_custody_");
    }
}
