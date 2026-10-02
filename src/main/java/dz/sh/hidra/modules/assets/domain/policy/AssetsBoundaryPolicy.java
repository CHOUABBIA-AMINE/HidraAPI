/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsBoundaryPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.policy
 *
 * @Description : Validates assets ownership boundaries.
 *
 */
package dz.sh.hidra.modules.assets.domain.policy;

/**
 * Validates assets ownership boundaries.
 */
public final class AssetsBoundaryPolicy {

    private AssetsBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenTablePrefix(String tableName) {
        if (tableName == null) {
            return false;
        }
        return tableName.startsWith("hidra_assets_")
                || tableName.startsWith("hidra_topology_")
                || tableName.startsWith("hidra_integrity_")
                || tableName.startsWith("hidra_hse_")
                || tableName.startsWith("hidra_custody_");
    }

    public static boolean ownsPhysicalTopologyIdentity() {
        return false;
    }

    public static boolean ownsMaintenanceWorkOrders() {
        return true;
    }
}
