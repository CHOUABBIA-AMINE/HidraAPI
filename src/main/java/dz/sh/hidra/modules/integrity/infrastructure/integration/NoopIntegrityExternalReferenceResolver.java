/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopIntegrityExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.integration
 *
 * @Description : No-op integrity external reference resolver.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.integration;

/**
 * No-op integrity external reference resolver.
 */
public class NoopIntegrityExternalReferenceResolver implements IntegrityExternalReferenceResolver {

    @Override
    public boolean topologyAssetExists(String topologyAssetTypeCode, String topologyAssetId) {
        return true;
    }

    @Override
    public boolean hseCaseExists(String hseCaseId) {
        return true;
    }

    @Override
    public boolean incidentExists(String incidentId) {
        return true;
    }

    @Override
    public boolean documentExists(String documentReferenceId) {
        return true;
    }
}
