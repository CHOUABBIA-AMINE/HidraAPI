/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationBoundaryPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.policy
 *
 * @Description : Validates integration module ownership and safety boundaries.
 *
 */
package dz.sh.hidra.modules.integration.domain.policy;

import java.util.Locale;

/**
 * Validates integration module ownership and safety boundaries.
 */
public final class IntegrationBoundaryPolicy {

    private IntegrationBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenDirectTableWrite(String tableName) {
        if (tableName == null) {
            return false;
        }
        String normalized = tableName.toLowerCase(Locale.ROOT);
        return normalized.startsWith("hidra_telemetry_")
                || normalized.startsWith("hidra_topology_")
                || normalized.startsWith("hidra_planning_")
                || normalized.startsWith("hidra_monitoring_")
                || normalized.startsWith("hidra_alarm_")
                || normalized.startsWith("hidra_incident_")
                || normalized.startsWith("hidra_asset_")
                || normalized.startsWith("hidra_custody_")
                || normalized.startsWith("hidra_hse_");
    }

    public static boolean isForbiddenOtActuation(String operationName) {
        if (operationName == null) {
            return false;
        }
        String normalized = operationName.toLowerCase(Locale.ROOT);
        return normalized.contains("valve")
                || normalized.contains("pump")
                || normalized.contains("compressor")
                || normalized.contains("plc")
                || normalized.contains("rtu")
                || normalized.contains("scada_command")
                || normalized.contains("sis")
                || normalized.contains("esd");
    }

    public static boolean isForbiddenSecretMaterial(String value) {
        if (value == null) {
            return false;
        }
        String normalized = value.toLowerCase(Locale.ROOT);
        return normalized.contains("password")
                || normalized.contains("secret")
                || normalized.contains("token=")
                || normalized.contains("private_key")
                || normalized.contains("apikey")
                || normalized.contains("credential_value");
    }
}
