/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationSyntheticEquipmentNetworkInputTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Domain Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Verifies explicit synthetic union binding and connectivity.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision.*;
import dz.sh.hidra.modules.simulation.domain.service.SimulationEquipmentBehaviorEvaluator;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimulationSyntheticEquipmentNetworkInputTest {
    public static SimulationEquipmentParameterRevision approved(){
        var b=SimulationEquipmentParameterRevisionTest.fixture("r1");
        var c=b.compressorCurves().getFirst();
        c=new CompressorCurve(c.id(),c.revisionId(),c.recordedAt(),c.effectiveFrom(),c.effectiveUntil(),c.origin(),
                c.evidenceReference(),c.fluidSourceId(),c.fluidRevisionId(),c.fluidSha256(),
                SimulationEquipmentBehaviorEvaluator.HEAD,SimulationEquipmentBehaviorEvaluator.EFFICIENCY,
                SimulationEquipmentBehaviorEvaluator.LINEAR,c.referenceInletPressurePascalsAbsolute(),
                c.referenceInletTemperatureKelvin(),c.speedLines());
        return new SimulationEquipmentParameterRevision(b.sourceId(),b.revisionId(),b.recordedAt(),b.effectiveFrom(),
                b.effectiveUntil(),b.origin(),b.evidenceReference(),b.networkSourceId(),b.networkRevisionId(),
                b.networkSha256(),b.fluidSourceId(),b.fluidRevisionId(),b.fluidSha256(),b.fluidQualificationId(),
                b.equipment(),List.of(c),b.valveCharacteristics(),b.governedLimits(),b.governanceBinding());
    }
    public static SimulationSyntheticEquipmentNetworkInput union(SimulationEquipmentParameterRevision v){
        return new SimulationSyntheticEquipmentNetworkInput("synthetic-union",v.networkSourceId(),v.networkRevisionId(),
                v.networkSha256(),v.fluidSourceId(),v.fluidRevisionId(),v.fluidSha256(),v.fluidQualificationId(),
                SimulationEquipmentParameterRevisionTest.AT,
                List.of(new SimulationNetworkNodeInput("a",BigDecimal.ZERO),
                    new SimulationNetworkNodeInput("b",BigDecimal.ZERO)),List.of(),v,
                Map.of("valve",SimulationEquipmentBehaviorEvaluator.VALVE));
    }
    @Test void acceptsExplicitFullySyntheticConnectedUnion(){
        var union=union(approved());
        assertEquals(2,union.nodes().size());assertTrue(union.pipes().isEmpty());
        assertThrows(UnsupportedOperationException.class,()->union.valveMethods().clear());
    }
    @Test void refusesGenericCompressorNamesAndMismatchedBindings(){
        assertThrows(IllegalArgumentException.class,()->union(SimulationEquipmentParameterRevisionTest.fixture("r1")));
        var b=approved();
        assertThrows(IllegalArgumentException.class,()->new SimulationSyntheticEquipmentNetworkInput("u","wrong",
                b.networkRevisionId(),b.networkSha256(),b.fluidSourceId(),b.fluidRevisionId(),
                b.fluidSha256(),b.fluidQualificationId(),SimulationEquipmentParameterRevisionTest.AT,
                List.of(new SimulationNetworkNodeInput("a",BigDecimal.ZERO),
                        new SimulationNetworkNodeInput("b",BigDecimal.ZERO)),List.of(),b,
                Map.of("valve",SimulationEquipmentBehaviorEvaluator.VALVE)));
    }
    @Test void refusesUnmappedOrDisconnectedSyntheticUnion(){
        var b=approved();
        assertThrows(IllegalArgumentException.class,()->new SimulationSyntheticEquipmentNetworkInput("u",b.networkSourceId(),
                b.networkRevisionId(),b.networkSha256(),b.fluidSourceId(),b.fluidRevisionId(),
                b.fluidSha256(),b.fluidQualificationId(),SimulationEquipmentParameterRevisionTest.AT,
                List.of(new SimulationNetworkNodeInput("a",BigDecimal.ZERO),
                        new SimulationNetworkNodeInput("b",BigDecimal.ZERO),
                        new SimulationNetworkNodeInput("orphan",BigDecimal.ZERO)),List.of(),b,
                Map.of("valve",SimulationEquipmentBehaviorEvaluator.VALVE)));
    }

    @Test void revisionTimeAndSyntheticOriginAreExactAtUnionBoundary() {
        var rev=approved();var valid=union(rev);
        assertEquals(rev.revisionId(),valid.equipmentRevision().revisionId());
        assertEquals(rev.networkSha256(),valid.networkSha256());
        assertEquals(rev.fluidQualificationId(),valid.fluidQualificationId());
        assertEquals(Origin.SYNTHETIC,valid.equipmentRevision().origin());
        assertThrows(IllegalArgumentException.class,()->new SimulationSyntheticEquipmentNetworkInput(
                valid.id(),valid.networkSourceId(),valid.networkRevisionId(),valid.networkSha256(),
                valid.fluidSourceId(),valid.fluidRevisionId(),valid.fluidSha256(),valid.fluidQualificationId(),
                valid.at().minusNanos(1),valid.nodes(),valid.pipes(),rev,valid.valveMethods()));
        assertThrows(IllegalArgumentException.class,()->new SimulationSyntheticEquipmentNetworkInput(
                valid.id(),valid.networkSourceId(),"other-revision",valid.networkSha256(),
                valid.fluidSourceId(),valid.fluidRevisionId(),valid.fluidSha256(),valid.fluidQualificationId(),
                valid.at(),valid.nodes(),valid.pipes(),rev,valid.valveMethods()));
        assertThrows(IllegalArgumentException.class,()->new SimulationSyntheticEquipmentNetworkInput(
                valid.id(),valid.networkSourceId(),valid.networkRevisionId(),valid.networkSha256(),
                valid.fluidSourceId(),valid.fluidRevisionId(),valid.fluidSha256(),"wrong-qualification",
                valid.at(),valid.nodes(),valid.pipes(),rev,valid.valveMethods()));
    }
    @Test void unionOfTwoDisjointPipeSubgraphsConnectedOnlyByEquipmentIsLegal() {
        var rev=approved();
        var connected=new SimulationSyntheticEquipmentNetworkInput("bridge-union",
                rev.networkSourceId(),rev.networkRevisionId(),rev.networkSha256(),
                rev.fluidSourceId(),rev.fluidRevisionId(),rev.fluidSha256(),rev.fluidQualificationId(),
                SimulationEquipmentParameterRevisionTest.AT,
                List.of(new SimulationNetworkNodeInput("a",BigDecimal.ZERO),
                        new SimulationNetworkNodeInput("b",BigDecimal.ZERO),
                        new SimulationNetworkNodeInput("c",BigDecimal.ZERO),
                        new SimulationNetworkNodeInput("d",BigDecimal.ZERO)),
                List.of(new SimulationPipeSegmentInput("ac","a","c",BigDecimal.valueOf(1000),BigDecimal.ONE,BigDecimal.ZERO),
                        new SimulationPipeSegmentInput("bd","b","d",BigDecimal.valueOf(1000),BigDecimal.ONE,BigDecimal.ZERO)),
                rev,Map.of("valve",SimulationEquipmentBehaviorEvaluator.VALVE));
        assertEquals(2,connected.pipes().size());
        assertEquals(4,connected.nodes().size());
        assertThrows(IllegalArgumentException.class,()->new SimulationSyntheticEquipmentNetworkInput(
                connected.id(),connected.networkSourceId(),connected.networkRevisionId(),connected.networkSha256(),
                connected.fluidSourceId(),connected.fluidRevisionId(),connected.fluidSha256(),connected.fluidQualificationId(),
                connected.at(),connected.nodes(),
                List.of(new SimulationPipeSegmentInput("compressor","a","c",BigDecimal.ONE,BigDecimal.ONE,BigDecimal.ZERO)),
                rev,connected.valveMethods()));
        assertThrows(IllegalArgumentException.class,()->new SimulationSyntheticEquipmentNetworkInput(
                connected.id(),connected.networkSourceId(),connected.networkRevisionId(),connected.networkSha256(),
                connected.fluidSourceId(),connected.fluidRevisionId(),connected.fluidSha256(),connected.fluidQualificationId(),
                connected.at(),connected.nodes(),List.of(),rev,connected.valveMethods()));
    }
    @Test void rejectsAnyMissingOrGenericValveSelectionAndUnboundNode() {
        var r=approved(),net=union(r);
        for(var map:List.of(Map.<String,String>of(),
                Map.of("valve","SYNTHETIC_UNGOVERNED"),
                Map.of("valve",SimulationEquipmentBehaviorEvaluator.VALVE,"other",SimulationEquipmentBehaviorEvaluator.VALVE))) {
            assertThrows(IllegalArgumentException.class,()->new SimulationSyntheticEquipmentNetworkInput(
                    net.id(),net.networkSourceId(),net.networkRevisionId(),net.networkSha256(),
                    net.fluidSourceId(),net.fluidRevisionId(),net.fluidSha256(),net.fluidQualificationId(),
                    net.at(),net.nodes(),net.pipes(),r,map));
        }
    }

}
