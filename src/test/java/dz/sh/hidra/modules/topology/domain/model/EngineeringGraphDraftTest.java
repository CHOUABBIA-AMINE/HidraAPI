/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringGraphDraftTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Domain Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Tests immutable synthetic graph incidence, invalid identities and draft-only diagnostics.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EngineeringGraphDraftTest {
    static EngineeringGraphDraft connected() {
        return new EngineeringGraphDraft(
            List.of(new Node("a",NodeKind.JUNCTION,null),new Node("b",NodeKind.STATION,"station-1")),
            List.of(new Port("out","a",PortDirection.OUTLET),new Port("in","b",PortDirection.INLET)),
            List.of(new Branch("pipe",BranchKind.PIPE,"out","in")));
    }
    static EngineeringGraphDraft threeNodes() {
        return new EngineeringGraphDraft(
            List.of(new Node("a",NodeKind.JUNCTION,null),
                new Node("b",NodeKind.STATION,"station-1"),
                new Node("c",NodeKind.JUNCTION,null)),
            List.of(new Port("a-out","a",PortDirection.OUTLET),
                new Port("b-in","b",PortDirection.INLET),
                new Port("b-out","b",PortDirection.OUTLET),
                new Port("c-in","c",PortDirection.INLET)),
            List.of(new Branch("ab",BranchKind.PIPE,"a-out","b-in"),
                new Branch("bc",BranchKind.CONNECTOR,"b-out","c-in")));
    }

    @Test void acceptsSerialStationAndJunctionIncidenceWithoutDimensions() {
        var g=threeNodes();
        assertEquals(3,g.nodes().size());
        assertEquals("station-1",g.nodes().get(1).facilityId());
        assertEquals(2,g.branches().size());
        assertEquals(List.of("PHYSICAL_VALUES_NOT_SUPPLIED"),g.diagnostics());
    }
    @Test void acceptsParallelBranchesBetweenTheSamePortsWithDistinctIdentities() {
        var a=connected();
        var g=new EngineeringGraphDraft(a.nodes(),a.ports(),
            List.of(a.branches().get(0),new Branch("second",BranchKind.PIPE,"out","in")));
        assertEquals(2,g.branches().size());
        assertFalse(g.diagnostics().contains("GRAPH_DISCONNECTED"));
    }
    @Test void detectsEmptyAndDisconnectedSketchesWithoutFakingQualification() {
        assertEquals(List.of("GRAPH_NOT_AUTHORED"),
            new EngineeringGraphDraft(List.of(),List.of(),List.of()).diagnostics());
        var g=new EngineeringGraphDraft(
            List.of(new Node("a",NodeKind.JUNCTION,null),new Node("b",NodeKind.JUNCTION,null)),
            List.of(),List.of());
        assertEquals(List.of("GRAPH_DISCONNECTED","PIPE_NOT_AUTHORED","PHYSICAL_VALUES_NOT_SUPPLIED"),
            g.diagnostics());
    }
    @Test void detectsIsolatedSubgraphsWithoutDiscardingAuthoredEdges() {
        var a=connected();
        var g=new EngineeringGraphDraft(
            List.of(a.nodes().get(0),a.nodes().get(1),new Node("c",NodeKind.JUNCTION,null)),
            a.ports(),a.branches());
        assertTrue(g.diagnostics().contains("GRAPH_DISCONNECTED"));
        assertEquals(1,g.branches().size());
    }
    @Test void rejectsDuplicateIdsWithinEachNamespace() {
        var a=connected();
        assertThrows(IllegalArgumentException.class,()->new EngineeringGraphDraft(
            List.of(a.nodes().get(0),a.nodes().get(0)),a.ports(),a.branches()));
        assertThrows(IllegalArgumentException.class,()->new EngineeringGraphDraft(
            a.nodes(),List.of(a.ports().get(0),a.ports().get(0)),a.branches()));
        assertThrows(IllegalArgumentException.class,()->new EngineeringGraphDraft(
            a.nodes(),a.ports(),List.of(a.branches().get(0),a.branches().get(0))));
    }
    @Test void rejectsDanglingNodesAndPorts() {
        var a=connected();
        assertThrows(IllegalArgumentException.class,()->new EngineeringGraphDraft(
            a.nodes(),List.of(new Port("p","unknown",PortDirection.OUTLET)),List.of()));
        assertThrows(IllegalArgumentException.class,()->new EngineeringGraphDraft(
            a.nodes(),a.ports(),List.of(new Branch("bad",BranchKind.PIPE,"unknown","in"))));
    }
    @Test void rejectsSamePortAndIntraNodeBranch() {
        assertThrows(IllegalArgumentException.class,
            ()->new Branch("bad",BranchKind.PIPE,"one","one"));
        var g=new EngineeringGraphDraft(
            List.of(new Node("a",NodeKind.JUNCTION,null)),
            List.of(new Port("out","a",PortDirection.OUTLET),
                new Port("in","a",PortDirection.INLET)),List.of());
        assertThrows(IllegalArgumentException.class,()->new EngineeringGraphDraft(
            g.nodes(),g.ports(),List.of(new Branch("bad",BranchKind.PIPE,"out","in"))));
    }
    @Test void rejectsWrongPortDirectionForEitherEndpoint() {
        var g=connected();
        assertThrows(IllegalArgumentException.class,()->new EngineeringGraphDraft(g.nodes(),
            List.of(new Port("out","a",PortDirection.INLET),g.ports().get(0)),
            g.branches()));
        assertThrows(IllegalArgumentException.class,()->new EngineeringGraphDraft(g.nodes(),
            List.of(new Port("out","a",PortDirection.OUTLET),
                new Port("in","b",PortDirection.OUTLET)),g.branches()));
    }
    @Test void supportsBidirectionalEndpoints() {
        var g=connected();
        var bi=new EngineeringGraphDraft(g.nodes(),
            List.of(new Port("out","a",PortDirection.BIDIRECTIONAL),
                new Port("in","b",PortDirection.BIDIRECTIONAL)),g.branches());
        assertEquals(List.of("PHYSICAL_VALUES_NOT_SUPPLIED"),bi.diagnostics());
    }
    @Test void enforcesStationOnlyFacilityBindingAndRequiredKinds() {
        assertThrows(IllegalArgumentException.class,()->new Node("junction",NodeKind.JUNCTION,"station"));
        assertThrows(IllegalArgumentException.class,()->new Node("station",NodeKind.STATION,null));
        assertThrows(NullPointerException.class,()->new Node("a",null,null));
        assertThrows(NullPointerException.class,()->new Port("a","b",null));
        assertThrows(NullPointerException.class,()->new Branch("a",null,"x","y"));
    }
    @Test void rejectsBlankNonnfcSurrogateControlsAndOversizedUtf8Identities() {
        for(String s:Arrays.asList(null,""," a","a ","\t","a\n","e\u0301","\uD800","\uDC00","ü".repeat(60))){
            assertThrows(IllegalArgumentException.class,()->EngineeringGraphDraft.identity(s));
        }
        assertEquals("é",EngineeringGraphDraft.identity("é"));
    }
    @Test void copiesSortsAndFreezesAllListsWithoutChangingSourceIds() {
        var a=new ArrayList<Node>(connected().nodes());
        var p=new ArrayList<Port>(connected().ports());
        var b=new ArrayList<Branch>(connected().branches());
        var g=new EngineeringGraphDraft(a,p,b);
        a.clear();p.clear();b.clear();
        assertEquals(2,g.nodes().size());
        assertThrows(UnsupportedOperationException.class,()->g.nodes().clear());
        assertThrows(UnsupportedOperationException.class,()->g.ports().clear());
        assertThrows(UnsupportedOperationException.class,()->g.branches().clear());
        var reordered=new EngineeringGraphDraft(List.of(g.nodes().get(1),g.nodes().get(0)),
            List.of(g.ports().get(1),g.ports().get(0)),g.branches());
        assertEquals(g,reordered);
    }
    @Test void rejectsNullElementsAndExcessiveCollectionLengths() {
        assertThrows(IllegalArgumentException.class,()->new EngineeringGraphDraft(null,List.of(),List.of()));
        assertThrows(IllegalArgumentException.class,()->new EngineeringGraphDraft(
            Arrays.asList((Node)null),List.of(),List.of()));
        var many=new ArrayList<Node>();
        for(int i=0;i<=EngineeringGraphDraft.MAX_NODES;i++)
            many.add(new Node("n"+i,NodeKind.JUNCTION,null));
        assertThrows(IllegalArgumentException.class,()->new EngineeringGraphDraft(many,List.of(),List.of()));
    }

    @Test void domainHasNoInfrastructureOrFrameworkDependencies() throws Exception {
        String src=java.nio.file.Files.readString(java.nio.file.Path.of(
            "src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringGraphDraft.java"));
        assertFalse(src.contains("import org.springframework"));
        assertFalse(src.contains("import jakarta.persistence"));
        assertFalse(src.contains("import com.fasterxml.jackson"));
        assertFalse(src.contains("infrastructure.persistence"));
        assertFalse(src.contains("import dz.sh.hidra.platform"));
    }
}
