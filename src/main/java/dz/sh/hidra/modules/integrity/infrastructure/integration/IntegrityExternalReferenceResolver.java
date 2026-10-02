/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.integration
 *
 * @Description : Resolves integrity external references without importing foreign domains.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.integration;

/**
 * Resolves integrity external references without importing foreign domain models.
 */
public interface IntegrityExternalReferenceResolver {

    boolean topologyAssetExists(String topologyAssetTypeCode, String topologyAssetId);

    boolean hseCaseExists(String hseCaseId);

    boolean incidentExists(String incidentId);

    boolean documentExists(String documentReferenceId);
}
