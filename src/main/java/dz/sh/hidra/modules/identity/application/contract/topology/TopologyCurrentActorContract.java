/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyCurrentActorContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.topology
 *
 * @Description : Exports the trusted current human actor reference for Topology.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.topology;

import java.util.Optional;

/** Identity reference only; membership, scoped permission and revocation are separate decisions. */
public interface TopologyCurrentActorContract {

    Optional<Actor> currentActor();

    record Actor(String userId) {
        public Actor {
            if (userId == null || userId.isBlank()) {
                throw new IllegalArgumentException("Actor userId must not be blank.");
            }
            userId = userId.trim();
        }
    }
}
