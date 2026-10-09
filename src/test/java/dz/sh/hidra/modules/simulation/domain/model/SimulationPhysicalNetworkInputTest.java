/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationPhysicalNetworkInputTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Domain Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Verifies synthetic physical pipe graphs, explicit values and immutable revisions.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimulationPhysicalNetworkInputTest {
    @Test
    void acceptsSinglePipeWithSignedAndZeroElevationAndZeroRoughness() {
        var network = network(List.of(node("a", "-12.5"), node("b", "0")), List.of(pipe("p", "b", "a")));
        assertEquals(new BigDecimal("-12.5"), network.nodes().get(0).elevationMeters());
        assertEquals(BigDecimal.ZERO, network.nodes().get(1).elevationMeters());
        assertEquals(BigDecimal.ZERO, network.pipeSegments().get(0).absoluteRoughnessMeters());
        assertEquals("b", network.pipeSegments().get(0).fromNodeId());
        assertEquals("a", network.pipeSegments().get(0).toNodeId());
    }

    @Test
    void acceptsBranchWithoutDependingOnEdgeOrientation() {
        var pipes = List.of(pipe("p1", "b", "a"), pipe("p2", "c", "b"), pipe("p3", "d", "b"));
        assertEquals(pipes, network(nodes("a", "b", "c", "d"), pipes).pipeSegments());
    }

    @Test
    void acceptsMeshedCyclesAndDistinctParallelPipes() {
        var pipes = List.of(pipe("p1", "a", "b"), pipe("p2", "b", "c"), pipe("p3", "c", "a"),
                pipe("p4", "a", "b"), pipe("p5", "b", "a"));
        assertEquals(5, network(nodes("a", "b", "c"), pipes).pipeSegments().size());
        assertEquals(2, network(nodes("a", "b"), List.of(pipe("p1", "a", "b"), pipe("p2", "a", "b")))
                .pipeSegments().size());
    }

    @Test
    void normalizesRequiredIdentitiesWithoutChangingCaseOrPhysicalValues() {
        var pipe = pipe(" p ", " a ", " b ");
        var network = new SimulationPhysicalNetworkInput(" network ", " revision-1 ",
                List.of(node(" a ", "1.00"), node(" b ", "2")), List.of(pipe));
        assertEquals("network", network.id());
        assertEquals("revision-1", network.topologyRevisionId());
        assertEquals("a", network.nodes().get(0).id());
        assertEquals("p", pipe.id());
        assertEquals("a", pipe.fromNodeId());
        assertEquals("b", pipe.toNodeId());
        assertEquals(new BigDecimal("1.00"), network.nodes().get(0).elevationMeters());
        assertEquals("A", node("A", "0").id());
    }

    @Test
    void rejectsMissingOrBlankIdentitiesAtEveryPosition() {
        for (String id : Arrays.asList(null, "", " ", "\t\n")) {
            invalid(() -> node(id, "0"));
            invalid(() -> pipe(id, "a", "b"));
            invalid(() -> pipe("p", id, "b"));
            invalid(() -> pipe("p", "a", id));
            invalid(() -> new SimulationPhysicalNetworkInput(id, "r", nodes("a", "b"), List.of(pipe("p", "a", "b"))));
            invalid(() -> new SimulationPhysicalNetworkInput("n", id, nodes("a", "b"), List.of(pipe("p", "a", "b"))));
        }
    }

    @Test
    void rejectsMissingPhysicalValuesAndNonpositiveLengthOrDiameter() {
        invalid(() -> new SimulationNetworkNodeInput("a", null));
        for (BigDecimal value : Arrays.asList(null, BigDecimal.ZERO, new BigDecimal("-0.001"))) {
            invalid(() -> dimensions(value, BigDecimal.ONE, BigDecimal.ZERO));
            invalid(() -> dimensions(BigDecimal.ONE, value, BigDecimal.ZERO));
        }
        invalid(() -> dimensions(BigDecimal.ONE, BigDecimal.ONE, null));
        invalid(() -> dimensions(BigDecimal.ONE, BigDecimal.ONE, new BigDecimal("-0.001")));
        assertEquals(new BigDecimal("0.001"), dimensions(new BigDecimal("0.001"), BigDecimal.ONE,
                new BigDecimal("0.001")).lengthMeters());
    }

    @Test
    void rejectsIdenticalNormalizedEndpoints() {
        invalid(() -> pipe("p", "a", "a"));
        invalid(() -> pipe("p", " a ", "a"));
    }

    @Test
    void rejectsMissingEmptyUndersizedListsAndNullEntries() {
        var pipes = List.of(pipe("p", "a", "b"));
        invalid(() -> network(null, pipes));
        invalid(() -> network(List.of(), pipes));
        invalid(() -> network(nodes("a"), pipes));
        invalid(() -> network(nodes("a", "b"), null));
        invalid(() -> network(nodes("a", "b"), List.of()));
        invalid(() -> network(Arrays.asList(node("a", "0"), null), pipes));
        invalid(() -> network(nodes("a", "b"), Arrays.asList(pipes.get(0), null)));
    }

    @Test
    void rejectsDuplicateNormalizedNodeAndPipeIdentities() {
        invalid(() -> network(nodes("a", " a ", "b"), List.of(pipe("p", "a", "b"))));
        invalid(() -> network(nodes("a", "b"), List.of(pipe("p", "a", "b"), pipe(" p ", "b", "a"))));
    }

    @Test
    void rejectsDanglingEndpointsInEitherDirection() {
        invalid(() -> network(nodes("a", "b"), List.of(pipe("p", "missing", "b"))));
        invalid(() -> network(nodes("a", "b"), List.of(pipe("p", "a", "missing"))));
    }

    @Test
    void rejectsIsolatedNodesAndDisconnectedComponentsWithNoIsolatedNodes() {
        invalid(() -> network(nodes("a", "b", "c"), List.of(pipe("p", "a", "b"))));
        invalid(() -> network(nodes("a", "b", "c", "d"),
                List.of(pipe("p1", "a", "b"), pipe("p2", "c", "d"))));
    }

    @Test
    void defensivelyCopiesBothListsAndExposesUnmodifiableLists() {
        var nodes = new ArrayList<>(nodes("a", "b"));
        var pipes = new ArrayList<>(List.of(pipe("p", "a", "b")));
        var first = network(nodes, pipes);
        nodes.set(0, node("a", "20"));
        pipes.set(0, dimensions(new BigDecimal("20"), BigDecimal.ONE, BigDecimal.ZERO));
        nodes.clear();
        pipes.clear();
        assertEquals(BigDecimal.ZERO, first.nodes().get(0).elevationMeters());
        assertEquals(new BigDecimal("100"), first.pipeSegments().get(0).lengthMeters());
        assertEquals(2, first.nodes().size());
        assertEquals(1, first.pipeSegments().size());
        assertThrows(UnsupportedOperationException.class, () -> first.nodes().clear());
        assertThrows(UnsupportedOperationException.class, () -> first.pipeSegments().clear());
    }

    @Test
    void replacementRevisionPreservesPreviouslyCapturedPhysicalValues() {
        var original = network(nodes("a", "b"), List.of(pipe("p", "a", "b")));
        var replacement = new SimulationPhysicalNetworkInput(original.id(), "revision-2",
                List.of(node("a", "10"), node("b", "0")),
                List.of(dimensions(new BigDecimal("200"), new BigDecimal("0.5"), new BigDecimal("0.0001"))));
        assertEquals("revision-1", original.topologyRevisionId());
        assertEquals("revision-2", replacement.topologyRevisionId());
        assertEquals(new BigDecimal("100"), original.pipeSegments().get(0).lengthMeters());
        assertEquals(new BigDecimal("200"), replacement.pipeSegments().get(0).lengthMeters());
        assertEquals(BigDecimal.ONE, original.pipeSegments().get(0).internalDiameterMeters());
        assertEquals(BigDecimal.ZERO, original.nodes().get(0).elevationMeters());
    }

    private static SimulationNetworkNodeInput node(String id, String elevation) {
        return new SimulationNetworkNodeInput(id, new BigDecimal(elevation));
    }

    private static List<SimulationNetworkNodeInput> nodes(String... ids) {
        return Arrays.stream(ids).map(id -> node(id, "0")).toList();
    }

    private static SimulationPipeSegmentInput pipe(String id, String from, String to) {
        return new SimulationPipeSegmentInput(id, from, to, new BigDecimal("100"), BigDecimal.ONE, BigDecimal.ZERO);
    }

    private static SimulationPipeSegmentInput dimensions(BigDecimal length, BigDecimal diameter, BigDecimal roughness) {
        return new SimulationPipeSegmentInput("p", "a", "b", length, diameter, roughness);
    }

    private static SimulationPhysicalNetworkInput network(List<SimulationNetworkNodeInput> nodes,
            List<SimulationPipeSegmentInput> pipes) {
        return new SimulationPhysicalNetworkInput("synthetic-network", "revision-1", nodes, pipes);
    }

    private static void invalid(org.junit.jupiter.api.function.Executable operation) {
        assertThrows(InvalidSimulationValueException.class, operation);
    }
}
