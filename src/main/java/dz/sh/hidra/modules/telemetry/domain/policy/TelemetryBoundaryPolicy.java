/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryBoundaryPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.policy
 *
 * @Description : Validates telemetry boundary rules.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.policy;

/**
 * Telemetry boundary policy.
 */
public final class TelemetryBoundaryPolicy {

    private TelemetryBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenTableName(String tableName) {
        if (tableName == null) {
            return false;
        }
        return tableName.contains("pipeline")
                || tableName.contains("facility")
                || tableName.contains("alarm")
                || tableName.contains("incident")
                || tableName.contains("workflow_task")
                || tableName.contains("audit_record")
                || tableName.contains("risk_score")
                || tableName.contains("simulation_run")
                || tableName.contains("custody_fiscal_record")
                || tableName.contains("work_order");
    }
}
