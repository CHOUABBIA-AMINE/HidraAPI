/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopAssetsExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.integration
 *
 * @Description : No-op assets external reference resolver.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.integration;

/**
 * No-op assets external reference resolver.
 */
public class NoopAssetsExternalReferenceResolver implements AssetsExternalReferenceResolver {

    @Override
    public boolean topologyAssetExists(String topologyAssetTypeCode, String topologyAssetId) {
        return true;
    }

    @Override
    public boolean partyExists(String partyId) {
        return true;
    }

    @Override
    public boolean documentExists(String documentReferenceId) {
        return true;
    }

    @Override
    public boolean integrityRecommendationExists(String recommendationId) {
        return true;
    }

    @Override
    public boolean telemetryReadingExists(String readingReferenceId) {
        return true;
    }
}
