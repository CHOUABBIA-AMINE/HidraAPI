/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationPhysicalNetworkRevisionContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Application Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.simulation
 *
 * @Description : Verifies standard Java exports, exact metadata and immutable physical DTO boundaries.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.simulation;

import dz.sh.hidra.modules.topology.application.contract.simulation.SimulationPhysicalNetworkRevisionContract.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimulationPhysicalNetworkRevisionContractTest {
    @Test
    void acceptsExactEnumsAndNormalizesIdentitiesWithoutChangingScalars() {
        for(String scope:List.of("PIPELINE","PIPELINE_SYSTEM")) for(String origin:List.of("SYNTHETIC","DECLARED_PARAMETER")) {
            var d=new Draft(); d.scope=scope; d.origin=origin;
            var v=d.build();
            assertEquals("s",v.sourceId()); assertEquals("r",v.revisionId()); assertEquals("q",v.scopeId());
            assertEquals("evidence",v.evidenceReference()); assertEquals(scope,v.scopeType()); assertEquals(origin,v.origin());
            assertEquals(new BigDecimal("1E+2"),v.pipeSegments().get(0).lengthMeters());
            assertEquals(new BigDecimal("-1.00"),v.nodes().get(0).elevationMeters());
            assertNull(v.effectiveUntil());
        }
    }

    @Test
    void regulatorRequiresSeparateV2FormatWhileV1ExistingKindsRemainAccepted() {
        var regulator = new Draft();
        regulator.equipment = List.of(new EquipmentLink("reg", "a", "b", "REGULATOR"));
        regulator.format = "HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V2";
        assertEquals("REGULATOR", regulator.build().equipmentLinks().get(0).kind());
        assertEquals(regulator.format, regulator.build().payloadFormat());
        regulator.format = "HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V1";
        assertThrows(IllegalArgumentException.class, regulator::build);
        var old = new Draft();
        old.format = "HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V2";
        assertThrows(IllegalArgumentException.class, old::build);
        old.format = "HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V1";
        assertEquals("VALVE", old.build().equipmentLinks().get(0).kind());
        assertThrows(IllegalArgumentException.class, () -> new EquipmentLink("reg", "a", "b", "regulator"));
    }

    @Test
    void rejectsAllMissingOrBlankMetadataAndUnknownEnumFormatDigest() {
        for(String bad:Arrays.asList(null,""," ","\t\n")) {
            invalid(d->d.source=bad); invalid(d->d.revision=bad); invalid(d->d.scopeId=bad); invalid(d->d.evidence=bad);
            invalid(d->d.scope=bad); invalid(d->d.origin=bad); invalid(d->d.format=bad); invalid(d->d.digest=bad);
            assertThrows(IllegalArgumentException.class,()->new Node(bad,BigDecimal.ZERO));
            assertThrows(IllegalArgumentException.class,()->pipe(bad,"a","b"));
            assertThrows(IllegalArgumentException.class,()->pipe("p",bad,"b"));
            assertThrows(IllegalArgumentException.class,()->pipe("p","a",bad));
            assertThrows(IllegalArgumentException.class,()->new EquipmentLink(bad,"a","b","VALVE"));
            assertThrows(IllegalArgumentException.class,()->new EquipmentLink("e",bad,"b","VALVE"));
            assertThrows(IllegalArgumentException.class,()->new EquipmentLink("e","a",bad,"VALVE"));
            assertThrows(IllegalArgumentException.class,()->new EquipmentLink("e","a","b",bad));
        }
        for(String bad:List.of("pipeline"," PIPELINE ","FACILITY")) invalid(d->d.scope=bad);
        for(String bad:List.of("synthetic","APPROVED"," SYNTHETIC ")) invalid(d->d.origin=bad);
        for(String bad:List.of("valve","PUMP"," VALVE ")) assertThrows(IllegalArgumentException.class,()->new EquipmentLink("e","a","b",bad));
        invalid(d->d.format="HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V2");
        for(String bad:List.of("A".repeat(64),"a".repeat(63),"a".repeat(65),"g".repeat(64))) invalid(d->d.digest=bad);
        invalid(d->d.recorded=null); invalid(d->d.from=null);
        invalid(d->d.until=Instant.EPOCH); invalid(d->d.until=Instant.EPOCH.minusNanos(1));
    }

    @Test
    void rejectsInvalidPhysicalScalarsAndSelfIncidence() {
        assertThrows(IllegalArgumentException.class,()->new Node("a",null));
        for(BigDecimal bad:Arrays.asList(null,BigDecimal.ZERO,BigDecimal.ONE.negate())) {
            assertThrows(IllegalArgumentException.class,()->new PipeSegment("p","a","b",bad,BigDecimal.ONE,BigDecimal.ZERO));
            assertThrows(IllegalArgumentException.class,()->new PipeSegment("p","a","b",BigDecimal.ONE,bad,BigDecimal.ZERO));
        }
        for(BigDecimal bad:Arrays.asList(null,BigDecimal.ONE.negate())) assertThrows(IllegalArgumentException.class,
                ()->new PipeSegment("p","a","b",BigDecimal.ONE,BigDecimal.ONE,bad));
        assertThrows(IllegalArgumentException.class,()->pipe("p","a"," a "));
        assertThrows(IllegalArgumentException.class,()->new EquipmentLink("e","a"," a ","VALVE"));
    }

    @Test
    void requiresNonNullListsAndEntriesAndDefendsAllOrderedLists() {
        invalid(d->d.nodes=null); invalid(d->d.pipes=null); invalid(d->d.equipment=null);
        invalid(d->d.nodes=List.of()); invalid(d->d.pipes=List.of());
        invalid(d->d.nodes=Arrays.asList(new Node("a",BigDecimal.ZERO),null));
        invalid(d->d.pipes=Arrays.asList(pipe("p","a","b"),null));
        invalid(d->d.equipment=Arrays.asList(new EquipmentLink("e","a","b","VALVE"),null));
        var d=new Draft();
        d.nodes=new ArrayList<>(d.nodes); d.pipes=new ArrayList<>(d.pipes); d.equipment=new ArrayList<>(d.equipment);
        var v=d.build(); d.nodes.clear();d.pipes.clear();d.equipment.clear();
        assertEquals(2,v.nodes().size());assertEquals(1,v.pipeSegments().size());assertEquals(1,v.equipmentLinks().size());
        assertThrows(UnsupportedOperationException.class,()->v.nodes().clear());
        assertThrows(UnsupportedOperationException.class,()->v.pipeSegments().clear());
        assertThrows(UnsupportedOperationException.class,()->v.equipmentLinks().clear());
    }

    private static final class Draft {
        String source=" s ",revision=" r ",scope="PIPELINE",scopeId=" q ",origin="SYNTHETIC",evidence=" evidence ";
        Instant recorded=Instant.EPOCH.minusSeconds(1),from=Instant.EPOCH,until=null;
        String format="HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V1",digest="a".repeat(64);
        List<Node> nodes=List.of(new Node("a",new BigDecimal("-1.00")),new Node("b",BigDecimal.ZERO));
        List<PipeSegment> pipes=List.of(pipe("p","b","a"));
        List<EquipmentLink> equipment=List.of(new EquipmentLink("e","a","b","VALVE"));
        Revision build() { return new Revision(source,revision,scope,scopeId,recorded,from,until,origin,evidence,nodes,pipes,equipment,format,digest); }
    }
    private static PipeSegment pipe(String id,String from,String to) {return new PipeSegment(id,from,to,new BigDecimal("1E+2"),new BigDecimal("0.50"),BigDecimal.ZERO);}
    private static void invalid(Consumer<Draft> change) {var d=new Draft();change.accept(d);assertThrows(IllegalArgumentException.class,d::build);}
}
