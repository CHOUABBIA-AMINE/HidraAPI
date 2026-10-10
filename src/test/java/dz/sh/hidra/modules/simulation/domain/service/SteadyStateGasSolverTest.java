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
 * @Description : Verifies synthetic network solutions against independent analytical references and failure cases.
 *
 */
package dz.sh.hidra.modules.simulation.domain.service;

import dz.sh.hidra.modules.simulation.domain.model.SimulationNetworkNodeInput;
import dz.sh.hidra.modules.simulation.domain.model.SimulationPhysicalNetworkInput;
import dz.sh.hidra.modules.simulation.domain.model.SimulationPipeSegmentInput;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import dz.sh.hidra.modules.simulation.domain.model.SteadyStateGasSolution;
import dz.sh.hidra.modules.simulation.domain.model.SimulationSyntheticEquipmentNetworkInput;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentGasSolution;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision.*;
import dz.sh.hidra.modules.simulation.domain.model.SimulationSyntheticEquipmentNetworkInputTest;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevisionTest;
import java.util.ArrayList;
import dz.sh.hidra.modules.simulation.domain.service.SteadyStateGasSolver.Boundary;
import dz.sh.hidra.modules.simulation.domain.service.SteadyStateGasSolver.SyntheticGasProperties;
import dz.sh.hidra.modules.simulation.domain.service.SteadyStateGasSolver.NumericalControls;
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
        assertEquals(0d,result.pipeMassFlowKilogramsPerSecond().get("ab"),0d);
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

    private static final SyntheticGasProperties GAS = new SyntheticGasProperties(300, 0.018, 0.00001);
    // Independently calculated 70-digit compressible-Poiseuille benchmark, not production output.
    private static final double LAMINAR_K = 11292056105231.957431452538704223;
    private static final double Q = 0.0001;
    private static final double PA = 5_000_000;
    private static NumericalControls controls(double flowScale, int budget, double momentumTolerance) {
        return new NumericalControls(PA, flowScale, 1e-12, momentumTolerance, 1e-14, budget, 40);
    }
    private static Boundary pressure(double p) { return new Boundary(p, null); }
    private static Boundary injection(double q) { return new Boundary(null, q); }
    private static SimulationPipeSegmentInput laminarPipe(String id, String from, String to) {
        return new SimulationPipeSegmentInput(id, from, to, new BigDecimal("1000"), new BigDecimal("0.01"), BigDecimal.ZERO);
    }
    private SteadyStateGasSolution network(SimulationPhysicalNetworkInput net, Map<String, Boundary> boundaries,
            double pressureGuess, double flowGuess, NumericalControls settings) {
        var p = new HashMap<String, Double>(); var q = new HashMap<String, Double>();
        boundaries.forEach((id,b) -> { if (b.pressurePascalsAbsolute() == null) p.put(id, pressureGuess); });
        net.pipeSegments().forEach(edge -> q.put(edge.id(), flowGuess));
        return solver.solveSyntheticIdealGasNetwork(net, boundaries, GAS, settings, p, q);
    }
    private static void pressureReference(double expected, double actual) {
        assertEquals(expected, actual, Math.max(0.001, Math.abs(expected) * 1e-10));
    }
    private static void flowReference(double expected, double actual) {
        assertEquals(expected, actual, Math.max(1e-12, Math.abs(expected) * 1e-8));
    }
    /** Independent Poiseuille + incidence check, without solver residual/friction helpers. */
    private static void laminarResiduals(SimulationPhysicalNetworkInput net, Map<String, Boundary> boundaries,
            SteadyStateGasSolution result) {
        assertTrue(result.converged(), result.status());
        var balance = new HashMap<String, Double>();
        net.nodes().forEach(n -> balance.put(n.id(), 0d));
        for (var edge : net.pipeSegments()) {
            double q = result.pipeMassFlowKilogramsPerSecond().get(edge.id());
            balance.compute(edge.fromNodeId(), (k,v) -> v + q);
            balance.compute(edge.toNodeId(), (k,v) -> v - q);
            double from = result.nodePressurePascalsAbsolute().get(edge.fromNodeId());
            double to = result.nodePressurePascalsAbsolute().get(edge.toNodeId());
            assertTrue(Math.abs(to * to - from * from + LAMINAR_K * q) / (PA * PA) <= 1e-12);
        }
        double global = 0;
        for (var entry : boundaries.entrySet()) {
            double expected = entry.getValue().injectionKilogramsPerSecond() == null
                    ? result.pressureBoundaryInjectionKilogramsPerSecond().get(entry.getKey())
                    : entry.getValue().injectionKilogramsPerSecond();
            assertEquals(expected, balance.get(entry.getKey()), 1e-12); global += expected;
        }
        assertEquals(0, global, 1e-12);
    }

    @Test void independentForwardReverseAndRefinementReferences() {
        var net = tree(List.of(node("a",0),node("b",0)), List.of(laminarPipe("ab","a","b")));
        for (double sign : new double[]{1,-1}) {
            var boundaries = Map.of("a",pressure(PA),"b",injection(-sign * Q));
            for (double guess : new double[]{PA * 0.95, PA * 1.05}) for (double tolerance : new double[]{1e-12,1e-13}) {
                var result = network(net,boundaries,guess,0,controls(Q,30,tolerance));
                laminarResiduals(net,boundaries,result);
                flowReference(sign * Q,result.pipeMassFlowKilogramsPerSecond().get("ab"));
                pressureReference(sign > 0 ? 4999887.078163813571654 : 5000112.919285975805046,
                        result.nodePressurePascalsAbsolute().get("b"));
                assertTrue(result.iterations() > 0);
            }
        }
    }

    @Test void independentZeroFlowHydrostaticAndFlowingElevationReference() {
        var net = tree(List.of(node("a",0),node("b",100)),List.of(laminarPipe("ab","a","b")));
        var zero = network(net,Map.of("a",pressure(PA),"b",injection(0)),PA,0,controls(Q,30,1e-13));
        assertTrue(zero.converged(),zero.status());
        pressureReference(4964740.841675542439863,zero.nodePressurePascalsAbsolute().get("b"));
        flowReference(0,zero.pipeMassFlowKilogramsPerSecond().get("ab"));
        var result = network(net,Map.of("a",pressure(PA),"b",injection(-Q)),PA,0,controls(Q,30,1e-13));
        assertTrue(result.converged(),result.status());
        // Independently integrate linear elevation with Poiseuille loss (not production phi).
        double c = 0.018 / (8.31446261815324 * 300), exponent = -2 * c * 9.80665 * 100;
        double expectedSquared = PA * PA * Math.exp(exponent)
                - LAMINAR_K * Q * (Math.exp(exponent) - 1) / exponent;
        pressureReference(Math.sqrt(expectedSquared),result.nodePressurePascalsAbsolute().get("b"));
        flowReference(Q,result.pipeMassFlowKilogramsPerSecond().get("ab"));
    }

    @Test void actualBranchAndMeshedTriangleHaveIndependentFlowsAndPressures() {
        var nodes = List.of(node("a",0),node("b",0),node("c",0));
        var branch = tree(nodes,List.of(laminarPipe("ab","a","b"),laminarPipe("ac","a","c")));
        var branchBoundaries = Map.of("a",pressure(PA),"b",injection(-Q/2),"c",injection(-Q/2));
        var branched = network(branch,branchBoundaries,PA,0,controls(Q,30,1e-13));
        laminarResiduals(branch,branchBoundaries,branched);
        for (String id : List.of("ab","ac")) flowReference(Q/2,branched.pipeMassFlowKilogramsPerSecond().get(id));
        pressureReference(Math.sqrt(PA*PA-LAMINAR_K*Q/2),branched.nodePressurePascalsAbsolute().get("b"));
        var mesh = tree(nodes,List.of(laminarPipe("ab","a","b"),laminarPipe("ac","a","c"),laminarPipe("cb","c","b")));
        var boundaries = Map.of("a",pressure(PA),"b",injection(-Q),"c",injection(0));
        var result = network(mesh,boundaries,PA*0.97,0,controls(Q,30,1e-13));
        laminarResiduals(mesh,boundaries,result);
        flowReference(2*Q/3,result.pipeMassFlowKilogramsPerSecond().get("ab"));
        flowReference(Q/3,result.pipeMassFlowKilogramsPerSecond().get("ac"));
        flowReference(Q/3,result.pipeMassFlowKilogramsPerSecond().get("cb"));
        pressureReference(4999924.719059243116160,result.nodePressurePascalsAbsolute().get("b"));
        pressureReference(Math.sqrt(PA*PA-LAMINAR_K*Q/3),result.nodePressurePascalsAbsolute().get("c"));
        var reordered = tree(List.of(nodes.get(2),nodes.get(0),nodes.get(1)),
                List.of(mesh.pipeSegments().get(2),mesh.pipeSegments().get(1),mesh.pipeSegments().get(0)));
        assertEquals(result,network(reordered,boundaries,PA*0.97,0,controls(Q,30,1e-13)));
    }

    @Test void parallelReversedLinksAndMixedPressureBoundariesRemainDistinct() {
        var net = tree(List.of(node("a",0),node("b",0)),List.of(laminarPipe("ab","a","b"),laminarPipe("ba","b","a")));
        var boundaries = Map.of("a",pressure(PA),"b",injection(-Q));
        var result = network(net,boundaries,PA,0,controls(Q,30,1e-13));
        laminarResiduals(net,boundaries,result);
        flowReference(Q/2,result.pipeMassFlowKilogramsPerSecond().get("ab"));
        flowReference(-Q/2,result.pipeMassFlowKilogramsPerSecond().get("ba"));
        pressureReference(Math.sqrt(PA*PA-LAMINAR_K*Q/2),result.nodePressurePascalsAbsolute().get("b"));
        var three = tree(List.of(node("a",0),node("b",0),node("c",0)),List.of(laminarPipe("ac","a","c"),laminarPipe("cb","c","b")));
        double pb = Math.sqrt(PA*PA-LAMINAR_K*Q);
        var mixed = Map.of("a",pressure(PA),"b",pressure(pb),"c",injection(Q/2));
        var solved = network(three,mixed,PA,0,controls(Q,30,1e-13));
        laminarResiduals(three,mixed,solved);
        flowReference(Q/4,solved.pipeMassFlowKilogramsPerSecond().get("ac"));
        flowReference(3*Q/4,solved.pipeMassFlowKilogramsPerSecond().get("cb"));
        flowReference(Q/4,solved.pressureBoundaryInjectionKilogramsPerSecond().get("a"));
        flowReference(-3*Q/4,solved.pressureBoundaryInjectionKilogramsPerSecond().get("b"));
        assertThrows(UnsupportedOperationException.class,()->solved.pressureBoundaryInjectionKilogramsPerSecond().put("forged",1d));
    }

    // Separate scalar Swamee-Jain + integrated equation, evaluated without production helpers.
    private static double turbulentLoss(double q) {
        double reynolds = Math.abs(q) * 4 / (Math.PI * 0.5 * 1e-5);
        double reciprocalLog = 1 / Math.log10(0.000015 / 1.85 + 5.74 * Math.exp(-0.9 * Math.log(reynolds)));
        double darcy = reciprocalLog * reciprocalLog / 4;
        double area = Math.PI / 16;
        return darcy * 1000 * q * Math.abs(q) * (8.31446261815324 * 300 / 0.018) / (0.5 * area * area);
    }
    private static double bracketedTurbulentOracle(double deltaSquared) {
        double low = 0.1, high = 2;
        for (int i=0;i<80;i++) {double middle=(low+high)/2;
            if (turbulentLoss(middle) > deltaSquared) high=middle; else low=middle;}
        return (low+high)/2;
    }
    @Test void turbulentMixedPressureSolveHasIndependentBracketedReferenceAndIterationFailure() {
        var net = tree(List.of(node("a",0),node("b",0)),List.of(pipe("ab","a","b")));
        double pa=100000, pb=Math.sqrt(pa*pa-turbulentLoss(1));
        var boundaries=Map.of("a",pressure(pa),"b",pressure(pb));
        double expected=bracketedTurbulentOracle(pa*pa-pb*pb);
        for(double guess:new double[]{0.5,1.5}) for(double tolerance:new double[]{1e-13,1e-14}) {
            var solved=network(net,boundaries,PA,guess,controls(1,30,tolerance));
            assertTrue(solved.converged(),solved.status());assertTrue(solved.iterations()>1);
            flowReference(expected,solved.pipeMassFlowKilogramsPerSecond().get("ab"));
            double flow=solved.pipeMassFlowKilogramsPerSecond().get("ab");
            assertTrue(Math.abs(pb*pb-pa*pa+turbulentLoss(flow))/(PA*PA)<=1e-12);
        }
        var limited=network(net,boundaries,PA,0.5,controls(1,1,1e-14));
        assertFalse(limited.converged());assertEquals("ITERATION_LIMIT",limited.status());assertEquals(1,limited.iterations());
    }

    @Test void analyticalFrictionDerivativeMatchesIndependentCentralPerturbation() throws Exception {
        var edgeClass=Class.forName(SteadyStateGasSolver.class.getName()+"$Edge");
        var constructor=edgeClass.getDeclaredConstructors()[0];constructor.setAccessible(true);
        var edge=constructor.newInstance("e",0,1,0.5,0.000015,1d,1d,1e-5);
        var method=SteadyStateGasSolver.class.getDeclaredMethod("frictionProductAndDerivative",double.class,edgeClass);method.setAccessible(true);
        // Independently compute f*q*|q| by extracting the dimensional loss multiplier.
        double multiplier=1000*(8.31446261815324*300/0.018)/(0.5*Math.pow(Math.PI/16,2));
        for(double q:new double[]{-1d,1d}) {
            double h=1e-5;
            double derivative=(turbulentLoss(q+h)-turbulentLoss(q-h))/(2*h*multiplier);
            var actual=(double[])method.invoke(null,q,edge);
            assertEquals(derivative,actual[1],Math.abs(derivative)*1e-8);
        }
        var zero=(double[])method.invoke(null,0d,edge);
        assertEquals(16*Math.PI*0.5*1e-5,zero[1],1e-16);assertEquals(0d,zero[0]);
    }

    @Test void scaledConditioningAndUnsupportedRegimesFailHonestly() {
        var nodes=List.of(node("a",0),node("b",0));
        var huge=new SimulationPipeSegmentInput("huge","a","b",BigDecimal.ONE,new BigDecimal("1000"),BigDecimal.ZERO);
        var net=tree(nodes,List.of(laminarPipe("ab","a","b"),huge));
        var result=network(net,Map.of("a",pressure(PA),"b",pressure(PA)),PA,Q,controls(Q,30,1e-13));
        assertFalse(result.converged());assertEquals("SINGULAR_OR_ILL_CONDITIONED",result.status());
        var one=tree(nodes,List.of(laminarPipe("ab","a","b")));
        double transitional=3000*Math.PI*0.01*1e-5/4;
        var refused=network(one,Map.of("a",pressure(PA),"b",injection(-transitional)),PA,transitional,controls(Q,30,1e-13));
        assertFalse(refused.converged());assertEquals("UNSUPPORTED_PROPERTY_REGIME",refused.status());
        assertThrows(IllegalArgumentException.class,()->network(one,Map.of("a",injection(Q),"b",injection(-Q)),PA,0,controls(Q,30,1e-13)));
        assertThrows(IllegalArgumentException.class,()->new Boundary(PA,Q));
        assertThrows(IllegalArgumentException.class,()->new Boundary(null,null));
        assertThrows(IllegalArgumentException.class,()->new SyntheticGasProperties(0,0.018,1e-5));
        assertThrows(IllegalArgumentException.class,()->new NumericalControls(PA,Q,1e-12,1e-13,0,30,40));
        assertThrows(IllegalArgumentException.class,()->solver.solveSyntheticIdealGasNetwork(one,Map.of("a",pressure(PA),"b",injection(-Q)),GAS,controls(Q,30,1e-13),Map.of("b",PA),Map.of("bad",0d)));
    }

    @Test void nonphysicalDemandAndFailedLineSearchDoNotClaimConvergence() {
        var one=tree(List.of(node("a",0),node("b",0)),List.of(laminarPipe("ab","a","b")));
        // Small pressure datum cannot sustain this imposed loss in the declared model.
        var failed=network(one,Map.of("a",pressure(100),"b",injection(-Q)),100,0,controls(Q,30,1e-13));
        assertFalse(failed.converged());
        assertTrue(List.of("NONPHYSICAL_PRESSURE","LINE_SEARCH_FAILED","ITERATION_LIMIT").contains(failed.status()),failed.status());
        var lowBudget=new NumericalControls(PA,1,1e-12,1e-14,1e-14,30,1);
        var turbulent=tree(List.of(node("a",0),node("b",0)),List.of(pipe("ab","a","b")));
        double pa=100000,pb=Math.sqrt(pa*pa-turbulentLoss(1));
        var refused=network(turbulent,Map.of("a",pressure(pa),"b",pressure(pb)),PA,0.016,lowBudget);
        assertFalse(refused.converged());assertTrue(List.of("LINE_SEARCH_FAILED","UNSUPPORTED_PROPERTY_REGIME").contains(refused.status()),refused.status());
    }

    private static final SyntheticGasProperties EQUIPMENT_GAS=new SyntheticGasProperties(300,0.018,0.01);
    private static final NumericalControls EQUIPMENT_CONTROLS=
            new NumericalControls(200000,2,1e-11,1e-12,1e-14,60,40);
    private static SimulationEquipmentParameterRevision oneDevice(Kind kind,double control) {
        var all=SimulationSyntheticEquipmentNetworkInputTest.approved();
        var old=all.equipment().stream().filter(e->e.kind()==kind).findFirst().orElseThrow();
        var next=new Equipment(old.id(),"a","b",kind,
                kind==Kind.COMPRESSOR?old.curveId():null,
                kind==Kind.COMPRESSOR?old.curveRevisionId():null,
                kind==Kind.COMPRESSOR?BigDecimal.valueOf(control):null,
                kind==Kind.VALVE?old.characteristicId():null,
                kind==Kind.VALVE?old.characteristicRevisionId():null,
                kind==Kind.VALVE?BigDecimal.valueOf(control):null);
        var limits=all.governedLimits().stream().filter(x->x.equipmentId().equals(old.id())).toList();
        return new SimulationEquipmentParameterRevision(all.sourceId(),all.revisionId(),
                all.recordedAt(),all.effectiveFrom(),all.effectiveUntil(),all.origin(),all.evidenceReference(),
                all.networkSourceId(),all.networkRevisionId(),all.networkSha256(),
                all.fluidSourceId(),all.fluidRevisionId(),all.fluidSha256(),all.fluidQualificationId(),
                List.of(next),kind==Kind.COMPRESSOR?all.compressorCurves():List.of(),
                kind==Kind.VALVE?all.valveCharacteristics():List.of(),limits,all.governanceBinding());
    }
    private static SimulationSyntheticEquipmentNetworkInput equipmentNet(
            SimulationEquipmentParameterRevision rev,boolean downstreamPipe){
        var nodes=downstreamPipe?List.of(node("a",0),node("b",0),node("c",0)):
                List.of(node("a",0),node("b",0));
        var pipes=downstreamPipe?List.of(new SimulationPipeSegmentInput("bc","b","c",
                new BigDecimal("1000"),BigDecimal.ONE,BigDecimal.ZERO)):List.<SimulationPipeSegmentInput>of();
        return new SimulationSyntheticEquipmentNetworkInput("synthetic-network",
                rev.networkSourceId(),rev.networkRevisionId(),rev.networkSha256(),
                rev.fluidSourceId(),rev.fluidRevisionId(),rev.fluidSha256(),rev.fluidQualificationId(),
                SimulationEquipmentParameterRevisionTest.AT,nodes,pipes,rev,
                rev.equipment().stream().anyMatch(e->e.kind()==Kind.VALVE)
                        ?Map.of("valve",SimulationEquipmentBehaviorEvaluator.VALVE):Map.of());
    }
    private SimulationEquipmentGasSolution equipment(SimulationSyntheticEquipmentNetworkInput net,
            Map<String,Boundary> boundary,Map<String,Double> guessP,Map<String,Double> guessPipes,
            Map<String,Double> guessDevices){
        return solver.solveSyntheticIdealGasEquipmentNetwork(net,boundary,EQUIPMENT_GAS,
                EQUIPMENT_CONTROLS,guessP,guessPipes,guessDevices);
    }
    @Test void independentCompressorBridgePressurePowerAndPipeMassBalance(){
        var input=equipmentNet(oneDevice(Kind.COMPRESSOR,1500),true);
        var b=Map.of("a",pressure(200000),"b",injection(0),"c",injection(-1.5));
        var result=equipment(input,b,Map.of("b",200192d,"c",199770d),
                Map.of("bc",1.5),Map.of("compressor",1.5));
        assertTrue(result.pipeSolution().converged(),result.pipeSolution().status());
        assertEquals(200194.936153744447,result.pipeSolution().nodePressurePascalsAbsolute().get("b"),0.0002);
        assertEquals(199771.448460542870,result.pipeSolution().nodePressurePascalsAbsolute().get("c"),0.0002);
        assertEquals(1.5,result.equipmentMassFlowsKilogramsPerSecond().get("compressor"),1e-8);
        assertEquals(1.5,result.pipeSolution().pipeMassFlowKilogramsPerSecond().get("bc"),1e-8);
        assertEquals(1.5,result.pipeSolution().pressureBoundaryInjectionKilogramsPerSecond().get("a"),1e-8);
        assertEquals(261.290322580645,result.compressorShaftPowerWatts().get("compressor"),1e-5);
        assertTrue(Math.abs(result.compressorLogPressureResiduals().get("compressor"))<=1e-12);
        assertEquals(Map.of("bc",result.pipeSolution().pipeMassFlowKilogramsPerSecond().get("bc")),
                result.pipeSolution().pipeMassFlowKilogramsPerSecond());
    }
    @Test void independentValveBridgePressureAndOpeningMap(){
        var input=equipmentNet(oneDevice(Kind.VALVE,0.5),true);
        var b=Map.of("a",pressure(200000),"b",injection(0),"c",injection(-0.5));
        var result=equipment(input,b,Map.of("b",199498d,"c",199357d),
                Map.of("bc",0.5),Map.of("valve",0.5));
        assertTrue(result.pipeSolution().converged(),result.pipeSolution().status());
        assertEquals(199500,result.pipeSolution().nodePressurePascalsAbsolute().get("b"),0.0002);
        assertEquals(199358.445317658515,result.pipeSolution().nodePressurePascalsAbsolute().get("c"),0.0002);
        assertEquals(0.5,result.equipmentMassFlowsKilogramsPerSecond().get("valve"),1e-8);
        assertEquals(0.5,result.pipeSolution().pressureBoundaryInjectionKilogramsPerSecond().get("a"),1e-8);
        assertTrue(Math.abs(result.valveFlowResidualsKilogramsPerSecond().get("valve"))<=1e-11);
    }
    @Test void equipmentOnlyCompressorAndValveHaveNoFakePipeFlows(){
        var comp=equipmentNet(oneDevice(Kind.COMPRESSOR,1500),false);
        var result=equipment(comp,Map.of("a",pressure(200000),"b",injection(-1.5)),
                Map.of("b",200190d),Map.of(),Map.of("compressor",1.5));
        assertTrue(result.pipeSolution().converged(),result.pipeSolution().status());
        assertTrue(result.pipeSolution().pipeMassFlowKilogramsPerSecond().isEmpty());
        var valve=equipmentNet(oneDevice(Kind.VALVE,0.5),false);
        var r=equipment(valve,Map.of("a",pressure(200000),"b",injection(-0.5)),
                Map.of("b",199495d),Map.of(),Map.of("valve",0.5));
        assertTrue(r.pipeSolution().converged(),r.pipeSolution().status());
        assertTrue(r.pipeSolution().pipeMassFlowKilogramsPerSecond().isEmpty());
    }
    @Test void closedValveRequiresEveryActiveComponentAnchored(){
        var net=equipmentNet(oneDevice(Kind.VALVE,0),true);
        assertThrows(IllegalArgumentException.class,()->equipment(net,
                Map.of("a",pressure(200000),"b",injection(0),"c",injection(0)),
                Map.of("b",200000d,"c",200000d),Map.of("bc",0d),Map.of("valve",0d)));
        var anchored=equipment(net,Map.of("a",pressure(200000),"b",injection(0),"c",pressure(199000)),
                Map.of("b",199000d),Map.of("bc",0d),Map.of("valve",0d));
        assertTrue(anchored.pipeSolution().converged(),anchored.pipeSolution().status());
        assertEquals(0,anchored.equipmentMassFlowsKilogramsPerSecond().get("valve"),1e-11);
        assertEquals(0,anchored.pipeSolution().pressureBoundaryInjectionKilogramsPerSecond().get("a"),1e-11);
        assertEquals(0,anchored.pipeSolution().pressureBoundaryInjectionKilogramsPerSecond().get("c"),1e-11);
    }
    @Test void invalidEquipmentGuessAndUnanchoredUnionRefuse(){
        var v=equipmentNet(oneDevice(Kind.VALVE,0.5),false);
        assertThrows(IllegalArgumentException.class,()->equipment(v,
                Map.of("a",injection(0),"b",injection(0)),Map.of("a",200000d,"b",199500d),
                Map.of(),Map.of("valve",0.5)));
        assertThrows(IllegalArgumentException.class,()->equipment(v,
                Map.of("a",pressure(200000),"b",injection(-0.5)),Map.of("b",199500d),
                Map.of(),Map.of("valve",-0.5)));
    }

}
