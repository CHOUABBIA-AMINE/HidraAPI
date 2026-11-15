/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrityMaintenanceTopologyOwnershipContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.integrity
 * @Description : T18 governed Integrity and Assets maintenance handoff boundary.
 */
package dz.sh.hidra.modules.topology.application.contract.integrity;

import java.util.Optional;
public interface IntegrityMaintenanceTopologyOwnershipContract {
    record Owned(String assetType,String assetId,String companyId) { }
    Optional<Owned> currentOwner(String type,String id);
}
