/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SteadyStateGasSolverTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Domain Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.service
 *
 * @Description : Verifies synthetic tree solver mass conservation, pressure laws and refusal cases.
 *
 */
package dz.sh.hidra.modules.simulation.domain.service;

import dz.sh.hidra.modules.simulation.domain.model.SimulationNetworkNodeInput;
import dz.sh.hidra.modules.simulation.domain.model.SimulationPhysicalNetworkInput;
import dz.sh.hidra.modules.simulation.domain.model.SimulationPipeSegmentInput;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SteadyStateGasSolverTest {
    private final SteadyStateGasSolver solver = new SteadyStateGasSolver();

    @Test
    void flatPipeHasCorrectFlowPressureAndConservation() {
        var result = solve(tree(List.of(node("a",0),node("b",0)),List.of(pipe("ab","a","b"))),
                Map.of("a",1d,"b",-1d),"a");
        assertTrue(result.converged());
        assertEquals(1d,result.pipeMassFlowKilogramsPerSecond().get("ab"),1e-12);
        assertTrue(result.nodePressurePascalsAbsolute().get("b") < 5_000_000);
        assertTrue(result.nodePressurePascalsAbsolute().get("b") > 0);
        result.nodeMassResidualKilogramsPerSecond().values().forEach(r -> assertEquals(0d,r,1e-10));
        result.pipePressureSquaredResidualPascalsSquared().values().forEach(r -> assertEquals(0d,r,0.01));
    }

    @Test
    void reversedPipeFlowRaisesDownstreamReferencePressure() {
        var result = solve(tree(List.of(node("a",0),node("b",0)),List.of(pipe("ab","a","b"))),
                Map.of("a",-1d,"b",1d),"a");
        assertEquals(-1d,result.pipeMassFlowKilogramsPerSecond().get("ab"),1e-12);
        assertTrue(result.nodePressurePascalsAbsolute().get("b") > 5_000_000);
    }

    @Test
    void zeroFlowWithElevationHasHydrostaticPressureDifference() {
        var result = solve(tree(List.of(node("a",0),node("b",100)),List.of(pipe("ab","a","b"))),
                Map.of("a",0d,"b",0d),"a");
        assertTrue(result.converged());
        assertEquals(0d,result.pipeMassFlowKilogramsPerSecond().get("ab"));
        assertTrue(result.nodePressurePascalsAbsolute().get("b") < 5_000_000);
    }

    @Test
    void branchMassConservationAndDeterminism() {
        var graph=tree(List.of(node("a",0),node("b",0),node("c",0)),
                List.of(pipe("ab","a","b"),pipe("bc","b","c")));
        var injections=Map.of("a",3d,"b",-1d,"c",-2d);
        var result=solve(graph,injections,"a");
        assertEquals(3d,result.pipeMassFlowKilogramsPerSecond().get("ab"),1e-12);
        assertEquals(2d,result.pipeMassFlowKilogramsPerSecond().get("bc"),1e-12);
        assertEquals(result,solve(graph,injections,"a"));
        result.nodeMassResidualKilogramsPerSecond().values().forEach(r -> assertEquals(0d,r,1e-10));
    }

    @Test
    void rejectsUnbalancedBoundaryUnknownNodeAndLoops() {
        var graph=tree(List.of(node("a",0),node("b",0)),List.of(pipe("ab","a","b")));
        assertThrows(IllegalArgumentException.class,()->solve(graph,Map.of("a",1d,"b",0d),"a"));
        assertThrows(IllegalArgumentException.class,()->solve(graph,Map.of("a",1d,"b",-1d),"unknown"));
        var loop=tree(List.of(node("a",0),node("b",0)),List.of(pipe("ab","a","b"),pipe("ba","b","a")));
        assertThrows(IllegalArgumentException.class,()->solve(loop,Map.of("a",1d,"b",-1d),"a"));
    }

    @Test
    void rejectsNonphysicalGasAndInputs() {
        var graph=tree(List.of(node("a",0),node("b",0)),List.of(pipe("ab","a","b")));
        assertThrows(IllegalArgumentException.class,()->solver.solveSyntheticIdealGasTree(graph,
                Map.of("a",1d,"b",-1d),"a",5e6,0,0.018,1e-5));
        assertThrows(IllegalArgumentException.class,()->solver.solveSyntheticIdealGasTree(graph,
                Map.of("a",Double.NaN,"b",0d),"a",5e6,300,0.018,1e-5));
    }

    private dz.sh.hidra.modules.simulation.domain.model.SteadyStateGasSolution solve(
            SimulationPhysicalNetworkInput net,Map<String,Double> values,String datum) {
        return solver.solveSyntheticIdealGasTree(net,values,datum,5_000_000,300,0.018,1e-5);
    }
    private static SimulationNetworkNodeInput node(String id,int elevation) {
        return new SimulationNetworkNodeInput(id,BigDecimal.valueOf(elevation));
    }
    private static SimulationPipeSegmentInput pipe(String id,String a,String b) {
        return new SimulationPipeSegmentInput(id,a,b,BigDecimal.valueOf(1000),
                BigDecimal.valueOf(0.5),BigDecimal.valueOf(0.000015));
    }
    private static SimulationPhysicalNetworkInput tree(List<SimulationNetworkNodeInput> n,
            List<SimulationPipeSegmentInput> e) {
        return new SimulationPhysicalNetworkInput("fixture","revision-1",n,e);
    }
}
