/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsExternalReferenceResolver
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.integration
 *
 * @Description : Resolves assets external references without importing foreign domains.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.integration;

/**
 * Resolves assets external references without importing foreign domain models.
 */
public interface AssetsExternalReferenceResolver {

    boolean topologyAssetExists(String topologyAssetTypeCode, String topologyAssetId);

    boolean partyExists(String partyId);

    boolean documentExists(String documentReferenceId);

    boolean integrityRecommendationExists(String recommendationId);

    boolean telemetryReadingExists(String readingReferenceId);
}
