/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInstalledConfigurationCodecTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Canonical UTF-8 byte and pinned-source round-trip qualification.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.*;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.*;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration.*;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StationInstalledConfigurationCodecTest {
    private final StationInstalledConfigurationCodec codec=new StationInstalledConfigurationCodec();
    private final StationInternalGraphRevisionCodec graphCodec=new StationInternalGraphRevisionCodec();
    private static final Instant FROM=Instant.parse("2026-02-01T00:00:00Z");

    private StationInstalledConfiguration snapshot(boolean reversed) {
        String f="station-é";
        var graph=new StationInternalGraph(f,"graph-r1",Status.COMPLETE,
            List.of(new Header("discharge",HeaderKind.DISCHARGE,"d"),
                    new Header("suction",HeaderKind.SUCTION,"s")),
            List.of(new Unit("p1",f,"i1","o1"),new Unit("p2",f,"i2","o2")),
            List.of(new Edge("in1","s","i1"),new Edge("in2","s","i2"),
                    new Edge("out1","o1","d"),new Edge("out2","o2","d")));
        var rev=new StationInternalGraphRevision(f,"graph-r1",graph,SourceClassification.SYNTHETIC,
            FROM.minusSeconds(100),FROM.minusSeconds(100),null,
            "synthetic:graph-é",null,Qualification.STRUCTURALLY_QUALIFIED);
        var positions=new ArrayList<Position>(List.of(
            new Position("p1",Disposition.CONNECTED,"unit-1","install-1",1,
                    FROM.minusSeconds(10),null,"synthetic:évidence"),
            new Position("p2",Disposition.UNOCCUPIED,null,null,0,null,null,null)));
        var edges=new ArrayList<>(List.of("in1","out1"));
        if(reversed){Collections.reverse(positions);Collections.reverse(edges);}
        return new StationInstalledConfiguration("company",f,"config-1","request-1",0,1,1,
            rev,StationInternalGraphRevisionCodec.FORMAT,graphCodec.sha256(graphCodec.encode(rev)),
            FROM,FROM.plusSeconds(600),FROM,"synthetic:source-é","binding","session","user",
            positions,edges);
    }
    @Test void canonicalRoundTripRetainsPinnedGraphAndEvidence() {
        var input=snapshot(false);var bytes=codec.encode(input);
        assertEquals(input,codec.decode(bytes));
        assertEquals(StationInstalledConfigurationCodec.FORMAT,
            "HIDRA_TOPOLOGY_STATION_INSTALLED_CONFIGURATION_V1");
        assertEquals(64,codec.sha256(bytes).length());
    }
    @Test void inputPermutationsProduceIdenticalCanonicalBytes() {
        assertArrayEquals(codec.encode(snapshot(false)),codec.encode(snapshot(true)));
        assertEquals(snapshot(false),snapshot(true));
    }
    @Test void rejectsTrailingTruncationUnknownFormatAndByteTampering() {
        byte[] bytes=codec.encode(snapshot(false));
        byte[] tail=Arrays.copyOf(bytes,bytes.length+1);
        assertThrows(InvalidTopologyValueException.class,()->codec.decode(tail));
        assertThrows(InvalidTopologyValueException.class,()->codec.decode(Arrays.copyOf(bytes,bytes.length-1)));
        var bad=bytes.clone();bad[8]^=1;
        assertThrows(InvalidTopologyValueException.class,()->codec.decode(bad));
        var graphChange=bytes.clone();graphChange[graphChange.length/2]^=1;
        assertThrows(InvalidTopologyValueException.class,()->codec.decode(graphChange));
    }
    @Test void rejectsInvalidStringsNonCanonicalByteOrderAndUnknownMarkers() {
        var bytes=codec.encode(snapshot(false));
        byte[] invalid=bytes.clone();
        // First UTF-8 string starts at offset four; 0xFF is never valid UTF-8.
        invalid[4]=(byte)0xff;
        assertThrows(InvalidTopologyValueException.class,()->codec.decode(invalid));
        assertThrows(InvalidTopologyValueException.class,()->codec.decode(new byte[0]));
        assertThrows(InvalidTopologyValueException.class,()->codec.decode(new byte[1_048_577]));
    }
    @Test void rejectsMismatchBetweenDeclaredGraphDigestAndActualGraph() {
        var x=snapshot(false);
        var falseDigest=new StationInstalledConfiguration(x.companyPartyId(),x.facilityId(),
            x.revisionId(),x.requestId(),x.expectedEpoch(),x.selectedEpoch(),x.graphHeadEpoch(),
            x.pinnedGraph(),x.graphPayloadFormat(),"0".repeat(64),x.effectiveFrom(),
            x.effectiveUntil(),x.recordedAt(),x.sourceReference(),x.actorBindingId(),
            x.actorSessionId(),x.actorUserId(),x.positions(),x.activeEdgeIds());
        assertThrows(InvalidTopologyValueException.class,()->codec.encode(falseDigest));
    }
    @Test void defensiveInputAndOutputBytesDoNotMutateCanonicalSnapshot() {
        byte[] raw=codec.encode(snapshot(false)),saved=raw.clone();
        var copy=raw.clone();copy[copy.length-1]^=2;
        assertArrayEquals(saved,codec.encode(codec.decode(raw)));
        assertNotEquals(codec.sha256(raw),codec.sha256(copy));
    }

    private StationInstalledConfiguration payloadSnapshot(List<String> witnesses) {
        String f="synthetic-payload-boundary";
        var units=new ArrayList<Unit>();
        var edges=new ArrayList<Edge>();
        var positions=new ArrayList<Position>();
        for(int i=0;i<witnesses.size();i++) {
            units.add(new Unit("p"+i,f,"i"+i,"o"+i));
            edges.add(new Edge("in"+i,"s","i"+i));
            edges.add(new Edge("out"+i,"o"+i,"d"));
            positions.add(new Position("p"+i,Disposition.ISOLATED,"u"+i,"install"+i,1,
                FROM,null,witnesses.get(i)));
        }
        var graph=new StationInternalGraph(f,"graph",Status.COMPLETE,
            List.of(new Header("suction",HeaderKind.SUCTION,"s"),
                    new Header("discharge",HeaderKind.DISCHARGE,"d")),units,edges);
        var revision=new StationInternalGraphRevision(f,"graph",graph,SourceClassification.SYNTHETIC,
            FROM,FROM,null,"synthetic:graph",null,Qualification.STRUCTURALLY_QUALIFIED);
        revision=graphCodec.decode(graphCodec.encode(revision));
        return new StationInstalledConfiguration("company",f,"config","request",0,1,1,revision,
            StationInternalGraphRevisionCodec.FORMAT,graphCodec.sha256(graphCodec.encode(revision)),
            FROM,null,FROM,"synthetic:source","binding","session","user",positions,List.of());
    }
    @Test void canonicalPayloadAcceptsExactByteLimitAndRejectsOneMoreByte() {
        var witnesses=new ArrayList<>(Collections.nCopies(16,"e"));
        for(int i=0;i<15;i++) witnesses.set(i,"e".repeat(StationInstalledConfigurationCodec.MAX_TEXT_BYTES));
        int remaining=StationInstalledConfigurationCodec.MAX_PAYLOAD_BYTES-codec.encode(payloadSnapshot(witnesses)).length;
        assertTrue(remaining>0 && remaining+2<=StationInstalledConfigurationCodec.MAX_TEXT_BYTES);
        witnesses.set(15,"e".repeat(remaining+1));
        var exact=payloadSnapshot(witnesses);
        var bytes=codec.encode(exact);
        assertEquals(StationInstalledConfigurationCodec.MAX_PAYLOAD_BYTES,bytes.length);
        assertEquals(exact,codec.decode(bytes));
        assertEquals(64,codec.sha256(bytes).length());
        witnesses.set(15,witnesses.get(15)+"e");
        var excess=payloadSnapshot(witnesses);
        assertThrows(InvalidTopologyValueException.class,()->codec.encode(excess));
        assertThrows(InvalidTopologyValueException.class,
            ()->codec.decode(Arrays.copyOf(bytes,bytes.length+1)));
    }
    @Test void maximumUtf8WitnessRoundTripsAndMalformedUnicodeCannotEncode() {
        String max="é".repeat(StationInstalledConfigurationCodec.MAX_TEXT_BYTES/2);
        var exact=payloadSnapshot(List.of(max));
        assertEquals(exact,codec.decode(codec.encode(exact)));
        assertThrows(InvalidTopologyValueException.class,()->payloadSnapshot(List.of(max+"a")));
        var malformed=payloadSnapshot(List.of("synthetic:"+(char)0xd800));
        assertThrows(InvalidTopologyValueException.class,()->codec.encode(malformed));
    }
}
