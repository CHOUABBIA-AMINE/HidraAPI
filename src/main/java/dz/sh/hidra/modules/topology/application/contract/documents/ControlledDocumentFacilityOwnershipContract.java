/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ControlledDocumentFacilityOwnershipContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.documents
 * @Description : Current Topology facility company identity and display snapshot.
 */
package dz.sh.hidra.modules.topology.application.contract.documents;

import java.util.Optional;

public interface ControlledDocumentFacilityOwnershipContract {
    record Facility(String id,String companyId,String code,String label){}
    Optional<Facility> currentFacility(String facilityId);
}
