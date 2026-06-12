/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyBoundaryPolicy
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.policy
 *
 * @Description : Validates topology boundary rules.
 *
 */
package dz.sh.hidra.modules.topology.domain.policy;

public final class TopologyBoundaryPolicy {
    private TopologyBoundaryPolicy() { throw new UnsupportedOperationException("Utility class must not be instantiated."); }
    public static boolean isForbiddenTablePrefix(String tableName) {
        if (tableName == null) return false;
        return tableName.startsWith("hidra_asset_") || tableName.startsWith("hidra_assets_") || tableName.startsWith("hidra_integrity_") || tableName.startsWith("hidra_hse_") || tableName.startsWith("hidra_custody_") || tableName.startsWith("hidra_telemetry_");
    }
}
