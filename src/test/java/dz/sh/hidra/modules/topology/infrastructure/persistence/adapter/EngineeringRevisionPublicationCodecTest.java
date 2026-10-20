/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationCodecTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Checks canonical publication bytes, source format dispatch and incomplete survey preservation.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.*;
import dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EngineeringRevisionPublicationCodecTest {
    private static final Scope SCOPE = new Scope(UUID.fromString("00000000-0000-0000-0000-000000000001"), "company", "network");
    private static final UUID DRAFT = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private final EngineeringRevisionPublicationCodec codec = new EngineeringRevisionPublicationCodec();
    private static Component metadata() {
        return Component.of(3, EngineeringNetworkDraftCodec.FORMAT,
                new EngineeringNetworkDraftCodec().encode(new EngineeringNetworkDraft.Metadata(null, "Draft evidence", null)));
    }
    private static Component graph() {
        var graph = new EngineeringGraphDraft(
                List.of(new Node("a", NodeKind.JUNCTION, null), new Node("z", NodeKind.JUNCTION, null)),
                List.of(new Port("from", "a", PortDirection.OUTLET), new Port("to", "z", PortDirection.INLET)),
                List.of(new Branch("pipe", BranchKind.PIPE, "from", "to")));
        return Component.of(1, EngineeringGraphDraftCodec.FORMAT, new EngineeringGraphDraftCodec().encode(graph));
    }
    private static SurveyRouteProfile profile() {
        return new SurveyRouteProfile("pipe", new Crs("DECLARED", "local",
                List.of(new Axis("easting", "m"), new Axis("northing", "m"))), null,
                "survey origin", Direction.FROM_TO, "survey evidence",
                List.of(point("p0", "0.00"), point("p1", "10.000")),
                List.of(new Section("section", new BigDecimal("0.00"), new BigDecimal("10.000"), "section evidence")));
    }
    private static Point point(String id, String chainage) {
        return new Point(id, new BigDecimal("1.2300"), new BigDecimal("-2.00"), new BigDecimal(chainage),
                null, new BigDecimal("0.010"), new BigDecimal("0.0010"), null);
    }
    private static Snapshot complete() {
        var graph = graph();
        var source = Component.of(2, SurveyRouteProfileCodec.FORMAT, new SurveyRouteProfileCodec().encode(profile()));
        return new Snapshot(SCOPE, DRAFT, 3, metadata(), graph,
                List.of(new Survey("pipe", source, 1L, graph.sha256(), List.of("ELEVATION_NOT_SUPPLIED"))),
                List.of("TITLE_NOT_SUPPLIED", "EVIDENCE_NOT_SUPPLIED", "PHYSICAL_VALUES_NOT_SUPPLIED", EngineeringRevisionPublication.UNQUALIFIED));
    }
    private static Snapshot absent() {
        return new Snapshot(SCOPE, DRAFT, 3, metadata(), null, List.of(),
                List.of("TITLE_NOT_SUPPLIED", "EVIDENCE_NOT_SUPPLIED", "GRAPH_NOT_AUTHORED", EngineeringRevisionPublication.UNQUALIFIED));
    }
    private static byte[] replace(byte[] bytes, String from, String to) {
        if (from.length() != to.length()) throw new IllegalArgumentException();
        byte[] result = bytes.clone(), target = from.getBytes(StandardCharsets.UTF_8), replacement = to.getBytes(StandardCharsets.UTF_8);
        outer: for (int start = 0; start <= result.length - target.length; start++) {
            for (int k = 0; k < target.length; k++) if (result[start + k] != target[k]) continue outer;
            System.arraycopy(replacement, 0, result, start, target.length);
            return result;
        }
        throw new AssertionError("Fixture field not found");
    }

    @Test void roundTripsCanonicalSourceBytesAndDecimalScales() {
        Snapshot source = complete();
        byte[] bytes = codec.encode(source);
        Snapshot decoded = codec.decode(bytes, SCOPE);
        assertEquals(source, decoded);
        assertArrayEquals(bytes, codec.encode(decoded));
        assertEquals(source.metadata().base64(), decoded.metadata().base64());
        var profile = new SurveyRouteProfileCodec().decode(Base64.getDecoder().decode(decoded.surveys().get(0).source().base64()));
        assertEquals(new BigDecimal("1.2300"), profile.points().get(0).firstCoordinate());
        assertEquals(new BigDecimal("10.000"), profile.sections().get(0).endChainageMetres());
        assertNull(profile.verticalReference()); assertNull(profile.points().get(0).elevation());
        bytes[0] ^= 1;
        assertEquals(source, decoded);
    }

    @Test void preservesAbsentGraphAndSurveyDiagnostics() {
        Snapshot decoded = codec.decode(codec.encode(absent()), SCOPE);
        assertNull(decoded.graph()); assertFalse(decoded.engineeringQualified());
        assertEquals(absent().diagnostics(), decoded.diagnostics());
        for (String diagnostic : List.of("SURVEY_NOT_AUTHORED", "SURVEY_GRAPH_STALE")) {
            var missing = new Snapshot(SCOPE, DRAFT, 3, metadata(), graph(),
                    List.of(new Survey("pipe", null, null, null, List.of(diagnostic))),
                    List.of("TITLE_NOT_SUPPLIED", "EVIDENCE_NOT_SUPPLIED", "PHYSICAL_VALUES_NOT_SUPPLIED", EngineeringRevisionPublication.UNQUALIFIED));
            var retained = codec.decode(codec.encode(missing), SCOPE);
            assertNull(retained.surveys().get(0).source());
            assertEquals(List.of(diagnostic), retained.surveys().get(0).diagnostics());
        }
        var incomplete = codec.decode(codec.encode(complete()), SCOPE);
        assertEquals(List.of("ELEVATION_NOT_SUPPLIED"), incomplete.surveys().get(0).diagnostics());
    }

    @Test void rejectsUnknownFormatAndNoncanonicalPayload() {
        byte[] bytes = codec.encode(complete());
        assertThrows(IllegalArgumentException.class, () -> codec.decode(
                replace(bytes, EngineeringRevisionPublicationCodec.FORMAT, "HIDRA_ENGINEERING_PUBLICATION_V9"), SCOPE));
        byte[] qualified = bytes.clone(); qualified[qualified.length - 1] = 1;
        assertThrows(IllegalArgumentException.class, () -> codec.decode(qualified, SCOPE));
        byte[] alias = bytes.clone(); alias[alias.length - 1] = 2;
        assertThrows(IllegalArgumentException.class, () -> codec.decode(alias, SCOPE));
        var unknown = new Snapshot(SCOPE, DRAFT, 3,
                Component.of(3, "HIDRA_ENGINEERING_METADATA_V9", Base64.getDecoder().decode(metadata().base64())),
                null, List.of(), absent().diagnostics());
        assertThrows(IllegalArgumentException.class, () -> codec.encode(unknown));
        byte[] invalidMetadata = Base64.getDecoder().decode(metadata().base64()); invalidMetadata[3] = 2;
        var corrupt = new Snapshot(SCOPE, DRAFT, 3, Component.of(3, EngineeringNetworkDraftCodec.FORMAT, invalidMetadata),
                null, List.of(), absent().diagnostics());
        assertThrows(IllegalArgumentException.class, () -> codec.encode(corrupt));
    }

    @Test void rejectsTruncatedTrailingAndOversizedPayload() {
        byte[] bytes = codec.encode(complete());
        for (int n = 0; n < bytes.length; n++) {
            byte[] truncated = Arrays.copyOf(bytes, n);
            assertThrows(IllegalArgumentException.class, () -> codec.decode(truncated, SCOPE));
        }
        assertThrows(IllegalArgumentException.class, () -> codec.decode(Arrays.copyOf(bytes, bytes.length + 1), SCOPE));
        assertThrows(IllegalArgumentException.class, () -> codec.decode(null, SCOPE));
        assertThrows(IllegalArgumentException.class, () -> codec.decode(new byte[EngineeringRevisionPublicationCodec.MAX_BYTES + 1], SCOPE));
        byte[] negativeLength = bytes.clone(); Arrays.fill(negativeLength, 0, 4, (byte) 255);
        assertThrows(IllegalArgumentException.class, () -> codec.decode(negativeLength, SCOPE));
    }

    @Test void rejectsComponentDigestOrContextSubstitution() {
        Snapshot source = complete(); byte[] bytes = codec.encode(source);
        String digest = source.metadata().sha256();
        String changed = (digest.charAt(0) == '0' ? "1" : "0") + digest.substring(1);
        assertThrows(IllegalArgumentException.class, () -> codec.decode(replace(bytes, digest, changed), SCOPE));
        assertThrows(IllegalArgumentException.class, () -> codec.decode(bytes, new Scope(UUID.randomUUID(), "company", "network")));
        assertThrows(IllegalArgumentException.class, () -> codec.decode(bytes, new Scope(SCOPE.contextId(), "other", "network")));
        assertThrows(IllegalArgumentException.class, () -> codec.decode(bytes, new Scope(SCOPE.contextId(), "company", "other")));
        var wrongBranch = new Snapshot(SCOPE, DRAFT, 3, metadata(), graph(),
                List.of(new Survey("other", source.surveys().get(0).source(), 1L, graph().sha256(), List.of())),
                source.diagnostics());
        assertThrows(IllegalArgumentException.class, () -> codec.encode(wrongBranch));
        var omittedPipe = new Snapshot(SCOPE, DRAFT, 3, metadata(), graph(), List.of(), source.diagnostics());
        assertThrows(IllegalArgumentException.class, () -> codec.encode(omittedPipe));
    }

    @Test void preservesOldSourceFormatsWithoutReinterpretation() {
        Snapshot original = complete(), decoded = codec.decode(codec.encode(original), SCOPE);
        assertEquals(EngineeringNetworkDraftCodec.FORMAT, decoded.metadata().format());
        assertEquals(EngineeringGraphDraftCodec.FORMAT, decoded.graph().format());
        assertEquals(SurveyRouteProfileCodec.FORMAT, decoded.surveys().get(0).source().format());
        for (Component component : List.of(decoded.metadata(), decoded.graph(), decoded.surveys().get(0).source())) {
            assertEquals(EngineeringRevisionPublication.hash(Base64.getDecoder().decode(component.base64())), component.sha256());
        }
        assertEquals(original.surveys().get(0).source().base64(), decoded.surveys().get(0).source().base64());
        var metadata = new EngineeringNetworkDraftCodec().decode(Base64.getDecoder().decode(decoded.metadata().base64()));
        assertNull(metadata.title()); assertNull(metadata.evidenceReference());
        assertEquals("Draft evidence", metadata.description());
    }
}
