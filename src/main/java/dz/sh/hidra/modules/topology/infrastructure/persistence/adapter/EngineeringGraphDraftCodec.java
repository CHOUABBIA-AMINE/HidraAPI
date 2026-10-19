/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringGraphDraftCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Encodes immutable engineering graph sketches as bounded canonical UTF-8, never qualified physical revisions.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;
import java.util.Objects;

/** V1 is an engineering-only draft format. Never reinterpret existing physical V1/V2 bytes. */
public final class EngineeringGraphDraftCodec {
    public static final String FORMAT = "HIDRA_ENGINEERING_GRAPH_DRAFT_V1";
    public static final int MAX_BYTES = 1_048_576;

    public byte[] encode(EngineeringGraphDraft graph) {
        Objects.requireNonNull(graph, "Engineering graph required");
        try {
            ByteArrayOutputStream raw = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(raw);
            string(out, FORMAT);
            out.writeInt(graph.nodes().size());
            for (Node n : graph.nodes()) {
                string(out, n.id());
                string(out, n.kind().name());
                optional(out, n.facilityId());
            }
            out.writeInt(graph.ports().size());
            for (Port p : graph.ports()) {
                string(out, p.id());
                string(out, p.nodeId());
                string(out, p.direction().name());
            }
            out.writeInt(graph.branches().size());
            for (Branch b : graph.branches()) {
                string(out, b.id());
                string(out, b.kind().name());
                string(out, b.fromPortId());
                string(out, b.toPortId());
            }
            out.flush();
            byte[] result = raw.toByteArray();
            if (result.length > MAX_BYTES) throw new IllegalArgumentException("Graph payload exceeds limit");
            return result;
        } catch (IOException e) {
            throw new IllegalArgumentException("Graph encoding failed", e);
        }
    }

    public EngineeringGraphDraft decode(byte[] bytes) {
        if (bytes == null || bytes.length < 4 || bytes.length > MAX_BYTES)
            throw new IllegalArgumentException("Invalid graph payload bounds");
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
            if (!FORMAT.equals(string(in))) throw new IllegalArgumentException("Unknown graph format");
            int n = count(in, EngineeringGraphDraft.MAX_NODES, 12);
            List<Node> nodes = new ArrayList<>(n);
            for (int i = 0; i < n; i++)
                nodes.add(new Node(string(in), NodeKind.valueOf(string(in)), optional(in)));
            int p = count(in, EngineeringGraphDraft.MAX_PORTS, 12);
            List<Port> ports = new ArrayList<>(p);
            for (int i = 0; i < p; i++)
                ports.add(new Port(string(in), string(in), PortDirection.valueOf(string(in))));
            int b = count(in, EngineeringGraphDraft.MAX_BRANCHES, 16);
            List<Branch> branches = new ArrayList<>(b);
            for (int i = 0; i < b; i++)
                branches.add(new Branch(string(in), BranchKind.valueOf(string(in)), string(in), string(in)));
            if (in.available() != 0) throw new IllegalArgumentException("Trailing graph payload bytes");
            EngineeringGraphDraft graph = new EngineeringGraphDraft(nodes, ports, branches);
            if (!Arrays.equals(bytes, encode(graph))) throw new IllegalArgumentException("Noncanonical graph payload");
            return graph;
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid graph draft bytes", e);
        }
    }

    public String sha256(byte[] bytes) {
        if (bytes == null || bytes.length > MAX_BYTES)
            throw new IllegalArgumentException("Invalid digest payload");
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 unavailable", e);
        }
    }

    private static int count(DataInputStream in, int max, int minBytes) throws IOException {
        int n = in.readInt();
        if (n < 0 || n > max || (long) n * minBytes > in.available())
            throw new IllegalArgumentException("Invalid graph count");
        return n;
    }

    private static void string(DataOutputStream out, String value) throws IOException {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > 80 || bytes.length == 0) throw new IllegalArgumentException("Text bounds");
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static String string(DataInputStream in) throws IOException {
        int size = in.readInt();
        if (size <= 0 || size > 80 || size > in.available())
            throw new IllegalArgumentException("Invalid string size");
        byte[] raw = in.readNBytes(size);
        try {
            CharBuffer decoded = StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT)
                .decode(ByteBuffer.wrap(raw));
            String result = decoded.toString();
            // For FORMAT, the same identifier rules are safe and byte-canonical.
            EngineeringGraphDraft.identity(result);
            return result;
        } catch (CharacterCodingException bad) {
            throw new IllegalArgumentException("Invalid UTF-8", bad);
        }
    }

    private static void optional(DataOutputStream out, String value) throws IOException {
        if (value == null) out.writeInt(-1);
        else string(out, value);
    }

    private static String optional(DataInputStream in) throws IOException {
        in.mark(4);
        int n = in.readInt();
        if (n == -1) return null;
        in.reset();
        return string(in);
    }
}
