/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPhysicalNetworkRevisionTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Domain Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Verifies synthetic physical source revisions, graph invariants and immutable boundaries.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision.EquipmentKind;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision.EquipmentLink;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision.Node;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision.Origin;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision.PipeSegment;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision.ScopeType;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TopologyPhysicalNetworkRevisionTest {
    private static final Instant START = Instant.parse("2000-01-01T00:00:00.123456789Z");
    private static final Instant END = START.plusSeconds(60);

    @Test
    void acceptsExplicitSinglePipeValuesAndEmptyEquipment() {
        var revision = new Draft().build();
        assertEquals(new BigDecimal("-12.50"), revision.nodes().get(0).elevationMeters());
        assertEquals(BigDecimal.ZERO, revision.nodes().get(1).elevationMeters());
        assertEquals(BigDecimal.ZERO, revision.pipeSegments().get(0).absoluteRoughnessMeters());
        assertTrue(revision.equipmentLinks().isEmpty());
        assertEquals("b", revision.pipeSegments().get(0).fromNodeId());
    }

    @Test
    void acceptsBranchesMeshesCyclesAndParallelLinksInOriginalOrder() {
        var pipes = List.of(pipe("p3", "c", "a"), pipe("p2", "b", "c"),
                pipe("p1", "a", "b"), pipe("p4", "b", "a"), pipe("p5", "b", "d"));
        var equipment = List.of(equipment("e2", "b", "a", EquipmentKind.VALVE),
                equipment("e1", "a", "b", EquipmentKind.COMPRESSOR));
        var revision = graph(nodes("d", "a", "b", "c"), pipes, equipment);
        assertEquals(pipes, revision.pipeSegments());
        assertEquals(equipment, revision.equipmentLinks());
        assertEquals("d", revision.nodes().get(0).id());
        assertEquals("b", revision.equipmentLinks().get(0).fromNodeId());
    }

    @Test
    void acceptsBranchWithoutEquipment() {
        assertEquals(3, graph(nodes("a", "b", "c", "d"),
                List.of(pipe("p1", "b", "a"), pipe("p2", "b", "c"), pipe("p3", "d", "b")),
                List.of()).pipeSegments().size());
    }

    @Test
    void equipmentJoinsPipeSubnetworksAndCanBeOnlyIncidenceOfNode() {
        var revision = graph(nodes("a", "b", "c", "d", "e"),
                List.of(pipe("p1", "a", "b"), pipe("p2", "c", "d")),
                List.of(equipment("compressor", "c", "b", EquipmentKind.COMPRESSOR),
                        equipment("valve", "d", "e", EquipmentKind.VALVE)));
        assertEquals(2, revision.pipeSegments().size());
        assertEquals("e", revision.equipmentLinks().get(1).toNodeId());
    }

    @Test
    void supportsBothScopeTypesAndOriginsWithoutIdentityConflation() {
        for (var scope : ScopeType.values()) {
            for (var origin : Origin.values()) {
                var draft = new Draft();
                draft.scopeType = scope;
                draft.origin = origin;
                var revision = draft.build();
                assertEquals(scope, revision.scopeType());
                assertEquals(origin, revision.origin());
                assertNotEquals(revision.sourceId(), revision.scopeId());
                assertNotEquals(revision.revisionId(), revision.pipeSegments().get(0).id());
            }
        }
    }

    @Test
    void normalizesAllIdentitiesAndPreservesDecimalScaleAndCase() {
        var draft = new Draft();
        draft.sourceId = " source ";
        draft.revisionId = " revision ";
        draft.scopeId = " scope ";
        draft.evidenceReference = " evidence ";
        draft.nodes = List.of(new Node(" A ", new BigDecimal("1.00")), new Node(" b ", BigDecimal.ZERO));
        draft.pipes = List.of(pipe(" p ", " A ", " b "));
        draft.equipment = List.of(equipment(" e ", " b ", " A ", EquipmentKind.COMPRESSOR));
        var revision = draft.build();
        assertEquals("source", revision.sourceId());
        assertEquals("revision", revision.revisionId());
        assertEquals("scope", revision.scopeId());
        assertEquals("evidence", revision.evidenceReference());
        assertEquals("A", revision.nodes().get(0).id());
        assertEquals(new BigDecimal("1.00"), revision.nodes().get(0).elevationMeters());
        assertEquals("p", revision.pipeSegments().get(0).id());
        assertEquals("A", revision.pipeSegments().get(0).fromNodeId());
        assertEquals("b", revision.pipeSegments().get(0).toNodeId());
        assertEquals("e", revision.equipmentLinks().get(0).id());
        assertEquals("b", revision.equipmentLinks().get(0).fromNodeId());
        assertEquals("A", revision.equipmentLinks().get(0).toNodeId());
    }

    @Test
    void rejectsEveryMissingOrBlankIdentity() {
        for (String id : Arrays.asList(null, "", " ", "\t\n", "\u2003")) {
            invalidDraft(d -> d.sourceId = id);
            invalidDraft(d -> d.revisionId = id);
            invalidDraft(d -> d.scopeId = id);
            invalidDraft(d -> d.evidenceReference = id);
            invalid(() -> new Node(id, BigDecimal.ZERO));
            invalid(() -> pipe(id, "a", "b"));
            invalid(() -> pipe("p", id, "b"));
            invalid(() -> pipe("p", "a", id));
            invalid(() -> equipment(id, "a", "b", EquipmentKind.VALVE));
            invalid(() -> equipment("e", id, "b", EquipmentKind.VALVE));
            invalid(() -> equipment("e", "a", id, EquipmentKind.VALVE));
        }
    }

    @Test
    void rejectsMissingEnumsRequiredTimesAndPhysicalScalars() {
        invalidDraft(d -> d.scopeType = null);
        invalidDraft(d -> d.origin = null);
        invalidDraft(d -> d.recordedAt = null);
        invalidDraft(d -> d.effectiveFrom = null);
        invalid(() -> new Node("a", null));
        invalid(() -> new PipeSegment("p", "a", "b", null, BigDecimal.ONE, BigDecimal.ZERO));
        invalid(() -> new PipeSegment("p", "a", "b", BigDecimal.ONE, null, BigDecimal.ZERO));
        invalid(() -> new PipeSegment("p", "a", "b", BigDecimal.ONE, BigDecimal.ONE, null));
        invalid(() -> equipment("e", "a", "b", null));
        invalid(() -> new Draft().build().effectiveAt(null));
    }

    @Test
    void rejectsNonpositiveDimensionsAndNegativeRoughness() {
        for (var number : List.of(BigDecimal.ZERO, new BigDecimal("-0.01"))) {
            invalid(() -> new PipeSegment("p", "a", "b", number, BigDecimal.ONE, BigDecimal.ZERO));
            invalid(() -> new PipeSegment("p", "a", "b", BigDecimal.ONE, number, BigDecimal.ZERO));
        }
        invalid(() -> new PipeSegment("p", "a", "b", BigDecimal.ONE, BigDecimal.ONE,
                new BigDecimal("-0.0001")));
    }

    @Test
    void usesInclusiveStartExclusiveEndWithNanosecondPrecision() {
        var revision = new Draft().build();
        assertFalse(revision.effectiveAt(START.minusNanos(1)));
        assertTrue(revision.effectiveAt(START));
        assertTrue(revision.effectiveAt(END.minusNanos(1)));
        assertFalse(revision.effectiveAt(END));
        assertFalse(revision.effectiveAt(END.plusNanos(1)));
        invalidDraft(d -> d.effectiveUntil = START);
        invalidDraft(d -> d.effectiveUntil = START.minusNanos(1));
    }

    @Test
    void allowsOpenEndedRetroactiveAndScheduledIntervals() {
        for (var recorded : List.of(START.minusSeconds(100), END.plusSeconds(100))) {
            var draft = new Draft();
            draft.recordedAt = recorded;
            draft.effectiveUntil = null;
            assertTrue(draft.build().effectiveAt(Instant.MAX));
            assertEquals(recorded, draft.build().recordedAt());
        }
    }

    @Test
    void rejectsSelfLinksAfterNormalization() {
        invalid(() -> pipe("p", " a ", "a"));
        invalid(() -> equipment("e", "b", " b ", EquipmentKind.COMPRESSOR));
    }

    @Test
    void rejectsMissingEmptyAndNullElementLists() {
        invalidDraft(d -> d.nodes = null);
        invalidDraft(d -> d.pipes = null);
        invalidDraft(d -> d.equipment = null);
        invalidDraft(d -> d.nodes = List.of());
        invalidDraft(d -> d.nodes = nodes("a"));
        invalidDraft(d -> d.pipes = List.of());
        invalidDraft(d -> d.nodes = Arrays.asList(new Node("a", BigDecimal.ZERO), null));
        invalidDraft(d -> d.pipes = Arrays.asList(pipe("p", "a", "b"), null));
        invalidDraft(d -> d.equipment = Arrays.asList(equipment("e", "a", "b", EquipmentKind.VALVE), null));
        invalidDraft(d -> {
            d.pipes = List.of();
            d.equipment = List.of(equipment("e", "a", "b", EquipmentKind.VALVE));
        });
    }

    @Test
    void rejectsNormalizedDuplicatesAndPipeEquipmentIdentityCollisions() {
        invalidDraft(d -> d.nodes = nodes("a", " a ", "b"));
        invalidDraft(d -> d.pipes = List.of(pipe("p", "a", "b"), pipe(" p ", "b", "a")));
        invalidDraft(d -> d.equipment = List.of(equipment("e", "a", "b", EquipmentKind.VALVE),
                equipment(" e ", "b", "a", EquipmentKind.COMPRESSOR)));
        invalidDraft(d -> d.equipment = List.of(equipment(" p ", "a", "b", EquipmentKind.VALVE)));
    }

    @Test
    void allowsDistinctNodeAndLinkNamespaces() {
        var draft = new Draft();
        draft.pipes = List.of(pipe("a", "a", "b"));
        draft.equipment = List.of(equipment("b", "a", "b", EquipmentKind.VALVE));
        assertEquals("a", draft.build().pipeSegments().get(0).id());
    }

    @Test
    void rejectsDanglingPipeAndEquipmentEndpoints() {
        invalidDraft(d -> d.pipes = List.of(pipe("p", "missing", "b")));
        invalidDraft(d -> d.pipes = List.of(pipe("p", "a", "missing")));
        invalidDraft(d -> d.equipment = List.of(equipment("e", "missing", "b", EquipmentKind.VALVE)));
        invalidDraft(d -> d.equipment = List.of(equipment("e", "a", "missing", EquipmentKind.COMPRESSOR)));
    }

    @Test
    void rejectsIsolatedNodesAndDisconnectedUnionEvenWithoutIsolatedNodes() {
        invalidDraft(d -> d.nodes = nodes("a", "b", "c"));
        invalid(() -> graph(nodes("a", "b", "c", "d"),
                List.of(pipe("p1", "a", "b"), pipe("p2", "c", "d")), List.of()));
        invalid(() -> graph(nodes("a", "b", "c", "d"), List.of(pipe("p", "a", "b")),
                List.of(equipment("e", "c", "d", EquipmentKind.VALVE))));
    }

    @Test
    void defensiveCopiesProtectAllListsAndStableValueEquality() {
        var draft = new Draft();
        var nodes = new ArrayList<>(draft.nodes);
        var pipes = new ArrayList<>(draft.pipes);
        var equipment = new ArrayList<>(List.of(equipment("e", "a", "b", EquipmentKind.VALVE)));
        draft.nodes = nodes;
        draft.pipes = pipes;
        draft.equipment = equipment;
        var revision = draft.build();
        var equivalent = draft.build();
        int hash = revision.hashCode();
        nodes.clear();
        pipes.clear();
        equipment.clear();
        assertEquals(equivalent, revision);
        assertEquals(hash, revision.hashCode());
        assertThrows(UnsupportedOperationException.class, () -> revision.nodes().clear());
        assertThrows(UnsupportedOperationException.class, () -> revision.pipeSegments().clear());
        assertThrows(UnsupportedOperationException.class, () -> revision.equipmentLinks().clear());
    }

    @Test
    void replacingRevisionPreservesOriginalGeometryLinksTimesAndValueIdentity() {
        var draft = new Draft();
        var original = draft.build();
        int hash = original.hashCode();
        draft.revisionId = "revision-2";
        draft.nodes = List.of(new Node("a", BigDecimal.ONE), new Node("b", BigDecimal.ZERO),
                new Node("c", BigDecimal.ZERO));
        draft.pipes = List.of(new PipeSegment("p", "a", "b", new BigDecimal("200"),
                new BigDecimal("0.8"), new BigDecimal("0.001")));
        draft.equipment = List.of(equipment("e", "b", "c", EquipmentKind.COMPRESSOR));
        draft.effectiveFrom = END;
        draft.effectiveUntil = null;
        var replacement = draft.build();
        assertNotEquals(original, replacement);
        assertEquals(new Draft().build(), original);
        assertEquals(hash, original.hashCode());
        assertEquals(START, original.effectiveFrom());
        assertEquals(END, original.effectiveUntil());
        assertEquals(new BigDecimal("100.00"), original.pipeSegments().get(0).lengthMeters());
        assertTrue(original.equipmentLinks().isEmpty());
    }

    private static final class Draft {
        String sourceId = "synthetic-source";
        String revisionId = "revision-1";
        ScopeType scopeType = ScopeType.PIPELINE_SYSTEM;
        String scopeId = "synthetic-scope";
        Instant recordedAt = END.plusSeconds(1);
        Instant effectiveFrom = START;
        Instant effectiveUntil = END;
        Origin origin = Origin.SYNTHETIC;
        String evidenceReference = "synthetic-test-fixture";
        List<Node> nodes = List.of(new Node("a", new BigDecimal("-12.50")), new Node("b", BigDecimal.ZERO));
        List<PipeSegment> pipes = List.of(pipe("p", "b", "a"));
        List<EquipmentLink> equipment = List.of();

        TopologyPhysicalNetworkRevision build() {
            return new TopologyPhysicalNetworkRevision(sourceId, revisionId, scopeType, scopeId,
                    recordedAt, effectiveFrom, effectiveUntil, origin, evidenceReference, nodes, pipes, equipment);
        }
    }

    private static TopologyPhysicalNetworkRevision graph(List<Node> nodes, List<PipeSegment> pipes,
                                                          List<EquipmentLink> equipment) {
        var draft = new Draft();
        draft.nodes = nodes;
        draft.pipes = pipes;
        draft.equipment = equipment;
        return draft.build();
    }

    private static List<Node> nodes(String... ids) {
        return Arrays.stream(ids).map(id -> new Node(id, BigDecimal.ZERO)).toList();
    }

    private static PipeSegment pipe(String id, String from, String to) {
        return new PipeSegment(id, from, to, new BigDecimal("100.00"), new BigDecimal("0.50"), BigDecimal.ZERO);
    }

    private static EquipmentLink equipment(String id, String from, String to, EquipmentKind kind) {
        return new EquipmentLink(id, from, to, kind);
    }

    private static void invalidDraft(Consumer<Draft> change) {
        var draft = new Draft();
        change.accept(draft);
        invalid(draft::build);
    }

    private static void invalid(Runnable action) {
        assertThrows(InvalidTopologyValueException.class, action::run);
    }
}
