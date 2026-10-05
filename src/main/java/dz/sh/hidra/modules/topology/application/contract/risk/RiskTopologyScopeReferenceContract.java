/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskTopologyScopeReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.risk
 *
 * @Description : Topology-owned typed scope resolution exported to Risk.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.risk;

import java.util.Optional;

public interface RiskTopologyScopeReferenceContract {

    Optional<ScopeView> resolve(String scopeType, String scopeId);

    record ScopeView(String id, String code, String label) { }
}
