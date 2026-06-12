/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningBoundaryPolicy
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.policy
 *
 * @Description : Validates planning boundary rules.
 *
 */
package dz.sh.hidra.modules.planning.domain.policy;

/**
 * Planning boundary policy.
 */
public final class PlanningBoundaryPolicy {

    private PlanningBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenTableName(String tableName) {
        if (tableName == null) {
            return false;
        }
        return tableName.contains("telemetry_reading")
                || tableName.contains("trusted_telemetry_reading")
                || tableName.contains("pipeline")
                || tableName.contains("facility")
                || tableName.contains("monitoring_deviation")
                || tableName.contains("alarm")
                || tableName.contains("incident")
                || tableName.contains("workflow_task")
                || tableName.contains("audit_record")
                || tableName.contains("custody_transfer_actual")
                || tableName.contains("invoice")
                || tableName.contains("contract_master")
                || tableName.contains("maintenance_work_order")
                || tableName.contains("simulation_run");
    }
}
