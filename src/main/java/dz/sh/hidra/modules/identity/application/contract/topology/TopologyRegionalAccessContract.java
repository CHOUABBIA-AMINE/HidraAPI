/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyRegionalAccessContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.topology
 *
 * @Description : Provides T33 scoped regional resource access evidence.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.topology;

import java.util.Objects;

/** Current scalar Identity decision for future Topology readers, not a durable grant. */
public interface TopologyRegionalAccessContract {
    enum ResourceKind { PIPELINE_SYSTEM, PIPELINE, PIPELINE_SEGMENT, FACILITY, EQUIPMENT }
    record Request(ResourceKind resourceKind, String resourceId, String companyPartyId) {
        public Request {
            Objects.requireNonNull(resourceKind, "resourceKind");
            if (resourceId == null || resourceId.isBlank()
                    || companyPartyId == null || companyPartyId.isBlank())
                throw new IllegalArgumentException("Exact resource and company references required");
        }
    }
    boolean permitted(Request request);
}
