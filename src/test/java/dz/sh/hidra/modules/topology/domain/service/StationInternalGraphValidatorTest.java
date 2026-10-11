/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInternalGraphValidatorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Exercises synthetic series/parallel graph cases and negative diagnostics.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StationInternalGraphValidatorTest {
    private final StationInternalGraphValidator validator = new StationInternalGraphValidator();
    private StationInternalGraph sample(Status status) {
        var headers = List.of(new Header("s", HeaderKind.SUCTION, "s"),
                new Header("d", HeaderKind.DISCHARGE, "d"), new Header("m", HeaderKind.INTERMEDIATE, "m"));
        var units = List.of(new Unit("u1", "station", "i1", "o1"),
                new Unit("u2", "station", "i2", "o2"), new Unit("u3", "station", "i3", "o3"));
        var edges = List.of(new Edge("a", "s", "i1"), new Edge("b", "o1", "m"),
                new Edge("c", "m", "i2"), new Edge("d", "o2", "d"),
                new Edge("e", "s", "i3"), new Edge("f", "o3", "d"));
        return new StationInternalGraph("station", "synthetic-r1", status, headers, units, edges);
    }
    @Test void distinguishesSeriesBranchFromIndividualParallelUnitsWithoutNumerics() {
        // Synthetic chain u1 -> u2 and a separate u3 branch: no *single* pair
        // shares both declared header junctions without traversing another unit.
        var report = validator.inspect(sample(Status.COMPLETE));
        assertTrue(report.complete());
        assertTrue(report.seriesPairs().contains("u1>u2"));
        assertTrue(report.parallelPairs().isEmpty());

        // Independently declared units u1/u3 between the same suction/discharge
        // junctions are an unambiguous unit-level parallel configuration.
        var headers = List.of(new Header("s", HeaderKind.SUCTION, "s"),
                new Header("d", HeaderKind.DISCHARGE, "d"));
        var units = List.of(new Unit("u1", "station", "i1", "o1"),
                new Unit("u3", "station", "i3", "o3"));
        var edges = List.of(new Edge("a", "s", "i1"), new Edge("b", "o1", "d"),
                new Edge("c", "s", "i3"), new Edge("d", "o3", "d"));
        var parallel = validator.inspect(new StationInternalGraph(
                "station", "synthetic-parallel-r1", Status.COMPLETE, headers, units, edges));
        assertTrue(parallel.complete());
        assertEquals(List.of("u1|u3"), parallel.parallelPairs());
        assertTrue(parallel.seriesPairs().isEmpty());
    }
    @Test void incompleteDraftReturnsDeterministicDiagnosticsButCompleteRejects() {
        var draft = new StationInternalGraph("station", "r", Status.DRAFT,
                List.of(new Header("s", HeaderKind.SUCTION, "s")), List.of(new Unit("u", "station", "i", "o")),
                List.of(new Edge("dangling", "s", "absent")));
        var report = validator.inspect(draft);
        assertTrue(report.diagnostics().contains("DANGLING_PORT:dangling"));
        assertTrue(report.diagnostics().contains("MISSING_DISCHARGE_HEADER"));
        assertThrows(InvalidTopologyValueException.class, () -> validator.inspect(
                new StationInternalGraph("station", "r", Status.COMPLETE, draft.headers(), draft.units(), draft.edges())));
    }
    @Test void rejectsReversedIncidenceAndDeclaredDirectedCycle() {
        var base = sample(Status.DRAFT);
        var edges = new ArrayList<>(base.edges());
        edges.add(new Edge("bad", "i1", "m"));
        assertThrows(InvalidTopologyValueException.class, () -> validator.inspect(
                new StationInternalGraph("station", "r", Status.DRAFT, base.headers(), base.units(), edges)));
        var cyclic = List.of(new Edge("a", "s", "i1"), new Edge("b", "o1", "m"),
                new Edge("c", "m", "i2"), new Edge("d", "o2", "m"));
        assertThrows(InvalidTopologyValueException.class, () -> validator.inspect(
                new StationInternalGraph("station", "r", Status.DRAFT, base.headers(), base.units(), cyclic)));
    }
}
