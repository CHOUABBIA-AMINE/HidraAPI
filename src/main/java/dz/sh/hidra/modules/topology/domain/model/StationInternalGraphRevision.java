/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInternalGraphRevision
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Declares a versioned station graph with independently supplied source and time evidence.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import java.time.Instant;

/** Domain declaration, not a persisted publication or operator approval. */
public record StationInternalGraphRevision(
        String facilityId, String revisionId, StationInternalGraph graph,
        SourceClassification sourceClassification, Instant recordedAt,
        Instant effectiveFrom, Instant effectiveUntil, String evidenceReference,
        String expectedPredecessorRevisionId, Qualification qualification) {
    public enum SourceClassification { SYNTHETIC, DECLARED, VERIFIED }
    public enum Qualification { DRAFT, STRUCTURALLY_QUALIFIED }

    public StationInternalGraphRevision {
        facilityId = required(facilityId, "Station identity");
        revisionId = required(revisionId, "Revision identity");
        if (graph == null) throw invalid("Station graph required.");
        if (!facilityId.equals(graph.facilityId()) || !revisionId.equals(graph.revisionId()))
            throw invalid("Station graph and envelope identity must match.");
        if (sourceClassification == null) throw invalid("Source classification required.");
        if (recordedAt == null || effectiveFrom == null) throw invalid("Recorded and effective instants required.");
        if (effectiveUntil != null && !effectiveUntil.isAfter(effectiveFrom))
            throw invalid("Effective end must follow effective start.");
        evidenceReference = optional(evidenceReference);
        expectedPredecessorRevisionId = optional(expectedPredecessorRevisionId);
        if (revisionId.equals(expectedPredecessorRevisionId))
            throw invalid("Revision cannot be its own predecessor.");
        if (qualification == null) throw invalid("Qualification required.");
        if (qualification == Qualification.STRUCTURALLY_QUALIFIED
                && (graph.status() != StationInternalGraph.Status.COMPLETE || evidenceReference == null))
            throw invalid("Structural qualification requires a complete graph and evidence reference.");
        if (sourceClassification == SourceClassification.VERIFIED && evidenceReference == null)
            throw invalid("Verified classification requires an evidence reference.");
    }

    private static String required(String s, String label) {
        if (s == null || s.isBlank()) throw invalid(label + " required.");
        return s.trim();
    }
    private static String optional(String s) {
        return s == null || s.isBlank() ? null : s.trim();
    }
    private static InvalidTopologyValueException invalid(String s) {
        return new InvalidTopologyValueException(s);
    }
}
