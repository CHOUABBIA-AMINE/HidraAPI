/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyOperationalScopeTargetContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.organization
 *
 * @Description : Deliberate cross-module Topology contract exported to Organization operational-scope resolution.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.organization;

import java.util.Optional;

/**
 * Exposes current topology-owned target identity and assignability without leaking
 * topology domain models, repositories, persistence entities, or infrastructure.
 *
 * <p>The returned ID is the topology owner's native stable identifier. Code and
 * name are current display attributes and are never authoritative registry identity.</p>
 */
public interface TopologyOperationalScopeTargetContract {

    Optional<TargetView> resolvePipelineSystem(String targetId);

    Optional<TargetView> resolvePipeline(String targetId);

    Optional<TargetView> resolveFacility(String targetId);

    Optional<TargetView> resolveEquipment(String targetId);

    /**
     * Current read-only target projection for cross-module resolution.
     *
     * @param id owner-native stable identifier
     * @param code current owner code, nullable when unavailable
     * @param name current display name, nullable when unavailable
     * @param assignable whether the owner currently permits a new responsibility assignment
     */
    record TargetView(
            String id,
            String code,
            String name,
            boolean assignable
    ) {
        public TargetView {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("Topology target ID must not be blank.");
            }
            id = id.trim();
            code = normalize(code);
            name = normalize(name);
        }

        private static String normalize(String value) {
            return value == null || value.isBlank() ? null : value.trim();
        }
    }
}
