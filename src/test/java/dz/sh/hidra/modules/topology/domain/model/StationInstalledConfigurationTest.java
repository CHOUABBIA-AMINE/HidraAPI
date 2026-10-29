/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInstalledConfigurationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Pure domain acceptance of immutable installed configuration declarations.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration.*;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.*;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.*;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StationInstalledConfigurationTest {
    private static final Instant FROM=Instant.parse("2026-02-01T00:00:00Z");
    private static final Instant UNTIL=Instant.parse("2026-03-01T00:00:00Z");
    private static final String FORMAT="HIDRA_TOPOLOGY_STATION_GRAPH_REVISION_V1";

    private static StationInternalGraphRevision graph(String facility) {
        var units=new ArrayList<Unit>();
        var edges=new ArrayList<Edge>();
        for(int i=1;i<=3;i++) {
            units.add(new Unit(facility+"-p"+i,facility,"i"+i,"o"+i));
            edges.add(new Edge("in"+i,"s","i"+i));
            edges.add(new Edge("out"+i,"o"+i,"d"));
        }
        var g=new StationInternalGraph(facility,"graph-r1",Status.COMPLETE,
                List.of(new Header("suction",HeaderKind.SUCTION,"s"),
                        new Header("discharge",HeaderKind.DISCHARGE,"d")),units,edges);
        return new StationInternalGraphRevision(facility,"graph-r1",g,SourceClassification.SYNTHETIC,
                FROM.minusSeconds(60),FROM.minusSeconds(60),null,"synthetic:graph",null,
                Qualification.STRUCTURALLY_QUALIFIED);
    }
    static StationInstalledConfiguration fixture(String facility) {
        return create(facility, List.of(
                bound(facility+"-p1",Disposition.CONNECTED,"unit-"+facility+"-1"),
                bound(facility+"-p2",Disposition.ISOLATED,"unit-"+facility+"-2"),
                new Position(facility+"-p3",Disposition.UNOCCUPIED,null,null,0,null,null,null)),
                List.of("in1","out1"));
    }
    private static Position bound(String position,Disposition disposition,String unit) {
        return new Position(position,disposition,unit,"installation-"+unit,1,
                FROM.minusSeconds(3600),null,"synthetic:installation-event");
    }
    static StationInstalledConfiguration create(String facility,List<Position> positions,List<String> edges) {
        return new StationInstalledConfiguration("synthetic-company",facility,"config-r1","request-1",
                0,1,1,graph(facility),FORMAT,"0".repeat(64),FROM,UNTIL,FROM,
                "synthetic:declaration","synthetic-binding","synthetic-session","synthetic-user",
                positions,edges);
    }
    @Test void nineDistinctPhysicalUnitsAcrossThreeStationSnapshots() {
        var seen=new HashSet<String>();
        for(int station=1;station<=3;station++) {
            String facility="synthetic-station-"+station;
            var p=List.of(bound(facility+"-p1",Disposition.CONNECTED,facility+"-u1"),
                    bound(facility+"-p2",Disposition.CONNECTED,facility+"-u2"),
                    bound(facility+"-p3",Disposition.CONNECTED,facility+"-u3"));
            var x=create(facility,p,List.of("out3","in1","out1","in2","out2","in3"));
            assertEquals(3,x.positions().size());
            x.positions().forEach(row->assertTrue(seen.add(row.installedUnitId())));
            assertEquals(0,x.activeStructure().diagnostics().size());
        }
        assertEquals(9,seen.size());
    }
    @Test void mixedAndAllIsolatedOrUnoccupiedKeepExplicitStructuralMeaning() {
        var mixed=fixture("synthetic-station-a");
        assertEquals(Disposition.UNOCCUPIED,mixed.positions().get(2).disposition());
        assertTrue(mixed.activeStructure().seriesPairs().isEmpty());
        var p=List.of(bound("synthetic-station-a-p1",Disposition.ISOLATED,"u1"),
                new Position("synthetic-station-a-p2",Disposition.UNOCCUPIED,null,null,0,null,null,null),
                new Position("synthetic-station-a-p3",Disposition.UNOCCUPIED,null,null,0,null,null,null));
        var isolated=create("synthetic-station-a",p,List.of());
        assertTrue(isolated.activeStructure().parallelPairs().isEmpty());
        assertTrue(isolated.activeStructure().seriesPairs().isEmpty());
        assertTrue(isolated.activeStructure().diagnostics().isEmpty());
    }
    @Test void rejectsMissingDuplicateOrForeignPositionsAndReusedPhysicalUnit() {
        var p=fixture("synthetic-station-a").positions();
        assertThrows(InvalidTopologyValueException.class,()->create("synthetic-station-a",p.subList(0,2),List.of()));
        assertThrows(InvalidTopologyValueException.class,()->create("synthetic-station-a",
                List.of(p.get(0),p.get(0),p.get(2)),List.of()));
        assertThrows(InvalidTopologyValueException.class,()->create("synthetic-station-a",
                List.of(bound("synthetic-station-a-p1",Disposition.CONNECTED,"x"),
                        bound("synthetic-station-a-p2",Disposition.ISOLATED,"x"),p.get(2)),List.of()));
        assertThrows(InvalidTopologyValueException.class,()->create("synthetic-station-a",
                List.of(bound("wrong-station-p1",Disposition.CONNECTED,"u1"),p.get(1),p.get(2)),List.of()));
    }
    @Test void rejectsEdgesTouchingUnavailablePortsAndUnknownOrDuplicateEdges() {
        var x=fixture("synthetic-station-a");
        assertThrows(InvalidTopologyValueException.class,()->create(x.facilityId(),x.positions(),List.of("in2")));
        assertThrows(InvalidTopologyValueException.class,()->create(x.facilityId(),x.positions(),List.of("unknown")));
        assertThrows(InvalidTopologyValueException.class,()->create(x.facilityId(),x.positions(),List.of("in1","in1")));
    }
    @Test void rejectsFabricatedUnoccupiedWitnessAndInsufficientInstallationCoverage() {
        assertThrows(InvalidTopologyValueException.class,()->new Position("p",Disposition.UNOCCUPIED,
                "physical","installation",1,FROM,null,"synthetic"));
        assertThrows(InvalidTopologyValueException.class,()->create("synthetic-station-a",
                List.of(new Position("synthetic-station-a-p1",Disposition.CONNECTED,"x","inst",1,
                        FROM.plusSeconds(1),null,"synthetic"),fixture("synthetic-station-a").positions().get(1),
                        fixture("synthetic-station-a").positions().get(2)),List.of()));
    }
    @Test void rejectsTimestampPrecisionSourceAndEpochContradictions() {
        var x=fixture("synthetic-station-a");
        assertThrows(InvalidTopologyValueException.class,()->new StationInstalledConfiguration(
                x.companyPartyId(),x.facilityId(),x.revisionId(),x.requestId(),
                1,1,1,x.pinnedGraph(),x.graphPayloadFormat(),x.graphSha256(),
                x.effectiveFrom(),x.effectiveUntil(),x.recordedAt(),x.sourceReference(),
                x.actorBindingId(),x.actorSessionId(),x.actorUserId(),x.positions(),x.activeEdgeIds()));
        assertThrows(InvalidTopologyValueException.class,()->new StationInstalledConfiguration(
                x.companyPartyId(),x.facilityId(),x.revisionId(),x.requestId(),
                0,1,1,x.pinnedGraph(),x.graphPayloadFormat(),x.graphSha256(),
                x.effectiveFrom().plusNanos(1),x.effectiveUntil(),x.recordedAt(),x.sourceReference(),
                x.actorBindingId(),x.actorSessionId(),x.actorUserId(),x.positions(),x.activeEdgeIds()));
    }
}
