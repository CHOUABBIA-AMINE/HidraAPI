/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopConfigurationExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.integration
 *
 * @Description : No-op configuration external reference resolver.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.integration;

/**
 * No-op configuration external reference resolver.
 */
public class NoopConfigurationExternalReferenceResolver implements ConfigurationExternalReferenceResolver {

    @Override
    public boolean moduleExists(String moduleName) {
        return true;
    }

    @Override
    public boolean workflowInstanceExists(String workflowInstanceId) {
        return true;
    }

    @Override
    public boolean actorExists(String actorId) {
        return true;
    }

    @Override
    public boolean secretReferenceExists(String secretReference) {
        return true;
    }
}
