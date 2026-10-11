/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInternalGraphRevisionCodecTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Tests canonical station revision bytes, strict decoding and bounded resource handling.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.*;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.*;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StationInternalGraphRevisionCodecTest {
    private final StationInternalGraphRevisionCodec codec = new StationInternalGraphRevisionCodec();
    private static final Instant AT = Instant.ofEpochSecond(-1, 999_999_999);

    private StationInternalGraphRevision fixture(boolean parallel) {
        var headers = List.of(new Header("a", HeaderKind.SUCTION, "s"),
                new Header("z", HeaderKind.DISCHARGE, "d"));
        var units = List.of(new Unit("u1", "synthetic-station", "i1", "o1"),
                new Unit("u2", "synthetic-station", "i2", "o2"));
        var edges = parallel
                ? List.of(new Edge("a", "s", "i1"), new Edge("b", "s", "i2"),
                          new Edge("c", "o1", "d"), new Edge("d", "o2", "d"))
                : List.of(new Edge("a", "s", "i1"), new Edge("b", "o1", "i2"), new Edge("c", "o2", "d"));
        var graph = new StationInternalGraph("synthetic-station", "r1", Status.COMPLETE, headers, units, edges);
        return new StationInternalGraphRevision(graph.facilityId(), graph.revisionId(), graph,
                SourceClassification.SYNTHETIC, AT, AT, Instant.EPOCH, "synthetic:fixture", "r0",
                Qualification.STRUCTURALLY_QUALIFIED);
    }
    private StationInternalGraphRevision draft(String evidence, String predecessor) {
        var graph = new StationInternalGraph("synthetic-station", "r1", Status.DRAFT, List.of(), List.of(), List.of());
        return new StationInternalGraphRevision(graph.facilityId(), graph.revisionId(), graph,
                SourceClassification.DECLARED, Instant.MIN, Instant.MIN, null, evidence, predecessor, Qualification.DRAFT);
    }
    private StationInternalGraphRevision withGraph(StationInternalGraphRevision r, StationInternalGraph g) {
        return new StationInternalGraphRevision(g.facilityId(), g.revisionId(), g, r.sourceClassification(),
                r.recordedAt(), r.effectiveFrom(), r.effectiveUntil(), r.evidenceReference(),
                r.expectedPredecessorRevisionId(), r.qualification());
    }
    private <T> List<T> reversed(List<T> values) {
        var result = new ArrayList<>(values); Collections.reverse(result); return result;
    }

    @Test void serialAndParallelQualifiedGraphsRoundTrip() {
        for (boolean parallel : List.of(false, true)) {
            var revision = fixture(parallel);
            assertEquals(revision, codec.decode(codec.encode(revision)));
        }
    }
    @Test void permutationsProduceCanonicalBytesWithoutChangingInput() {
        var r = fixture(true); var g = r.graph();
        var permutedGraph = new StationInternalGraph(g.facilityId(), g.revisionId(), g.status(),
                reversed(g.headers()), reversed(g.units()), reversed(g.edges()));
        var p = withGraph(r, permutedGraph);
        var before = p.graph().units();
        assertArrayEquals(codec.encode(r), codec.encode(p));
        assertEquals(codec.sha256(codec.encode(r)), codec.sha256(codec.encode(p)));
        assertEquals(r, codec.decode(codec.encode(p)));
        assertEquals(before, p.graph().units());
        assertNotEquals(r.graph().units(), p.graph().units());
    }
    @Test void draftAndOptionalFieldsRetainSuppliedMeaning() {
        for (String evidence : Arrays.asList(null, "declared:évidence"))
            for (String predecessor : Arrays.asList(null, "r0")) {
                var r = draft(evidence, predecessor);
                assertEquals(r, codec.decode(codec.encode(r)));
                assertEquals(Qualification.DRAFT, codec.decode(codec.encode(r)).qualification());
            }
    }
    @Test void verifiedClassificationIsPreservedAsSuppliedDeclaration() {
        var r = fixture(false);
        var verified = new StationInternalGraphRevision(r.facilityId(), r.revisionId(), r.graph(),
                SourceClassification.VERIFIED, r.recordedAt(), r.effectiveFrom(), r.effectiveUntil(),
                "supplied:reference", null, r.qualification());
        assertEquals(verified, codec.decode(codec.encode(verified)));
    }
    @Test void instantExtremesAndNanoBoundariesRoundTrip() {
        for (var at : List.of(Instant.MIN, Instant.MAX, Instant.EPOCH, AT)) {
            var r = draft(null, null);
            var value = new StationInternalGraphRevision(r.facilityId(), r.revisionId(), r.graph(),
                    r.sourceClassification(), at, at, null, null, null, r.qualification());
            assertEquals(value, codec.decode(codec.encode(value)));
        }
    }
    @Test void formatLayoutAndShaMatchIndependentWireWriter() throws Exception {
        var r = fixture(false); var wire = wire(r);
        assertArrayEquals(wire.bytes(), codec.encode(r));
        assertEquals(HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(wire.bytes())),
                codec.sha256(codec.encode(r)));
        assertArrayEquals(codec.encode(r), codec.encode(r));
    }
    @Test void eachEnvelopeAndGraphFieldAffectsBytes() throws Exception {
        var w = wire(fixture(false)); var original = codec.sha256(w.bytes());
        // Raw field mutations demonstrate complete byte coverage; these need not be valid declarations.
        for (var field : w.offsets().entrySet()) {
            var altered = w.bytes().clone(); altered[field.getValue()] ^= 1;
            assertNotEquals(original, codec.sha256(altered), field.getKey());
        }
        assertFalse(Arrays.equals(codec.encode(fixture(false)), codec.encode(fixture(true))));
        assertFalse(Arrays.equals(codec.encode(draft(null, null)), codec.encode(draft("e", null))));
        assertFalse(Arrays.equals(codec.encode(draft(null, null)), codec.encode(draft(null, "r0"))));
    }
    @Test void validChangedDeclarationsProduceDifferentCanonicalPayloads() {
        var r = fixture(false); var g = r.graph();
        var variants = new ArrayList<StationInternalGraphRevision>();
        variants.add(new StationInternalGraphRevision(r.facilityId(), r.revisionId(), g,
                SourceClassification.DECLARED, r.recordedAt(), r.effectiveFrom(), r.effectiveUntil(),
                r.evidenceReference(), r.expectedPredecessorRevisionId(), r.qualification()));
        variants.add(new StationInternalGraphRevision(r.facilityId(), r.revisionId(), g,
                r.sourceClassification(), Instant.EPOCH, r.effectiveFrom(), r.effectiveUntil(),
                r.evidenceReference(), r.expectedPredecessorRevisionId(), r.qualification()));
        variants.add(new StationInternalGraphRevision(r.facilityId(), r.revisionId(), g,
                r.sourceClassification(), r.recordedAt(), Instant.ofEpochSecond(-2), r.effectiveUntil(),
                r.evidenceReference(), r.expectedPredecessorRevisionId(), r.qualification()));
        variants.add(new StationInternalGraphRevision(r.facilityId(), r.revisionId(), g,
                r.sourceClassification(), r.recordedAt(), r.effectiveFrom(), null,
                r.evidenceReference(), r.expectedPredecessorRevisionId(), r.qualification()));
        variants.add(new StationInternalGraphRevision(r.facilityId(), r.revisionId(), g,
                r.sourceClassification(), r.recordedAt(), r.effectiveFrom(), r.effectiveUntil(),
                "synthetic:other", "r-other", Qualification.DRAFT));
        variants.add(withGraph(r, new StationInternalGraph(g.facilityId(), "r2", g.status(), g.headers(), g.units(), g.edges())));
        var otherUnits = g.units().stream().map(u -> new Unit(u.equipmentId(), "other-station", u.inletPortId(), u.outletPortId())).toList();
        variants.add(withGraph(r, new StationInternalGraph("other-station", g.revisionId(), g.status(), g.headers(), otherUnits, g.edges())));
        var changedEdges = new ArrayList<>(g.edges());
        changedEdges.set(0, new Edge("renamed-edge", "s", "i1"));
        variants.add(withGraph(r, new StationInternalGraph(g.facilityId(), g.revisionId(), g.status(), g.headers(), g.units(), changedEdges)));
        var draftGraph = new StationInternalGraph(g.facilityId(), g.revisionId(), Status.DRAFT, g.headers(), g.units(), g.edges());
        variants.add(new StationInternalGraphRevision(r.facilityId(), r.revisionId(), draftGraph,
                r.sourceClassification(), r.recordedAt(), r.effectiveFrom(), r.effectiveUntil(),
                r.evidenceReference(), r.expectedPredecessorRevisionId(), Qualification.DRAFT));
        for (var changed : variants) {
            var encoded = codec.encode(changed);
            assertFalse(Arrays.equals(codec.encode(r), encoded));
            assertNotEquals(codec.sha256(codec.encode(r)), codec.sha256(encoded));
            assertArrayEquals(encoded, codec.encode(codec.decode(encoded)));
        }
    }
    @Test void bytesAreDefensivelyOwned() {
        var r = fixture(false); var bytes = codec.encode(r); var result = codec.decode(bytes);
        bytes[0] = 7; assertEquals(r, result);
        var again = codec.encode(r); again[0] = 9;
        assertEquals(r, codec.decode(codec.encode(r)));
    }
    @Test void everyTruncatedPrefixAndTrailingBytesAreRejected() {
        var bytes = codec.encode(fixture(false));
        for (int i = 0; i < bytes.length; i++) reject(Arrays.copyOf(bytes, i));
        reject(Arrays.copyOf(bytes, bytes.length + 1));
    }
    @Test void nullAndOversizedPayloadsAreRejected() {
        reject(null); reject(new byte[StationInternalGraphRevisionCodec.MAX_PAYLOAD_BYTES + 1]);
        assertThrows(InvalidTopologyValueException.class, () -> codec.encode(null));
        assertThrows(InvalidTopologyValueException.class, () -> codec.sha256(null));
        assertThrows(InvalidTopologyValueException.class,
                () -> codec.sha256(new byte[StationInternalGraphRevisionCodec.MAX_PAYLOAD_BYTES + 1]));
    }
    @Test void unknownFormatEnumsMalformedUtf8AndNoncanonicalWhitespaceAreRejected() throws Exception {
        var w = wire(fixture(false));
        for (String field : List.of("format", "source", "qualification", "status", "h0.kind"))
            reject(replaceString(w, field, "UNKNOWN".getBytes(StandardCharsets.UTF_8)));
        reject(replaceString(w, "facility", new byte[]{(byte)0xc3, 0x28}));
        reject(replaceString(w, "facility", " synthetic-station ".getBytes(StandardCharsets.UTF_8)));
    }
    @Test void invalidStringLengthsAndCollectionCountsAreRejected() throws Exception {
        var w = wire(fixture(false));
        for (int value : new int[]{-1, 65_537, Integer.MAX_VALUE}) {
            var b = w.bytes().clone(); ByteBuffer.wrap(b).putInt(w.offsets().get("facility"), value); reject(b);
        }
        for (String field : List.of("headers", "units", "edges"))
            for (int value : new int[]{-1, 10_001, Integer.MAX_VALUE, 100}) {
                var b = w.bytes().clone(); ByteBuffer.wrap(b).putInt(w.offsets().get(field), value); reject(b);
            }
    }
    @Test void optionFlagsTimeRangesAndIdentityMismatchAreRejected() throws Exception {
        var w = wire(fixture(false));
        for (String field : List.of("untilFlag", "evidenceFlag", "predecessorFlag")) {
            var b = w.bytes().clone(); b[w.offsets().get(field)] = 2; reject(b);
        }
        for (int value : new int[]{-1, 1_000_000_000}) {
            var b = w.bytes().clone(); ByteBuffer.wrap(b).putInt(w.offsets().get("recorded") + 8, value); reject(b);
        }
        var b = w.bytes().clone(); ByteBuffer.wrap(b).putLong(w.offsets().get("from"), Long.MAX_VALUE); reject(b);
        reject(replaceString(w, "graphFacility", "other".getBytes(StandardCharsets.UTF_8)));
        reject(replaceString(w, "graphRevision", "other".getBytes(StandardCharsets.UTF_8)));
        reject(replaceString(w, "u0.facility", "other".getBytes(StandardCharsets.UTF_8)));
        reject(replaceString(w, "predecessor", "r1".getBytes(StandardCharsets.UTF_8)));
    }
    @Test void duplicateIdsShuffledCollectionsAndUnqualifiedGraphAreRejected() throws Exception {
        var r = fixture(false); var w = wire(r);
        reject(replaceString(w, "h1.id", "a".getBytes(StandardCharsets.UTF_8)));
        reject(replaceString(w, "u1.id", "u1".getBytes(StandardCharsets.UTF_8)));
        reject(replaceString(w, "e1.id", "a".getBytes(StandardCharsets.UTF_8)));
        reject(replaceString(w, "e0.to", "missing".getBytes(StandardCharsets.UTF_8)));
        var g = r.graph();
        reject(wire(withGraph(r, new StationInternalGraph(g.facilityId(), g.revisionId(), g.status(),
                reversed(g.headers()), g.units(), g.edges()))).bytes());
        reject(wire(withGraph(r, new StationInternalGraph(g.facilityId(), g.revisionId(), g.status(),
                g.headers(), reversed(g.units()), g.edges()))).bytes());
        reject(wire(withGraph(r, new StationInternalGraph(g.facilityId(), g.revisionId(), g.status(),
                g.headers(), g.units(), reversed(g.edges())))).bytes());
        var broken = withGraph(r, new StationInternalGraph(g.facilityId(), g.revisionId(), g.status(),
                g.headers(), g.units(), List.of()));
        assertThrows(InvalidTopologyValueException.class, () -> codec.encode(broken));
        reject(wire(broken).bytes());
    }
    @Test void optionalEmptyEncodingAndInvalidProvenanceAreRejected() throws Exception {
        var w = wire(fixture(false));
        reject(replaceString(w, "evidence", new byte[0]));
        reject(replaceString(w, "predecessor", new byte[0]));
        var absent = wire(draft(null, null));
        reject(replaceString(absent, "source", "VERIFIED".getBytes(StandardCharsets.UTF_8)));
        reject(replaceString(absent, "qualification", "STRUCTURALLY_QUALIFIED".getBytes(StandardCharsets.UTF_8)));
    }
    @Test void encodeEnforcesStringByteMemberAndTotalPayloadLimits() {
        var r = draft("x".repeat(65_536), null);
        assertEquals(r, codec.decode(codec.encode(r)));
        for (String evidence : List.of("x".repeat(65_537), "é".repeat(32_769), "bad\ud800"))
            assertThrows(InvalidTopologyValueException.class, () -> codec.encode(draft(evidence, null)));
        var headers = new ArrayList<Header>();
        for (int i = 0; i < 10_001; i++) headers.add(new Header("h" + i, HeaderKind.INTERMEDIATE, "p" + i));
        var base = draft(null, null);
        var graph = new StationInternalGraph(base.facilityId(), base.revisionId(), Status.DRAFT,
                headers, List.of(), List.of());
        assertThrows(InvalidTopologyValueException.class, () -> codec.encode(withGraph(base, graph)));
        var edges = new ArrayList<Edge>();
        for (int i = 0; i < 20; i++) edges.add(new Edge(i + "x".repeat(65_000), "missing", "other"));
        var large = new StationInternalGraph(base.facilityId(), base.revisionId(), Status.DRAFT,
                List.of(), List.of(), edges);
        assertThrows(InvalidTopologyValueException.class, () -> codec.encode(withGraph(base, large)));
    }
    private void reject(byte[] bytes) { assertThrows(InvalidTopologyValueException.class, () -> codec.decode(bytes)); }

    /** Independent field-layout fixture, deliberately writes supplied order without canonicalization. */
    private record Wire(byte[] bytes, Map<String, Integer> offsets) {}
    private Wire wire(StationInternalGraphRevision r) throws IOException {
        var b = new ByteArrayOutputStream(); var out = new DataOutputStream(b); var offsets = new LinkedHashMap<String,Integer>();
        class Writer {
            void string(String key, String value) throws IOException {
                offsets.put(key, b.size()); var bytes = value.getBytes(StandardCharsets.UTF_8);
                out.writeInt(bytes.length); out.write(bytes);
            }
            void time(String key, Instant at) throws IOException {
                offsets.put(key, b.size()); out.writeLong(at.getEpochSecond()); out.writeInt(at.getNano());
            }
            void flag(String key, boolean value) throws IOException { offsets.put(key, b.size()); out.writeByte(value ? 1 : 0); }
            void count(String key, int n) throws IOException { offsets.put(key, b.size()); out.writeInt(n); }
        }
        var w = new Writer();
        w.string("format", "HIDRA_TOPOLOGY_STATION_GRAPH_REVISION_V1");
        w.string("facility", r.facilityId()); w.string("revision", r.revisionId()); w.string("source", r.sourceClassification().name());
        w.time("recorded", r.recordedAt()); w.time("from", r.effectiveFrom());
        w.flag("untilFlag", r.effectiveUntil() != null); if (r.effectiveUntil() != null) w.time("until", r.effectiveUntil());
        w.flag("evidenceFlag", r.evidenceReference() != null); if (r.evidenceReference() != null) w.string("evidence", r.evidenceReference());
        w.flag("predecessorFlag", r.expectedPredecessorRevisionId() != null);
        if (r.expectedPredecessorRevisionId() != null) w.string("predecessor", r.expectedPredecessorRevisionId());
        w.string("qualification", r.qualification().name()); var g = r.graph();
        w.string("graphFacility", g.facilityId()); w.string("graphRevision", g.revisionId()); w.string("status", g.status().name());
        w.count("headers", g.headers().size()); int i = 0;
        for (var h : g.headers()) { String k = "h" + i++; w.string(k + ".id", h.id()); w.string(k + ".kind", h.kind().name()); w.string(k + ".port", h.portId()); }
        w.count("units", g.units().size()); i = 0;
        for (var u : g.units()) { String k = "u" + i++; w.string(k + ".id", u.equipmentId()); w.string(k + ".facility", u.facilityId()); w.string(k + ".in", u.inletPortId()); w.string(k + ".out", u.outletPortId()); }
        w.count("edges", g.edges().size()); i = 0;
        for (var e : g.edges()) { String k = "e" + i++; w.string(k + ".id", e.id()); w.string(k + ".from", e.fromPortId()); w.string(k + ".to", e.toPortId()); }
        out.flush(); return new Wire(b.toByteArray(), offsets);
    }
    private byte[] replaceString(Wire wire, String key, byte[] replacement) throws IOException {
        int start = wire.offsets().get(key); int end = start + 4 + ByteBuffer.wrap(wire.bytes()).getInt(start);
        var b = new ByteArrayOutputStream(); b.write(wire.bytes(), 0, start); var out = new DataOutputStream(b);
        out.writeInt(replacement.length); out.write(replacement); b.write(wire.bytes(), end, wire.bytes().length - end);
        return b.toByteArray();
    }
}
