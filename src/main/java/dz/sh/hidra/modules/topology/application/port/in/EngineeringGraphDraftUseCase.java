/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringGraphDraftUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Defines the bounded draft-only graph write and as-of-read use case under existing protected engineering-draft authority.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/** Not an independent aggregate/authorization owner; the existing draft service implements this. */
public interface EngineeringGraphDraftUseCase {
    enum Kind { READ, UPDATE }

    record Command(Kind kind, String companyPartyId, String pipelineSystemId,
                   UUID draftId, Long expectedVersion, Long requestedVersion,
                   EngineeringGraphDraft graph) {
        public Command {
            Objects.requireNonNull(kind, "Graph operation required");
            Objects.requireNonNull(draftId, "Draft identity required");
            EngineeringNetworkDraft.identity(companyPartyId);
            EngineeringNetworkDraft.identity(pipelineSystemId);
            if (kind == Kind.UPDATE) {
                if (graph == null || expectedVersion == null || expectedVersion < 1
                        || requestedVersion != null)
                    throw new IllegalArgumentException("Graph update requires graph and positive expected version");
            } else if (graph != null || expectedVersion != null
                    || (requestedVersion != null && requestedVersion < 1)) {
                throw new IllegalArgumentException("Graph read accepts only an optional positive as-of version");
            }
        }
    }

    record Result(UUID draftId, long version, Long graphVersion, String graphSha256,
                  EngineeringGraphDraft graph, List<String> diagnostics) {
        public Result {
            Objects.requireNonNull(draftId, "Draft identity required");
            if (version < 1 || graphVersion != null && (graphVersion < 1 || graphVersion > version))
                throw new IllegalArgumentException("Invalid graph version");
            if ((graphVersion == null) != (graph == null)
                    || (graphVersion == null) != (graphSha256 == null))
                throw new IllegalArgumentException("Graph snapshot provenance must be complete");
            diagnostics = List.copyOf(Objects.requireNonNull(diagnostics));
        }
    }

    Result executeGraph(Command command);
}
