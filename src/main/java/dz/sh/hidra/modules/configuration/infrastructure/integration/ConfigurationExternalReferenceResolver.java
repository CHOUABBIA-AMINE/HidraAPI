/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.integration
 *
 * @Description : Resolves configuration external references without importing foreign domains.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.integration;

/**
 * Resolves configuration external references without importing foreign domain models.
 */
public interface ConfigurationExternalReferenceResolver {

    boolean moduleExists(String moduleName);

    boolean workflowInstanceExists(String workflowInstanceId);

    boolean actorExists(String actorId);

    boolean secretReferenceExists(String secretReference);
}
