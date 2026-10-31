package dz.sh.hidra.modules.simulation.api.rest.response;

import dz.sh.hidra.modules.simulation.api.rest.request.ManufacturerCharacteristicRevisionRequests;
import java.time.Instant;
import java.util.List;

/** Only committed protected results may enter these non-JPA HTTP representations. */
public final class ManufacturerCharacteristicRevisionResponses {
    private ManufacturerCharacteristicRevisionResponses() {}
    public record Receipt(String sourceId, String revisionId, String payloadSha256,
            Instant completedAt, boolean workflowQualified) {}
    public record Revision(String sourceId, String revisionId, String payloadSha256,
            ManufacturerCharacteristicRevisionRequests.Revision source, Instant completedAt,
            boolean workflowQualified) {}
    public record Certificate(String sourceId, String revisionId, String payloadSha256,
            ManufacturerCharacteristicRevisionRequests.Certificate certificate,
            Instant completedAt, boolean workflowQualified) {}
    public record Summary(String sourceId, String revisionId, String payloadSha256,
            Instant recordedAt, Instant effectiveFrom, Instant effectiveUntil,
            boolean workflowQualified) {}
    /** Live paginated result: no count, no cross-request consistency promise. */
    public record HistoryPage(List<Summary> history, String nextCursor, Instant completedAt) {
        public HistoryPage { history = List.copyOf(history); }
    }
}
