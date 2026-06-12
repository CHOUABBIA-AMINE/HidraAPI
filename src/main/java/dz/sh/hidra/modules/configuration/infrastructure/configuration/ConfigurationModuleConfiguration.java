/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationModuleConfiguration
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.configuration
 *
 * @Description : Configuration infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.configuration;

/**
 * Configuration infrastructure configuration.
 */
public record ConfigurationModuleConfiguration(
        boolean businessTaxonomyOwnershipBlocked,
        boolean secretValuePersistenceBlocked,
        boolean approvalWorkflowEnabled,
        boolean resolvedSnapshotEnabled
) {

    public static ConfigurationModuleConfiguration defaults() {
        return new ConfigurationModuleConfiguration(true, true, true, true);
    }
}
