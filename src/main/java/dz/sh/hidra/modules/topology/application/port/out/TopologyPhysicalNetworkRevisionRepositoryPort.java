/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPhysicalNetworkRevisionRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Appends and verifies exact Topology physical source revisions.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision;
import java.util.Optional;

public interface TopologyPhysicalNetworkRevisionRepositoryPort {
    TopologyPhysicalNetworkRevision append(TopologyPhysicalNetworkRevision revision);

    Optional<TopologyPhysicalNetworkRevision> find(String sourceId, String revisionId);

    Optional<StoredRevision> findStored(String sourceId, String revisionId);

    record StoredRevision(TopologyPhysicalNetworkRevision revision, String payloadFormat, String sha256) {
        public StoredRevision {
            if (revision == null || sha256 == null || !sha256.matches("[0-9a-f]{64}")) {
                throw new InvalidTopologyValueException("Stored physical revision metadata is invalid.");
            }
            boolean regulatorPresent = revision.equipmentLinks().stream()
                    .anyMatch(link -> link.kind() == TopologyPhysicalNetworkRevision.EquipmentKind.REGULATOR);
            boolean acceptedFormat = ("HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V1".equals(payloadFormat) && !regulatorPresent)
                    || ("HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V2".equals(payloadFormat) && regulatorPresent);
            if (!acceptedFormat) {
                throw new InvalidTopologyValueException("Stored physical revision format/kind mismatch.");
            }
        }
    }
}
