/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInternalGraphRevisionValidatorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Checks deterministic station revision structural qualification diagnostics.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StationInternalGraphRevisionValidatorTest {
    private final StationInternalGraphRevisionValidator validator = new StationInternalGraphRevisionValidator();
    private StationInternalGraph graph(StationInternalGraph.Status status) {
        return new StationInternalGraph("station", "r1", status,
                List.of(new StationInternalGraph.Header("s", StationInternalGraph.HeaderKind.SUCTION, "sp"),
                        new StationInternalGraph.Header("d", StationInternalGraph.HeaderKind.DISCHARGE, "dp")),
                List.of(new StationInternalGraph.Unit("u", "station", "in", "out")),
                List.of(new StationInternalGraph.Edge("a", "sp", "in"),
                        new StationInternalGraph.Edge("b", "out", "dp")));
    }
    private StationInternalGraphRevision revision(StationInternalGraph.Status status,
            StationInternalGraphRevision.Qualification qualification, String evidence) {
        var at = Instant.parse("2026-01-01T00:00:00Z");
        return new StationInternalGraphRevision("station", "r1", graph(status),
                StationInternalGraphRevision.SourceClassification.SYNTHETIC, at, at, null,
                evidence, null, qualification);
    }

    @Test void structurallyQualifiedCompleteGraphHasNoDiagnostics() {
        assertTrue(validator.inspect(revision(StationInternalGraph.Status.COMPLETE,
                StationInternalGraphRevision.Qualification.STRUCTURALLY_QUALIFIED, "synthetic")).qualified());
    }
    @Test void draftDiagnosticsAreDeterministicAndImmutable() {
        var report = validator.inspect(revision(StationInternalGraph.Status.DRAFT,
                StationInternalGraphRevision.Qualification.DRAFT, null));
        assertEquals(List.of("EVIDENCE_MISSING", "GRAPH_INCOMPLETE", "REVISION_DRAFT"), report.diagnostics());
        assertThrows(UnsupportedOperationException.class, () -> report.diagnostics().clear());
    }
    @Test void nullRevisionRejected() {
        assertThrows(InvalidTopologyValueException.class, () -> validator.inspect(null));
    }
}
