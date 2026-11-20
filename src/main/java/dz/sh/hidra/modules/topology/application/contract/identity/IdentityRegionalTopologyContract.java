/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityRegionalTopologyContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.identity
 *
 * @Description : Provides T33 scoped regional resource access evidence.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.identity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/** Topology-owned, current typed lineage with company context and no JPA or map payloads. */
public interface IdentityRegionalTopologyContract {
    enum Kind { PIPELINE_SYSTEM, PIPELINE, PIPELINE_SEGMENT, FACILITY, EQUIPMENT }
    record Scope(String type, String targetId) {
        public Scope {
            if (type == null || type.isBlank() || targetId == null || targetId.isBlank())
                throw new IllegalArgumentException("Exact owner scope required");
        }
    }
    record Resolution(Kind kind, String resourceId, String companyPartyId, List<Scope> ancestry) {
        public Resolution {
            if (kind == null || resourceId == null || resourceId.isBlank()
                    || companyPartyId == null || companyPartyId.isBlank()
                    || ancestry == null || ancestry.isEmpty())
                throw new IllegalArgumentException("Incomplete authoritative lineage");
            ancestry = List.copyOf(ancestry);
        }
    }
    Optional<Resolution> resolve(Kind kind, String resourceId, String companyPartyId, Instant at);
}
