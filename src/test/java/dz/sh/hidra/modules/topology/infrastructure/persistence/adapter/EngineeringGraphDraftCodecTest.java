/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringGraphDraftCodecTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Tests independent canonical graph bytes, strict UTF-8 decoding, tamper rejection and deterministic hashing.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EngineeringGraphDraftCodecTest {
    private final EngineeringGraphDraftCodec codec = new EngineeringGraphDraftCodec();

    // Independently constructed with Python struct.pack('>i', ...) and hashlib.sha256.
    private static final String CONNECTED_HEX =
        "0000002048494452415f454e47494e454552494e475f47524150485f44524146545f5631"
      + "000000020000000161000000084a554e4354494f4effffffff00000001620000000753544154494f4e0000000973746174696f6e2d31"
      + "0000000200000002696e000000016200000005494e4c4554000000036f75740000000161000000064f55544c4554"
      + "0000000100000004706970650000000450495045000000036f757400000002696e";
    private static final String CONNECTED_SHA256 =
        "6e7fd1dde5a571f5526349c3842fc27afe2cf59ccf7b65ef8726c36f0f338f4c";

    private static EngineeringGraphDraft connected() {
        return new EngineeringGraphDraft(
            List.of(new Node("a",NodeKind.JUNCTION,null),new Node("b",NodeKind.STATION,"station-1")),
            List.of(new Port("out","a",PortDirection.OUTLET),
                new Port("in","b",PortDirection.INLET)),
            List.of(new Branch("pipe",BranchKind.PIPE,"out","in")));
    }

    @Test void independentlyPackedConnectedBytesAndSha256Match() {
        var expected=HexFormat.of().parseHex(CONNECTED_HEX);
        assertEquals(169,expected.length);
        assertArrayEquals(expected,codec.encode(connected()));
        assertEquals(CONNECTED_SHA256,codec.sha256(expected));
        assertEquals(connected(),codec.decode(expected));
    }
    @Test void emptyDraftHasStableIndependentCanonicalBytes() {
        byte[] expected=HexFormat.of().parseHex(
            "0000002048494452415f454e47494e454552494e475f47524150485f44524146545f5631000000000000000000000000");
        assertArrayEquals(expected,codec.encode(new EngineeringGraphDraft(List.of(),List.of(),List.of())));
        assertEquals("71d579c3e03468bbfc8a487649148a871ea56f43d55ede951de28a300fde34db",
            codec.sha256(expected));
        assertEquals(List.of("GRAPH_NOT_AUTHORED"),codec.decode(expected).diagnostics());
    }
    @Test void sortingAndReorderedEquivalentGraphsHaveIdenticalBytes() {
        var v=connected();
        var swapped=new EngineeringGraphDraft(List.of(v.nodes().get(1),v.nodes().get(0)),
            List.of(v.ports().get(1),v.ports().get(0)),v.branches());
        assertArrayEquals(codec.encode(v),codec.encode(swapped));
    }
    @Test void parallelBranchesAreRoundTrippedAndHashedDifferently() {
        var x=connected();
        var other=new EngineeringGraphDraft(x.nodes(),x.ports(),
            List.of(x.branches().get(0),new Branch("parallel",BranchKind.PIPE,"out","in")));
        assertEquals(other,codec.decode(codec.encode(other)));
        assertNotEquals(codec.sha256(codec.encode(x)),codec.sha256(codec.encode(other)));
    }
    @Test void preservesNfcUnicodeIdentifiersWithoutCaseFolding() {
        var x=new EngineeringGraphDraft(
            List.of(new Node("énergie",NodeKind.JUNCTION,null),new Node("B",NodeKind.STATION,"STC-É")),
            List.of(new Port("sortie","énergie",PortDirection.OUTLET),
                new Port("entrée","B",PortDirection.INLET)),
            List.of(new Branch("pipe-α",BranchKind.PIPE,"sortie","entrée")));
        assertEquals(x,codec.decode(codec.encode(x)));
    }
    @Test void rejectsEveryTruncationTrailingBytesAndNull() {
        byte[] original=HexFormat.of().parseHex(CONNECTED_HEX);
        for(int n=0;n<original.length;n++) {
            byte[] truncated=Arrays.copyOf(original,n);
            assertThrows(IllegalArgumentException.class,()->codec.decode(truncated));
        }
        assertThrows(IllegalArgumentException.class,()->codec.decode(Arrays.copyOf(original,original.length+1)));
        assertThrows(IllegalArgumentException.class,()->codec.decode(null));
        assertThrows(NullPointerException.class,()->codec.encode(null));
        assertThrows(IllegalArgumentException.class,()->codec.sha256(null));
    }
    @Test void rejectsCorruptedHeaderAndUnknownFormat() {
        byte[] x=HexFormat.of().parseHex(CONNECTED_HEX);
        x[12]='X';
        assertThrows(IllegalArgumentException.class,()->codec.decode(x));
        x=HexFormat.of().parseHex(CONNECTED_HEX);
        ByteBuffer.wrap(x).putInt(0,Integer.MAX_VALUE);
        byte[] corrupt=x;
        assertThrows(IllegalArgumentException.class,()->codec.decode(corrupt));
    }
    @Test void rejectsInvalidUtf8AndMismatchedEnum() {
        byte[] x=HexFormat.of().parseHex(CONNECTED_HEX);
        // First node ID is a single UTF-8 octet at offset 44.
        x[44]=(byte)0xc0;
        assertThrows(IllegalArgumentException.class,()->codec.decode(x));
        var y=HexFormat.of().parseHex(CONNECTED_HEX);
        // "JUNCTION" ASCII field begins immediately after first node ID and its length.
        y[49]='X';
        assertThrows(IllegalArgumentException.class,()->codec.decode(y));
    }
    @Test void rejectsNoncanonicalElementOrdering() {
        var canonical=HexFormat.of().parseHex(CONNECTED_HEX);
        byte[] altered=canonical.clone();
        // Independent byte-span permutation of two valid nodes (a:21 bytes, b:29).
        // Graph semantics and references remain valid, but noncanonical order must fail.
        System.arraycopy(canonical,61,altered,40,29);
        System.arraycopy(canonical,40,altered,69,21);
        assertThrows(IllegalArgumentException.class,()->codec.decode(altered));
    }
    @Test void rejectsOversizedAndNegativeCountsBeforeAllocation() {
        byte[] x=HexFormat.of().parseHex(CONNECTED_HEX);
        // Graph node count follows 4-byte prefix and 32-byte format name.
        ByteBuffer.wrap(x).putInt(36,Integer.MAX_VALUE);
        assertThrows(IllegalArgumentException.class,()->codec.decode(x));
        ByteBuffer.wrap(x).putInt(36,-1);
        assertThrows(IllegalArgumentException.class,()->codec.decode(x));
    }
    @Test void rejectsOversizedPayloadAndDefensivelyDecodesBytes() {
        assertThrows(IllegalArgumentException.class,
            ()->codec.decode(new byte[EngineeringGraphDraftCodec.MAX_BYTES+1]));
        assertThrows(IllegalArgumentException.class,
            ()->codec.sha256(new byte[EngineeringGraphDraftCodec.MAX_BYTES+1]));
        byte[] encoded=codec.encode(connected());
        var reconstructed=codec.decode(encoded);
        encoded[0]=99;
        assertEquals(connected(),reconstructed);
    }
    @Test void emptyAndDisconnectedDiagnosticsRemainDraftOnlyAfterDecode() {
        var graph=new EngineeringGraphDraft(
            List.of(new Node("a",NodeKind.JUNCTION,null),new Node("b",NodeKind.JUNCTION,null)),
            List.of(),List.of());
        assertTrue(codec.decode(codec.encode(graph)).diagnostics().contains("GRAPH_DISCONNECTED"));
        assertTrue(codec.decode(codec.encode(graph)).diagnostics().contains("PHYSICAL_VALUES_NOT_SUPPLIED"));
    }
}
