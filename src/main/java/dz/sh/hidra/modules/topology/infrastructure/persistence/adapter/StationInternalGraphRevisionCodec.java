/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInternalGraphRevisionCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Encodes bounded canonical station graph revision bytes and rejects noncanonical payloads.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Edge;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Header;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.HeaderKind;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Unit;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.Qualification;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.SourceClassification;
import dz.sh.hidra.modules.topology.domain.service.StationInternalGraphRevisionValidator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HexFormat;

/** V1 collection order is canonical identity order, not physical route order. */
public final class StationInternalGraphRevisionCodec {
    public static final String FORMAT = "HIDRA_TOPOLOGY_STATION_GRAPH_REVISION_V1";
    public static final int MAX_PAYLOAD_BYTES = 1_048_576;
    public static final int MAX_COLLECTION_MEMBERS = 10_000;
    public static final int MAX_STRING_BYTES = 65_536;

    public byte[] encode(StationInternalGraphRevision revision) {
        if (revision == null) throw invalid("Station graph revision required.");
        var graph = revision.graph();
        boundedCount(graph.headers().size());
        boundedCount(graph.units().size());
        boundedCount(graph.edges().size());
        try {
            var bytes = new BoundedBytes();
            var out = new DataOutputStream(bytes);
            string(out, FORMAT);
            string(out, revision.facilityId());
            string(out, revision.revisionId());
            string(out, revision.sourceClassification().name());
            instant(out, revision.recordedAt());
            instant(out, revision.effectiveFrom());
            out.writeByte(revision.effectiveUntil() == null ? 0 : 1);
            if (revision.effectiveUntil() != null) instant(out, revision.effectiveUntil());
            optionalString(out, revision.evidenceReference());
            optionalString(out, revision.expectedPredecessorRevisionId());
            string(out, revision.qualification().name());
            string(out, graph.facilityId());
            string(out, graph.revisionId());
            string(out, graph.status().name());
            out.writeInt(graph.headers().size());
            for (var h : graph.headers().stream().sorted(Comparator.comparing(Header::id)).toList()) {
                string(out, h.id()); string(out, h.kind().name()); string(out, h.portId());
            }
            out.writeInt(graph.units().size());
            for (var u : graph.units().stream().sorted(Comparator.comparing(Unit::equipmentId)).toList()) {
                string(out, u.equipmentId()); string(out, u.facilityId());
                string(out, u.inletPortId()); string(out, u.outletPortId());
            }
            out.writeInt(graph.edges().size());
            for (var e : graph.edges().stream().sorted(Comparator.comparing(Edge::id)).toList()) {
                string(out, e.id()); string(out, e.fromPortId()); string(out, e.toPortId());
            }
            out.flush();
            // Apply existing graph/qualification rules after enforcing wire resource limits.
            new StationInternalGraphRevisionValidator().inspect(revision);
            return bytes.toByteArray();
        } catch (IOException failure) {
            throw invalid("Station graph revision could not be encoded as strict UTF-8.");
        }
    }

    public StationInternalGraphRevision decode(byte[] payload) {
        if (payload == null || payload.length > MAX_PAYLOAD_BYTES)
            throw invalid("Station graph payload required within V1 size limit.");
        byte[] bytes = payload.clone();
        try {
            var in = new DataInputStream(new ByteArrayInputStream(bytes));
            if (!FORMAT.equals(string(in))) throw invalid("Unsupported station graph revision format.");
            String facility = string(in);
            String revision = string(in);
            var source = SourceClassification.valueOf(string(in));
            Instant recorded = instant(in);
            Instant from = instant(in);
            Instant until = marker(in) ? instant(in) : null;
            String evidence = optionalString(in);
            String predecessor = optionalString(in);
            var qualification = Qualification.valueOf(string(in));
            String graphFacility = string(in);
            String graphRevision = string(in);
            var status = StationInternalGraph.Status.valueOf(string(in));
            int n = count(in, 12);
            var headers = new ArrayList<Header>(n);
            for (int i = 0; i < n; i++)
                headers.add(new Header(string(in), HeaderKind.valueOf(string(in)), string(in)));
            n = count(in, 16);
            var units = new ArrayList<Unit>(n);
            for (int i = 0; i < n; i++)
                units.add(new Unit(string(in), string(in), string(in), string(in)));
            n = count(in, 12);
            var edges = new ArrayList<Edge>(n);
            for (int i = 0; i < n; i++)
                edges.add(new Edge(string(in), string(in), string(in)));
            if (in.available() != 0) throw invalid("Trailing station graph revision bytes.");
            var graph = new StationInternalGraph(graphFacility, graphRevision, status, headers, units, edges);
            var result = new StationInternalGraphRevision(facility, revision, graph, source,
                    recorded, from, until, evidence, predecessor, qualification);
            if (!Arrays.equals(bytes, encode(result))) throw invalid("Noncanonical station graph revision bytes.");
            return result;
        } catch (IOException | IllegalArgumentException | DateTimeException failure) {
            throw invalid("Malformed station graph revision payload.");
        }
    }

    /** Checksum of supplied bytes; it does not authenticate evidence or approve publication. */
    public String sha256(byte[] payload) {
        if (payload == null || payload.length > MAX_PAYLOAD_BYTES)
            throw invalid("Station graph payload required within V1 size limit.");
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(payload.clone()));
        } catch (NoSuchAlgorithmException failure) {
            throw new IllegalStateException("Required SHA-256 implementation unavailable.", failure);
        }
    }

    private static void string(DataOutputStream out, String value) throws IOException {
        // UTF-8 needs at least one byte per UTF-16 code unit, including surrogate pairs.
        if (value.length() > MAX_STRING_BYTES) throw invalid("V1 string size limit exceeded.");
        ByteBuffer encoded = StandardCharsets.UTF_8.newEncoder()
                .onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT)
                .encode(CharBuffer.wrap(value));
        if (encoded.remaining() > MAX_STRING_BYTES) throw invalid("V1 string size limit exceeded.");
        out.writeInt(encoded.remaining());
        byte[] bytes = new byte[encoded.remaining()];
        encoded.get(bytes);
        out.write(bytes);
    }

    private static String string(DataInputStream in) throws IOException {
        int length = in.readInt();
        if (length < 0 || length > MAX_STRING_BYTES || length > in.available())
            throw invalid("Invalid V1 string length.");
        return StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT)
                .decode(ByteBuffer.wrap(in.readNBytes(length))).toString();
    }

    private static void optionalString(DataOutputStream out, String value) throws IOException {
        out.writeByte(value == null ? 0 : 1);
        if (value != null) string(out, value);
    }

    private static String optionalString(DataInputStream in) throws IOException {
        return marker(in) ? string(in) : null;
    }

    private static boolean marker(DataInputStream in) throws IOException {
        int flag = in.readUnsignedByte();
        if (flag != 0 && flag != 1) throw invalid("Invalid V1 optional marker.");
        return flag == 1;
    }

    private static void instant(DataOutputStream out, Instant value) throws IOException {
        out.writeLong(value.getEpochSecond()); out.writeInt(value.getNano());
    }

    private static Instant instant(DataInputStream in) throws IOException {
        long second = in.readLong(); int nano = in.readInt();
        if (nano < 0 || nano > 999_999_999) throw invalid("Invalid V1 nanoseconds.");
        return Instant.ofEpochSecond(second, nano);
    }

    private static int count(DataInputStream in, int minimumBytes) throws IOException {
        int count = in.readInt();
        boundedCount(count);
        if (count > in.available() / minimumBytes) throw invalid("V1 count exceeds remaining bytes.");
        return count;
    }

    private static void boundedCount(int count) {
        if (count < 0 || count > MAX_COLLECTION_MEMBERS) throw invalid("Invalid V1 member count.");
    }

    /** Enforces total size while writing, before unbounded stream growth. */
    private static final class BoundedBytes extends ByteArrayOutputStream {
        @Override public synchronized void write(int value) {
            requireCapacity(1); super.write(value);
        }
        @Override public synchronized void write(byte[] bytes, int offset, int length) {
            requireCapacity(length); super.write(bytes, offset, length);
        }
        private void requireCapacity(int additional) {
            if (additional > MAX_PAYLOAD_BYTES - count) throw invalid("V1 payload size limit exceeded.");
        }
    }

    private static InvalidTopologyValueException invalid(String message) {
        return new InvalidTopologyValueException(message);
    }
}
