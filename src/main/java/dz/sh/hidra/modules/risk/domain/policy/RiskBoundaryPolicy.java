/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskBoundaryPolicy
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.policy
 *
 * @Description : Validates risk ownership boundaries.
 *
 */
package dz.sh.hidra.modules.risk.domain.policy;

/**
 * Validates risk ownership boundaries.
 */
public final class RiskBoundaryPolicy {

    private RiskBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenOwnershipTable(String tableName) {
        if (tableName == null) {
            return false;
        }
        return tableName.startsWith("hidra_telemetry_")
                || tableName.startsWith("hidra_monitoring_")
                || tableName.startsWith("hidra_alarm_")
                || tableName.startsWith("hidra_incident_")
                || tableName.startsWith("hidra_hse_")
                || tableName.startsWith("hidra_integrity_")
                || tableName.startsWith("hidra_asset_")
                || tableName.startsWith("hidra_simulation_")
                || tableName.startsWith("hidra_workflow_")
                || tableName.startsWith("hidra_audit_")
                || tableName.startsWith("hidra_analytics_")
                || tableName.startsWith("hidra_reporting_");
    }
}
