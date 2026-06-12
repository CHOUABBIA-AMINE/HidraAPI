/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyBoundaryPolicy
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.policy
 *
 * @Description : Validates custody ownership boundaries.
 *
 */
package dz.sh.hidra.modules.custody.domain.policy;

/**
 * Validates custody ownership boundaries.
 */
public final class CustodyBoundaryPolicy {

    private CustodyBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenTablePrefix(String tableName) {
        if (tableName == null) {
            return false;
        }
        return tableName.startsWith("hidra_telemetry_")
                || tableName.startsWith("hidra_planning_")
                || tableName.startsWith("hidra_finance_")
                || tableName.startsWith("hidra_erp_")
                || tableName.startsWith("hidra_topology_");
    }

    public static boolean ownsTelemetryReading() {
        return false;
    }

    public static boolean ownsFinancePosting() {
        return false;
    }
}
