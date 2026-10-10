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
}
