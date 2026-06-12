/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopPlanningExternalReferenceValidator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.integration
 *
 * @Description : No-op planning external reference validator.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.integration;

/**
 * No-op planning external reference validator.
 */
public class NoopPlanningExternalReferenceValidator implements PlanningExternalReferenceValidator {

    @Override
    public boolean topologyAssetExists(String topologyAssetType, String topologyAssetId) {
        return true;
    }

    @Override
    public boolean partyExists(String partyId) {
        return true;
    }
}
