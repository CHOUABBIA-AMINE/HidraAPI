/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInstalledConfigurationCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Strict canonical immutable station-configuration snapshots with pinned graph bytes.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

/** Not an authorization token, graph publication or protected repository writer. */
public final class StationInstalledConfigurationCodec {
    public static final String FORMAT = "HIDRA_TOPOLOGY_STATION_INSTALLED_CONFIGURATION_V1";
    public static final int MAX_PAYLOAD_BYTES = 1_048_576;
    public static final int MAX_TEXT_BYTES = 65_536;
    private final StationInternalGraphRevisionCodec graphCodec = new StationInternalGraphRevisionCodec();

    public byte[] encode(StationInstalledConfiguration declaration) {
        if (declaration == null) throw invalid("Configuration declaration required.");
        byte[] graph = graphCodec.encode(declaration.pinnedGraph());
        if (!graphCodec.sha256(graph).equals(declaration.graphSha256())
                || !StationInternalGraphRevisionCodec.FORMAT.equals(declaration.graphPayloadFormat()))
            throw invalid("Pinned graph digest or format mismatch.");
        try {
            var bytes = new BoundedBytes();
            var out = new DataOutputStream(bytes);
            string(out, FORMAT);
            string(out, declaration.companyPartyId()); string(out, declaration.facilityId());
            string(out, declaration.revisionId()); string(out, declaration.requestId());
            out.writeLong(declaration.expectedEpoch()); out.writeLong(declaration.selectedEpoch());
            out.writeLong(declaration.graphHeadEpoch());
            string(out, declaration.graphPayloadFormat()); string(out, declaration.graphSha256());
            out.writeInt(graph.length); out.write(graph);
            instant(out, declaration.effectiveFrom()); optionalInstant(out, declaration.effectiveUntil());
            instant(out, declaration.recordedAt()); string(out, declaration.sourceReference());
            string(out, declaration.actorBindingId()); string(out, declaration.actorSessionId());
            string(out, declaration.actorUserId());
            out.writeInt(declaration.positions().size());
            for (var p : declaration.positions()) {
                string(out, p.positionId()); string(out, p.disposition().name());
                if (p.disposition() != Disposition.UNOCCUPIED) {
                    string(out, p.installedUnitId()); string(out, p.installationId());
                    out.writeLong(p.installationRevision()); instant(out, p.installationFrom());
                    optionalInstant(out, p.installationUntil());
                    string(out, p.installationEvidenceReference());
                }
            }
            out.writeInt(declaration.activeEdgeIds().size());
            for (var edge : declaration.activeEdgeIds()) string(out, edge);
            out.flush();
            return bytes.toByteArray();
        } catch (IOException failure) {
            throw invalid("Cannot encode configuration as canonical UTF-8.");
        }
    }

    public StationInstalledConfiguration decode(byte[] payload) {
        if (payload == null || payload.length == 0 || payload.length > MAX_PAYLOAD_BYTES)
            throw invalid("Configuration bytes are absent or exceed the format limit.");
        byte[] bytes = payload.clone();
        try {
            var in = new DataInputStream(new ByteArrayInputStream(bytes));
            if (!FORMAT.equals(string(in))) throw invalid("Unsupported configuration format.");
            String company = string(in), facility = string(in), revision = string(in), request = string(in);
            long expected = in.readLong(), selected = in.readLong(), head = in.readLong();
            String graphFormat = string(in), graphDigest = string(in);
            int graphLength = in.readInt();
            if (graphLength < 1 || graphLength > StationInternalGraphRevisionCodec.MAX_PAYLOAD_BYTES
                    || graphLength > in.available()) throw invalid("Invalid pinned graph byte count.");
            var graph = graphCodec.decode(in.readNBytes(graphLength));
            Instant from = instant(in), until = optionalInstant(in), recorded = instant(in);
            String source = string(in), binding = string(in), session = string(in), user = string(in);
            int n = count(in, 8);
            if (n > StationInstalledConfiguration.MAX_POSITIONS)
                throw invalid("Too many configuration positions.");
            var positions = new ArrayList<Position>(n);
            for (int i=0;i<n;i++) {
                String position = string(in);
                Disposition disposition = Disposition.valueOf(string(in));
                if (disposition == Disposition.UNOCCUPIED)
                    positions.add(new Position(position, disposition, null, null, 0, null, null, null));
                else positions.add(new Position(position, disposition, string(in), string(in), in.readLong(),
                        instant(in), optionalInstant(in), string(in)));
            }
            n = count(in, 4);
            var edges = new ArrayList<String>(n);
            for (int i=0;i<n;i++) edges.add(string(in));
            if (in.available() != 0) throw invalid("Trailing configuration bytes.");
            var result = new StationInstalledConfiguration(company, facility, revision, request,
                    expected, selected, head, graph, graphFormat, graphDigest, from, until,
                    recorded, source, binding, session, user, positions, edges);
            if (!Arrays.equals(bytes, encode(result)))
                throw invalid("Noncanonical configuration encoding.");
            return result;
        } catch (IOException | IllegalArgumentException failure) {
            throw invalid("Malformed configuration snapshot.");
        }
    }

    public String sha256(byte[] canonical) {
        if (canonical == null || canonical.length == 0 || canonical.length > MAX_PAYLOAD_BYTES)
            throw invalid("Canonical bytes required within limit.");
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(canonical.clone()));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 unavailable.", e);
        }
    }

    private static void string(DataOutputStream out, String value) throws IOException {
        ByteBuffer encoded = StandardCharsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT).encode(java.nio.CharBuffer.wrap(value));
        int length = encoded.remaining();
        if (length > MAX_TEXT_BYTES) throw invalid("String byte limit exceeded.");
        out.writeInt(length); byte[] raw = new byte[length]; encoded.get(raw); out.write(raw);
    }
    private static String string(DataInputStream in) throws IOException {
        int length = in.readInt();
        if (length < 1 || length > MAX_TEXT_BYTES || length > in.available())
            throw invalid("Invalid string length.");
        return StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT)
                .decode(ByteBuffer.wrap(in.readNBytes(length))).toString();
    }
    private static void instant(DataOutputStream out, Instant t) throws IOException {
        out.writeLong(t.getEpochSecond()); out.writeInt(t.getNano());
    }
    private static Instant instant(DataInputStream in) throws IOException {
        long seconds = in.readLong(); int nanos = in.readInt();
        if (nanos < 0 || nanos > 999_999_999) throw invalid("Invalid instant nanos.");
        return Instant.ofEpochSecond(seconds, nanos);
    }
    private static void optionalInstant(DataOutputStream out, Instant t) throws IOException {
        out.writeByte(t == null ? 0 : 1);
        if (t != null) instant(out, t);
    }
    private static Instant optionalInstant(DataInputStream in) throws IOException {
        int flag = in.readUnsignedByte();
        if (flag != 0 && flag != 1) throw invalid("Invalid optional instant marker.");
        return flag == 0 ? null : instant(in);
    }
    private static int count(DataInputStream in, int minimum) throws IOException {
        int n = in.readInt();
        if (n < 0 || n > 10_000 || n > in.available() / minimum) throw invalid("Invalid collection size.");
        return n;
    }
    private static final class BoundedBytes extends ByteArrayOutputStream {
        @Override public synchronized void write(int b) {
            require(1); super.write(b);
        }
        @Override public synchronized void write(byte[] b,int start,int length) {
            require(length); super.write(b,start,length);
        }
        private void require(int n) {
            if (n > MAX_PAYLOAD_BYTES - count) throw invalid("Configuration payload exceeds size limit.");
        }
    }
    private static InvalidTopologyValueException invalid(String message) {
        return new InvalidTopologyValueException(message);
    }
}
