/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityBoundaryPolicy
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.policy
 *
 * @Description : Validates integrity ownership boundaries.
 *
 */
package dz.sh.hidra.modules.integrity.domain.policy;

/**
 * Validates integrity ownership boundaries.
 */
public final class IntegrityBoundaryPolicy {

    private IntegrityBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenTablePrefix(String tableName) {
        if (tableName == null) {
            return false;
        }
        return tableName.startsWith("hidra_topology_")
                || tableName.startsWith("hidra_asset_")
                || tableName.startsWith("hidra_assets_")
                || tableName.startsWith("hidra_hse_")
                || tableName.startsWith("hidra_incident_");
    }

    public static boolean mayCreateMaintenanceWorkOrderDirectly() {
        return false;
    }
}
