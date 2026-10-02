/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityModuleConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.configuration
 *
 * @Description : Defines identity infrastructure configuration values.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.configuration;

/**
 * Framework-neutral identity module infrastructure configuration.
 *
 * @param externalSynchronizationEnabled whether external synchronization is enabled
 * @param authorizationDecisionPersistenceEnabled whether decisions are persisted
 */
public record IdentityModuleConfiguration(
        boolean externalSynchronizationEnabled,
        boolean authorizationDecisionPersistenceEnabled
) {

    public static IdentityModuleConfiguration defaults() {
        return new IdentityModuleConfiguration(false, true);
    }
}
