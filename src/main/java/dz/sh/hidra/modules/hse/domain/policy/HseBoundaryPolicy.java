/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseBoundaryPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.policy
 *
 * @Description : Validates HSE ownership boundaries.
 *
 */
package dz.sh.hidra.modules.hse.domain.policy;

/**
 * Validates HSE ownership boundaries.
 */
public final class HseBoundaryPolicy {

    private HseBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenTablePrefix(String tableName) {
        if (tableName == null) {
            return false;
        }
        return tableName.startsWith("hidra_incident_")
                || tableName.startsWith("hidra_alarm_")
                || tableName.startsWith("hidra_leak_detection_")
                || tableName.startsWith("hidra_integrity_")
                || tableName.startsWith("hidra_asset_")
                || tableName.startsWith("hidra_topology_");
    }

    public static boolean isForbiddenOperationalControl(String operationName) {
        if (operationName == null) {
            return false;
        }
        String normalized = operationName.toLowerCase();
        return normalized.contains("scada")
                || normalized.contains("plc")
                || normalized.contains("rtu")
                || normalized.contains("valve")
                || normalized.contains("pump")
                || normalized.contains("compressor")
                || normalized.contains("esd")
                || normalized.contains("sis");
    }
}
