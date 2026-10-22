/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityModuleConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-22
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

    /** Purpose-specific infrastructure binding; disabled until explicitly provisioned. */
    public static final String ORGANIZATION_COMMIT_PROOF_PREFIX = "hidra.identity.organization-commit-proof";

    public static IdentityModuleConfiguration defaults() {
        return new IdentityModuleConfiguration(false, true);
    }
}

