/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringGraphDraftRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Restricts graph snapshot reads and append-only writes to the parent draft's protected transaction and metadata version.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import java.util.Objects;
import java.util.UUID;

/** Graph snapshots share the existing EngineeringNetworkDraft metadata version and aggregate ownership. */
public interface EngineeringGraphDraftRepositoryPort {
    record Snapshot(long version, String sha256, EngineeringGraphDraft graph) {
        public Snapshot {
            if (version < 1 || sha256 == null || !sha256.matches("[0-9a-f]{64}"))
                throw new IllegalArgumentException("Invalid graph snapshot provenance");
            Objects.requireNonNull(graph);
        }
    }

    /** Returns null only if no snapshot exists at or before the authorized requested version. */
    Snapshot asOf(UUID draftId, long requestedVersion);

    /** Appends only; caller must already have inserted same-version metadata in same physical transaction. */
    Snapshot append(UUID draftId, long newVersion, EngineeringGraphDraft graph, long transactionId);
}
