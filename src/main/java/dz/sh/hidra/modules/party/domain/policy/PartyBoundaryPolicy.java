/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyBoundaryPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.policy
 *
 * @Description : Validates party boundary rules.
 *
 */
package dz.sh.hidra.modules.party.domain.policy;

/**
 * Party boundary policy.
 */
public final class PartyBoundaryPolicy {

    private PartyBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenTablePrefix(String tableName) {
        if (tableName == null) {
            return false;
        }
        return tableName.startsWith("hidra_org_")
                || tableName.startsWith("hidra_identity_")
                || tableName.startsWith("hidra_asset_")
                || tableName.startsWith("hidra_custody_")
                || tableName.startsWith("hidra_contract_")
                || tableName.startsWith("hidra_finance_")
                || tableName.startsWith("hidra_erp_")
                || tableName.startsWith("hidra_topology_");
    }
}
