/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsBoundaryPolicy
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.policy
 *
 * @Description : Validates analytics source-of-truth boundaries.
 *
 */
package dz.sh.hidra.modules.analytics.domain.policy;

import java.util.Locale;

/**
 * Validates analytics source-of-truth boundaries.
 */
public final class AnalyticsBoundaryPolicy {

    private AnalyticsBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenOperationalMutation(String operationName) {
        if (operationName == null) {
            return false;
        }
        String normalized = operationName.toLowerCase(Locale.ROOT);
        return normalized.contains("close_incident")
                || normalized.contains("acknowledge_alarm")
                || normalized.contains("validate_telemetry")
                || normalized.contains("change_topology")
                || normalized.contains("create_custody_ticket")
                || normalized.contains("execute_work_order")
                || normalized.contains("approve_business_decision")
                || normalized.contains("scada")
                || normalized.contains("plc")
                || normalized.contains("rtu")
                || normalized.contains("sis")
                || normalized.contains("esd");
    }

    public static boolean isForeignAggregatePayload(String sourceName) {
        if (sourceName == null) {
            return false;
        }
        String normalized = sourceName.toLowerCase(Locale.ROOT);
        return normalized.endsWith("domain")
                || normalized.endsWith("jpaentity")
                || normalized.contains(".domain.")
                || normalized.contains(".infrastructure.persistence.");
    }
}
