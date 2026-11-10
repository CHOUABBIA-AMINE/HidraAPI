/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningAssetOwnershipContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.planning
 *
 * @Description : Resolves current exact Topology ownership for Planning.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.planning;

import java.util.Optional;

public interface PlanningAssetOwnershipContract {

    Optional<String> activeOwner(String type, String id);
}
