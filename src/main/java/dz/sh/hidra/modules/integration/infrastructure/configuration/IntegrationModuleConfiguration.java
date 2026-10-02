/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationModuleConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.configuration
 *
 * @Description : Integration infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.configuration;

/**
 * Integration infrastructure configuration.
 */
public record IntegrationModuleConfiguration(
        boolean directTargetTableWritesBlocked,
        boolean otActuationBlocked,
        boolean secretMaterialPersistenceBlocked,
        boolean deadLetterReplayEnabled
) {

    public static IntegrationModuleConfiguration defaults() {
        return new IntegrationModuleConfiguration(true, true, true, true);
    }
}
