/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationTopologyTargetContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.simulation
 *
 * @Description : Resolves scalar candidate targets without changing model-scope semantics.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.simulation;

public interface SimulationTopologyTargetContract {
    /** Exact supported type and existing owner object; no lifecycle eligibility is implied. */
    boolean exists(String targetType, String targetId);
}
