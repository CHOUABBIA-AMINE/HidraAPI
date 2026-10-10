/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentBehaviorEvaluatorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Domain Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.service
 *
 * @Description : Verifies independent synthetic interpolation and refusal cases.
 *
 */
package dz.sh.hidra.modules.simulation.domain.service;

import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision.*;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevisionTest;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimulationEquipmentBehaviorEvaluatorTest {
    private final SimulationEquipmentBehaviorEvaluator evaluator=new SimulationEquipmentBehaviorEvaluator();
    private static final java.time.Instant AT=SimulationEquipmentParameterRevisionTest.AT;
    static CompressorCurve curve(){
        var v=SimulationEquipmentParameterRevisionTest.curve(Origin.SYNTHETIC,AT,AT,null);
        return new CompressorCurve(v.id(),v.revisionId(),v.recordedAt(),v.effectiveFrom(),v.effectiveUntil(),
            v.origin(),v.evidenceReference(),v.fluidSourceId(),v.fluidRevisionId(),v.fluidSha256(),
            SimulationEquipmentBehaviorEvaluator.HEAD,SimulationEquipmentBehaviorEvaluator.EFFICIENCY,
            SimulationEquipmentBehaviorEvaluator.LINEAR,v.referenceInletPressurePascalsAbsolute(),
            v.referenceInletTemperatureKelvin(),v.speedLines());
    }
    static ValveCharacteristic valve(){
        return SimulationEquipmentParameterRevisionTest.valve(Origin.SYNTHETIC,AT,AT,null);
    }
    @Test void compressorInterpolationHasIndependentFourCornerArithmeticAndAnalyticDerivatives(){
        var v=evaluator.compressor(curve(),1.5,1500);
        assertEquals(135,v.headJoulesPerKilogram(),1e-12);
        assertEquals(0.775,v.efficiency(),1e-12);
        assertEquals(-30,v.headFlowDerivative(),1e-12);
        assertEquals(0.09,v.headSpeedDerivative(),1e-12);
        assertEquals(-0.05,v.efficiencyFlowDerivative(),1e-12);
        assertEquals(0,v.efficiencySpeedDerivative(),1e-12);
        double h=1e-5,hn=0.01;
        assertEquals(v.headFlowDerivative(),(evaluator.compressor(curve(),1.5+h,1500).headJoulesPerKilogram()
                -evaluator.compressor(curve(),1.5-h,1500).headJoulesPerKilogram())/(2*h),1e-8);
        assertEquals(v.headSpeedDerivative(),(evaluator.compressor(curve(),1.5,1500+hn).headJoulesPerKilogram()
                -evaluator.compressor(curve(),1.5,1500-hn).headJoulesPerKilogram())/(2*hn),1e-10);
    }
    @Test void valveDerivativesAreIndependentAndClosedLineIsZero(){
        var v=evaluator.valve(valve(),SimulationEquipmentBehaviorEvaluator.VALVE,500,0.5);
        assertEquals(0.5,v.massFlowKilogramsPerSecond(),1e-12);
        assertEquals(0.001,v.flowDifferentialPressureDerivative(),1e-12);
        assertEquals(1,v.flowOpeningDerivative(),1e-12);
        assertFalse(v.closed());
        assertTrue(evaluator.valve(valve(),SimulationEquipmentBehaviorEvaluator.VALVE,500,0).closed());
        assertEquals(0,evaluator.valve(valve(),SimulationEquipmentBehaviorEvaluator.VALVE,500,0).massFlowKilogramsPerSecond(),1e-12);
    }
    @Test void exactEndpointOneSidedConventionsAndUnknownMethods(){
        assertEquals(100,evaluator.compressor(curve(),1,1000).headJoulesPerKilogram(),1e-12);
        assertEquals(160,evaluator.compressor(curve(),2,2000).headJoulesPerKilogram(),1e-12);
        assertEquals(2,evaluator.valve(valve(),SimulationEquipmentBehaviorEvaluator.VALVE,1000,1).massFlowKilogramsPerSecond(),1e-12);
        assertThrows(IllegalArgumentException.class,()->evaluator.compressor(curve(),2.01,1500));
        assertThrows(IllegalArgumentException.class,()->evaluator.compressor(curve(),1.5,2100));
        assertThrows(IllegalArgumentException.class,()->evaluator.valve(valve(),"generic",500,0.5));
        assertThrows(IllegalArgumentException.class,()->evaluator.valve(valve(),SimulationEquipmentBehaviorEvaluator.VALVE,-1,0.5));
        var c=SimulationEquipmentParameterRevisionTest.curve(Origin.SYNTHETIC,AT,AT,null);
        assertThrows(IllegalArgumentException.class,()->evaluator.compressor(c,1.5,1500));
    }
    @Test void rectangularMapRequirementAndUnsupportedActiveFlatLine(){
        var c=curve();
        var bad=new CompressorCurve(c.id(),c.revisionId(),c.recordedAt(),c.effectiveFrom(),c.effectiveUntil(),
                c.origin(),c.evidenceReference(),c.fluidSourceId(),c.fluidRevisionId(),c.fluidSha256(),
                c.headDefinitionReference(),c.efficiencyDefinitionReference(),c.interpolationMethodReference(),
                c.referenceInletPressurePascalsAbsolute(),c.referenceInletTemperatureKelvin(),
                List.of(c.speedLines().get(0),new SpeedLine(new BigDecimal("2000"),List.of(
                    new CompressorPoint(new BigDecimal("1.1"),new BigDecimal("200"),new BigDecimal("0.8")),
                    new CompressorPoint(new BigDecimal("2"),new BigDecimal("160"),new BigDecimal("0.75"))))));
        assertThrows(IllegalArgumentException.class,()->evaluator.compressor(bad,1.5,1500));
        var v=valve();
        var badValve=new ValveCharacteristic(v.id(),v.revisionId(),v.recordedAt(),v.effectiveFrom(),v.effectiveUntil(),
                v.origin(),v.evidenceReference(),v.fluidSourceId(),v.fluidRevisionId(),v.fluidSha256(),
                v.referenceTemperatureKelvin(),List.of(v.openingLines().getFirst(),
                    new OpeningLine(new BigDecimal("1"),List.of(
                        new ValvePoint(BigDecimal.ZERO,BigDecimal.ZERO),
                        new ValvePoint(new BigDecimal("1000"),BigDecimal.ZERO)))));
        assertThrows(IllegalArgumentException.class,()->evaluator.valve(badValve,SimulationEquipmentBehaviorEvaluator.VALVE,500,0.5));
    }

    // Frozen synthetic reference matrix (fe7be4b); map equations are independent of evaluator internals.
    private static void referenceDerivative(double expected,double actual) {
        assertEquals(expected,actual,Math.max(1e-10,1e-8*Math.abs(expected)));
    }
    @Test void independentCompressorOneSidedEndpointAndInteriorDerivatives() {
        var c=curve();
        double dq=1e-5,dn=0.01;
        for(double q:new double[]{1,1.5,2}) for(double rpm:new double[]{1000,1500,2000}) {
            var value=evaluator.compressor(c,q,rpm);
            // Independent bilinear model: H(1000,q)=120-20q; H(2000,q)=240-40q.
            double head=(120-20*q)*(2-rpm/1000)+(240-40*q)*(rpm/1000-1);
            assertEquals(head,value.headJoulesPerKilogram(),1e-12);
            assertEquals(0.85-0.05*q,value.efficiency(),1e-12);
            referenceDerivative(-20*rpm/1000,value.headFlowDerivative());
            referenceDerivative(0.12-0.02*q,value.headSpeedDerivative());
            referenceDerivative(-0.05,value.efficiencyFlowDerivative());
            referenceDerivative(0,value.efficiencySpeedDerivative());
            double nextQ=q==2?q-dq:q+dq,nextN=rpm==2000?rpm-dn:rpm+dn;
            double slopeQ=(evaluator.compressor(c,nextQ,rpm).headJoulesPerKilogram()-value.headJoulesPerKilogram())/(nextQ-q);
            double slopeN=(evaluator.compressor(c,q,nextN).headJoulesPerKilogram()-value.headJoulesPerKilogram())/(nextN-rpm);
            referenceDerivative(value.headFlowDerivative(),slopeQ);
            referenceDerivative(value.headSpeedDerivative(),slopeN);
            double etaSlope=(evaluator.compressor(c,nextQ,rpm).efficiency()-value.efficiency())/(nextQ-q);
            referenceDerivative(value.efficiencyFlowDerivative(),etaSlope);
        }
    }
    @Test void independentValveEndpointOneSidedAndInteriorFiniteDifferences() {
        var v=valve();
        double ddp=0.01, da=1e-5;
        for(double dp:new double[]{0,500,1000}) for(double a:new double[]{0.25,0.5,1}) {
            var value=evaluator.valve(v,SimulationEquipmentBehaviorEvaluator.VALVE,dp,a);
            assertEquals(0.002*dp*a,value.massFlowKilogramsPerSecond(),1e-12);
            referenceDerivative(0.002*a,value.flowDifferentialPressureDerivative());
            referenceDerivative(0.002*dp,value.flowOpeningDerivative());
            double nextDp=dp==1000?dp-ddp:dp+ddp,nextA=a==1?a-da:a+da;
            double dpSlope=(evaluator.valve(v,SimulationEquipmentBehaviorEvaluator.VALVE,nextDp,a)
                    .massFlowKilogramsPerSecond()-value.massFlowKilogramsPerSecond())/(nextDp-dp);
            double aSlope=(evaluator.valve(v,SimulationEquipmentBehaviorEvaluator.VALVE,dp,nextA)
                    .massFlowKilogramsPerSecond()-value.massFlowKilogramsPerSecond())/(nextA-a);
            referenceDerivative(value.flowDifferentialPressureDerivative(),dpSlope);
            referenceDerivative(value.flowOpeningDerivative(),aSlope);
        }
        for(double dp:new double[]{0,500,1000}) {
            var shut=evaluator.valve(v,SimulationEquipmentBehaviorEvaluator.VALVE,dp,0);
            assertTrue(shut.closed());
            assertEquals(0,shut.massFlowKilogramsPerSecond(),0);
            assertThrows(IllegalArgumentException.class,()->evaluator.valve(v,SimulationEquipmentBehaviorEvaluator.VALVE,dp,1.00001));
        }
        assertThrows(IllegalArgumentException.class,()->evaluator.valve(v,SimulationEquipmentBehaviorEvaluator.VALVE,1000.01,0.5));
        assertThrows(IllegalArgumentException.class,()->evaluator.compressor(curve(),Double.NaN,1500));
    }

}
