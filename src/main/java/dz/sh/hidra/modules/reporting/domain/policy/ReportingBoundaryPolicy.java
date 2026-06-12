/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingBoundaryPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.policy
 *
 * @Description : Validates reporting anti-corruption and reproducibility boundaries.
 *
 */
package dz.sh.hidra.modules.reporting.domain.policy;

import java.util.Locale;

/**
 * Validates reporting anti-corruption and reproducibility boundaries.
 */
public final class ReportingBoundaryPolicy {

    private ReportingBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenOperationalMutation(String operationName) {
        if (operationName == null) {
            return false;
        }
        String normalized = operationName.toLowerCase(Locale.ROOT);
        return normalized.contains("correct_reading")
                || normalized.contains("change_topology")
                || normalized.contains("revise_plan")
                || normalized.contains("acknowledge_alarm")
                || normalized.contains("close_incident")
                || normalized.contains("calculate_risk_score")
                || normalized.contains("decide_compliance")
                || normalized.contains("execute_work_order")
                || normalized.contains("scada")
                || normalized.contains("plc")
                || normalized.contains("rtu")
                || normalized.contains("sis")
                || normalized.contains("esd");
    }

    public static boolean isForbiddenForeignInternalImport(String typeName) {
        if (typeName == null) {
            return false;
        }
        String normalized = typeName.toLowerCase(Locale.ROOT);
        return normalized.contains(".infrastructure.")
                || normalized.contains(".domain.")
                || normalized.endsWith("jpaentity")
                || normalized.endsWith("aggregate");
    }

    public static boolean containsSecretMaterial(String value) {
        if (value == null) {
            return false;
        }
        String normalized = value.toLowerCase(Locale.ROOT);
        return normalized.contains("password")
                || normalized.contains("secret=")
                || normalized.contains("token=")
                || normalized.contains("apikey")
                || normalized.contains("private_key")
                || normalized.contains("credential_value");
    }
}
