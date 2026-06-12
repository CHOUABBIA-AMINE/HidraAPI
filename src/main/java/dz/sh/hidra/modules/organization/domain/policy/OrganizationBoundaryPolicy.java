/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationBoundaryPolicy
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.policy
 *
 * @Description : Validates organization boundary rules.
 *
 */
package dz.sh.hidra.modules.organization.domain.policy;

/**
 * Organization boundary policy.
 */
public final class OrganizationBoundaryPolicy {

    private OrganizationBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean isForbiddenTablePrefix(String tableName) {
        if (tableName == null) {
            return false;
        }
        return tableName.startsWith("hidra_identity_")
                || tableName.startsWith("hidra_topology_")
                || tableName.startsWith("hidra_workflow_")
                || tableName.startsWith("hidra_audit_")
                || tableName.startsWith("hidra_organization_");
    }
}
