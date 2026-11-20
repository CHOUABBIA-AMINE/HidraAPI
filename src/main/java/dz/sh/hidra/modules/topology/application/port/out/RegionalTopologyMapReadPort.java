/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegionalTopologyMapReadPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Exposes Topology-owned scalar regional graph queries.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * No foreign JPA entities or authorization decisions cross this port.
 * Candidate traversal is company-qualified, keyset ordered and bounded.
 * Metadata and coordinates are hydrated only after an Identity decision.
 */
public interface RegionalTopologyMapReadPort {
    record Candidate(String id, String parentId, String fromNodeId,
                     String toNodeId, String facilityId) { }

    record Detail(String id, String code, String nameAr, String nameFr, String nameEn,
                  String status, String facilityKind, String nodeType,
                  String pipelineSystemId, String pipelineType, String segmentType,
                  String flowDirection, String connectionType,
                  BigDecimal longitude, BigDecimal latitude, BigDecimal elevationMeters) { }

    /** Only the four T33-resource kinds can be traversed by company. */
    List<Candidate> candidates(String layer, String companyPartyId, String afterId,
                               int batchSize, Instant at);

    /** Nodes by permitted segment endpoints; connections by permitted segment IDs. */
    List<Candidate> dependents(String layer, List<String> permittedEndpointOrSegmentIds);

    /** Read protected fields only for pre-authorized exact IDs. */
    Map<String, Detail> hydrate(String layer, List<String> authorizedIds);

    /** Recheck status and exact graph incidence before returning a projection. */
    boolean unchanged(String layer, Candidate original, String companyPartyId, Instant at);
}
