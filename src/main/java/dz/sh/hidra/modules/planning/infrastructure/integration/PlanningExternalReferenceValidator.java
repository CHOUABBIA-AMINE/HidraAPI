/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningExternalReferenceValidator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.integration
 *
 * @Description : Validates neutral references for planning.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.integration;

/**
 * Validates neutral references for planning without importing external module domain models.
 */
public interface PlanningExternalReferenceValidator {

    boolean topologyAssetExists(String topologyAssetType, String topologyAssetId);

    boolean partyExists(String partyId);
}
