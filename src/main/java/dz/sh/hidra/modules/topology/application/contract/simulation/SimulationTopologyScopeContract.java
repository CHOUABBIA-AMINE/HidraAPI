/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationTopologyScopeContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.simulation
 *
 * @Description : Topology-owned public contract for Simulation model scope validation.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.simulation;

/**
 * Resolves Simulation topology scope references without exposing Topology domain or persistence types.
 */
public interface SimulationTopologyScopeContract {

    ScopeResolution resolve(String scopeType, String scopeId);

    record ScopeResolution(
            boolean supported,
            boolean exists,
            boolean eligible
    ) {

        public static ScopeResolution unsupported() {
            return new ScopeResolution(false, false, false);
        }

        public static ScopeResolution missing() {
            return new ScopeResolution(true, false, false);
        }

        public static ScopeResolution resolved(boolean eligible) {
            return new ScopeResolution(true, true, eligible);
        }
    }
}
