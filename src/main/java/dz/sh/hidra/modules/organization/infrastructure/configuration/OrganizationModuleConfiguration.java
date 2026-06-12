/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationModuleConfiguration
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.configuration
 *
 * @Description : Organization infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.configuration;

/**
 * Organization infrastructure configuration.
 */
public record OrganizationModuleConfiguration(
        boolean hierarchySnapshotEnabled,
        boolean contactPointValidationEnabled
) {

    public static OrganizationModuleConfiguration defaults() {
        return new OrganizationModuleConfiguration(true, true);
    }
}
