/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPhysicalNetworkRevisionCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Encodes and strictly verifies deterministic versioned physical revision bytes.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision.EquipmentKind;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision.EquipmentLink;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision.Node;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision.Origin;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision.PipeSegment;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision.ScopeType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;

public final class TopologyPhysicalNetworkRevisionCodec {
    public static final String FORMAT = "HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V1";

    public byte[] encode(TopologyPhysicalNetworkRevision revision) {
        if (revision == null) {
            throw invalid("Physical revision must be supplied.");
        }
        try {
            var bytes = new ByteArrayOutputStream();
            var out = new DataOutputStream(bytes);
            string(out, FORMAT);
            string(out, revision.sourceId());
            string(out, revision.revisionId());
            string(out, revision.scopeType().name());
            string(out, revision.scopeId());
            instant(out, revision.recordedAt());
            instant(out, revision.effectiveFrom());
            out.writeByte(revision.effectiveUntil() == null ? 0 : 1);
            if (revision.effectiveUntil() != null) {
                instant(out, revision.effectiveUntil());
            }
            string(out, revision.origin().name());
            string(out, revision.evidenceReference());
            out.writeInt(revision.nodes().size());
            for (var node : revision.nodes()) {
                string(out, node.id());
                decimal(out, node.elevationMeters());
            }
            out.writeInt(revision.pipeSegments().size());
            for (var pipe : revision.pipeSegments()) {
                string(out, pipe.id());
                string(out, pipe.fromNodeId());
                string(out, pipe.toNodeId());
                decimal(out, pipe.lengthMeters());
                decimal(out, pipe.internalDiameterMeters());
                decimal(out, pipe.absoluteRoughnessMeters());
            }
            out.writeInt(revision.equipmentLinks().size());
            for (var equipment : revision.equipmentLinks()) {
                string(out, equipment.id());
                string(out, equipment.fromNodeId());
                string(out, equipment.toNodeId());
                string(out, equipment.kind().name());
            }
            out.flush();
            return bytes.toByteArray();
        } catch (IOException failure) {
            throw invalid("Physical revision could not be encoded as strict UTF-8.");
        }
    }

    public TopologyPhysicalNetworkRevision decode(byte[] payload) {
        if (payload == null) {
            throw invalid("Physical revision payload must be supplied.");
        }
        byte[] bytes = payload.clone();
        try {
            var in = new DataInputStream(new ByteArrayInputStream(bytes));
            if (!FORMAT.equals(string(in))) {
                throw invalid("Unsupported physical revision format.");
            }
            String source = string(in);
            String revision = string(in);
            ScopeType scopeType = ScopeType.valueOf(string(in));
            String scope = string(in);
            Instant recorded = instant(in);
            Instant from = instant(in);
            int marker = in.readUnsignedByte();
            if (marker != 0 && marker != 1) {
                throw invalid("Invalid optional effective-end marker.");
            }
            Instant until = marker == 0 ? null : instant(in);
            Origin origin = Origin.valueOf(string(in));
            String evidence = string(in);
            int nodeCount = count(in, 12);
            var nodes = new ArrayList<Node>();
            for (int i = 0; i < nodeCount; i++) {
                nodes.add(new Node(string(in), decimal(in)));
            }
            int pipeCount = count(in, 36);
            var pipes = new ArrayList<PipeSegment>();
            for (int i = 0; i < pipeCount; i++) {
                pipes.add(new PipeSegment(string(in), string(in), string(in), decimal(in), decimal(in), decimal(in)));
            }
            int equipmentCount = count(in, 16);
            var equipment = new ArrayList<EquipmentLink>();
            for (int i = 0; i < equipmentCount; i++) {
                equipment.add(new EquipmentLink(string(in), string(in), string(in), EquipmentKind.valueOf(string(in))));
            }
            if (in.available() != 0) {
                throw invalid("Trailing physical revision bytes are not permitted.");
            }
            var decoded = new TopologyPhysicalNetworkRevision(source, revision, scopeType, scope, recorded, from,
                    until, origin, evidence, nodes, pipes, equipment);
            if (!Arrays.equals(bytes, encode(decoded))) {
                throw invalid("Physical revision encoding is not canonical.");
            }
            return decoded;
        } catch (IOException | IllegalArgumentException | ArithmeticException | DateTimeException failure) {
            throw invalid("Malformed physical revision payload.");
        }
    }

    public String sha256(byte[] payload) {
        if (payload == null) {
            throw invalid("Physical revision payload must be supplied.");
        }
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(payload.clone()));
        } catch (NoSuchAlgorithmException failure) {
            throw new IllegalStateException("Required SHA-256 implementation is unavailable.", failure);
        }
    }

    private static void string(DataOutputStream out, String value) throws IOException {
        ByteBuffer encoded = StandardCharsets.UTF_8.newEncoder()
                .onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT)
                .encode(CharBuffer.wrap(value));
        out.writeInt(encoded.remaining());
        byte[] bytes = new byte[encoded.remaining()];
        encoded.get(bytes);
        out.write(bytes);
    }

    private static String string(DataInputStream in) throws IOException {
        int length = in.readInt();
        if (length < 0 || length > in.available()) {
            throw invalid("String length exceeds remaining physical revision bytes.");
        }
        return StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT)
                .decode(ByteBuffer.wrap(in.readNBytes(length))).toString();
    }

    private static void decimal(DataOutputStream out, BigDecimal value) throws IOException {
        string(out, value.toPlainString());
        out.writeInt(value.scale());
    }

    private static BigDecimal decimal(DataInputStream in) throws IOException {
        return new BigDecimal(string(in)).setScale(in.readInt());
    }

    private static void instant(DataOutputStream out, Instant value) throws IOException {
        out.writeLong(value.getEpochSecond());
        out.writeInt(value.getNano());
    }

    private static Instant instant(DataInputStream in) throws IOException {
        long seconds = in.readLong();
        int nanos = in.readInt();
        if (nanos < 0 || nanos > 999_999_999) {
            throw invalid("Instant nanoseconds are invalid.");
        }
        return Instant.ofEpochSecond(seconds, nanos);
    }

    private static int count(DataInputStream in, int minimumBytesPerElement) throws IOException {
        int count = in.readInt();
        if (count < 0 || count > in.available() / minimumBytesPerElement) {
            throw invalid("List count exceeds remaining physical revision bytes.");
        }
        return count;
    }

    private static InvalidTopologyValueException invalid(String message) {
        return new InvalidTopologyValueException(message);
    }
}
