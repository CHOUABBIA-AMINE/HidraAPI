/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : RiskAssessmentAssetOwnershipContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.risk
 * @Description : Topology-owned currently active Risk facility and company binding.
 */
package dz.sh.hidra.modules.topology.application.contract.risk;

import java.util.Optional;
public interface RiskAssessmentAssetOwnershipContract {
    record Facility(String id,String companyPartyId,String code) { }
    Optional<Facility> currentFacility(String facilityId);
}
