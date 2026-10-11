/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInternalGraphRevisionTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Checks immutable station graph revision identity, temporal and source constraints.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StationInternalGraphRevisionTest {
    private StationInternalGraph graph() { return new StationInternalGraph("station", "r1", StationInternalGraph.Status.COMPLETE,
                List.of(new StationInternalGraph.Header("s", StationInternalGraph.HeaderKind.SUCTION, "sp"),
                        new StationInternalGraph.Header("d", StationInternalGraph.HeaderKind.DISCHARGE, "dp")),
                List.of(new StationInternalGraph.Unit("u", "station", "in", "out")),
                List.of(new StationInternalGraph.Edge("a", "sp", "in"),
                        new StationInternalGraph.Edge("b", "out", "dp"))); }

    @Test void preservesSyntheticRevisionAndDistinctHistoryIdentity() {
        var g = graph();
        var r = new StationInternalGraphRevision("station", "r1", g,
                StationInternalGraphRevision.SourceClassification.SYNTHETIC,
                Instant.parse("2026-01-02T00:00:00Z"), Instant.parse("2026-01-01T00:00:00Z"),
                null, "synthetic-evidence", null,
                StationInternalGraphRevision.Qualification.STRUCTURALLY_QUALIFIED);
        assertSame(g, r.graph());
        assertThrows(UnsupportedOperationException.class, () -> r.graph().units().clear());
        assertEquals("r1", r.revisionId());
    }

    @Test void rejectsIdentityTimeAndPredecessorContradictions() {
        var g = graph();
        var at = Instant.parse("2026-01-01T00:00:00Z");
        assertThrows(InvalidTopologyValueException.class, () ->
                new StationInternalGraphRevision("wrong", "r1", g,
                    StationInternalGraphRevision.SourceClassification.SYNTHETIC, at, at, null,
                    "e", null, StationInternalGraphRevision.Qualification.DRAFT));
        assertThrows(InvalidTopologyValueException.class, () ->
                new StationInternalGraphRevision("station", "r1", g,
                    StationInternalGraphRevision.SourceClassification.SYNTHETIC, at, at, at,
                    "e", null, StationInternalGraphRevision.Qualification.DRAFT));
        assertThrows(InvalidTopologyValueException.class, () ->
                new StationInternalGraphRevision("station", "r1", g,
                    StationInternalGraphRevision.SourceClassification.SYNTHETIC, at, at, null,
                    "e", "r1", StationInternalGraphRevision.Qualification.DRAFT));
    }

    @Test void requiresEvidenceForClaimedVerification() {
        var at = Instant.parse("2026-01-01T00:00:00Z");
        assertThrows(InvalidTopologyValueException.class, () ->
                new StationInternalGraphRevision("station", "r1", graph(),
                    StationInternalGraphRevision.SourceClassification.VERIFIED, at, at, null,
                    null, null, StationInternalGraphRevision.Qualification.DRAFT));
    }
}
