/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegionalTopologyRelationshipPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Provides T33 scoped regional resource access evidence.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.application.contract.identity.IdentityRegionalTopologyContract;
import java.time.Instant;
import java.util.Optional;

/** Topology-owned lookup of existing, active, exact parent-child relations. */
public interface RegionalTopologyRelationshipPort {
    Optional<IdentityRegionalTopologyContract.Resolution> resolve(
            IdentityRegionalTopologyContract.Kind kind, String resourceId,
            String companyPartyId, Instant at);
}
