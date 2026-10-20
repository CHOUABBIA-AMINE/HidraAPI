/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Defines bounded typed publication commands and immutable post-commit results.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.*;
import static dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.microseconds;
import java.time.Instant;
import java.util.*;

public interface EngineeringRevisionPublicationUseCase {
    enum Kind { PUBLISH, WITHDRAW, REACTIVATE, READ_EXACT, READ_CURRENT, READ_EFFECTIVE, LIST_HISTORY, READ_REQUEST }
    record Command(Kind kind, String companyPartyId, String pipelineSystemId, UUID draftId,
            UUID requestId, Long draftVersion, Long expectedEpoch, UUID expectedRevisionId,
            UUID revisionId, Instant asOf, Integer limit, String cursor) {
        public Command {
            Objects.requireNonNull(kind); Objects.requireNonNull(draftId);
            companyPartyId = EngineeringRevisionPublication.scopeIdentity(companyPartyId);
            pipelineSystemId = EngineeringRevisionPublication.scopeIdentity(pipelineSystemId);
            boolean mutation = kind == Kind.PUBLISH || kind == Kind.WITHDRAW || kind == Kind.REACTIVATE;
            if (mutation) {
                if (requestId == null || expectedEpoch == null || expectedEpoch < 0
                        || (expectedEpoch == 0) != (expectedRevisionId == null)
                        || kind != Kind.PUBLISH && expectedEpoch == 0) throw invalid();
            } else if (expectedEpoch != null || expectedRevisionId != null) throw invalid();
            if (kind == Kind.PUBLISH ? draftVersion == null || draftVersion < 1 : draftVersion != null) throw invalid();
            if (kind == Kind.READ_EXACT ? revisionId == null : revisionId != null) throw invalid();
            if (!mutation && (kind == Kind.READ_REQUEST ? requestId == null : requestId != null)) throw invalid();
            if (kind == Kind.READ_EFFECTIVE && asOf == null
                    || kind != Kind.READ_EFFECTIVE && kind != Kind.LIST_HISTORY && asOf != null) throw invalid();
            if (asOf != null) microseconds(asOf);
            if (kind == Kind.LIST_HISTORY) {
                if (limit == null || limit < 1 || limit > 100 || cursor != null &&
                        (cursor.isEmpty() || cursor.length() > 4096)) throw invalid();
            } else if (limit != null || cursor != null) throw invalid();
        }
        private static IllegalArgumentException invalid() { return new IllegalArgumentException("Invalid publication command"); }
    }
    /** All values are materialized before the final guard; delivery requires observed physical COMMIT. */
    record Result(Head observedHead, EngineeringRevisionPublication publication, List<Event> events,
            Receipt receipt, String nextCursor, boolean replayed) {
        public Result {
            events = List.copyOf(events);
            if (events.size() > 100 || nextCursor != null && nextCursor.length() > 4096
                    || replayed && receipt == null) throw new IllegalArgumentException("Invalid publication result");
            if (observedHead != null && publication != null
                    && !observedHead.scope().equals(publication.snapshot().scope()))
                throw new IllegalArgumentException("Publication result scope");
        }
    }
    Result executePublication(Command command);
}
