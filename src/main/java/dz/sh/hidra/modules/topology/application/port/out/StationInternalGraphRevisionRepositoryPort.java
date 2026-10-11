/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInternalGraphRevisionRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Defines owner-private exact append and read semantics for station graph revisions.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision;
import java.util.Optional;

/**
 * Owner-private contract; no storage or publication is performed by this interface.
 * Implementations reject blank identities and return absence only for an absent exact
 * facility/revision pair, never a latest revision or another station. Corruption fails.
 * Same canonical bytes replay safely; different bytes at the same identity conflict.
 * Reads verify payload format, bytes, digest and identity before returning domain data.
 * The predecessor field records supplied intent; it does not enact head selection,
 * predecessor existence checks, approval, withdrawal or compare-and-set publication.
 */
public interface StationInternalGraphRevisionRepositoryPort {
    /** Returns the stored canonical revision, including on an identical replay. */
    StationInternalGraphRevision append(StationInternalGraphRevision revision);

    Optional<StationInternalGraphRevision> find(String facilityId, String revisionId);

    Optional<StoredRevision> findStored(String facilityId, String revisionId);

    /** Metadata shape only: actual byte/digest equality is the adapter's responsibility. */
    record StoredRevision(StationInternalGraphRevision revision, String payloadFormat, String sha256) {
        public StoredRevision {
            if (revision == null || !"HIDRA_TOPOLOGY_STATION_GRAPH_REVISION_V1".equals(payloadFormat)
                    || sha256 == null || !sha256.matches("[0-9a-f]{64}")) {
                throw new InvalidTopologyValueException("Stored station graph revision metadata is invalid.");
            }
        }
    }
}
