/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseFacilityOwnershipContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.hse
 * @Description : Current active Topology FACILITY ownership projection, HSE consumer only.
 */
package dz.sh.hidra.modules.topology.application.contract.hse;

import java.util.Optional;
public interface HseFacilityOwnershipContract {
    record Facility(String id,String companyId,String code,String label){}
    Optional<Facility> active(String facilityId);
}
