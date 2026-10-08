/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningTargetTopologyReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.planning
 *
 * @Description : Exports typed asset identity and canonical snapshots for Planning targets.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.planning;

import java.util.Optional;

public interface PlanningTargetTopologyReferenceContract {
    Optional<Asset> resolve(String type, String id);
    record Asset(String id, String code, String name) { }
}
