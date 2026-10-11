/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRegulatorCharacteristicRevisionTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Verifies regulator provenance, exact references and bounded source-only configuration.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision.*;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevisionTest.*;

public class SimulationRegulatorCharacteristicRevisionTest {
    public static RegulatorConfiguration configuration(Isolation isolation){return new RegulatorConfiguration("reg-map","g1",
        "SYNTHETIC_REGULATOR_DOWNSTREAM_SETPOINT_CAPACITY_V1",ControlMode.DOWNSTREAM_SETPOINT,isolation,d("180000.00"));}
    public static SimulationRegulatorCharacteristicRevision characteristic(){return characteristic(Origin.SYNTHETIC,AT,AT,null,"fluid","f1","b".repeat(64),"0.01","300.00","100000.00","900000.00");}
    public static SimulationRegulatorCharacteristicRevision characteristic(Origin origin,Instant recorded,Instant from,Instant until,
        String fluid,String rev,String hash,String coefficient,String temperature,String min,String max){
        return new SimulationRegulatorCharacteristicRevision("reg-map","g1",recorded,from,until,origin,"synthetic regulator fixture",fluid,rev,hash,d(temperature),
            SimulationRegulatorCharacteristicRevision.METHOD,d(coefficient),d(min),d(max),d("100000.00"),d("800000.00"),d("1.00"),d("800000.00"));}
    public static SimulationEquipmentParameterRevision regulatorFixture(){return buildRegulator(characteristic(),configuration(Isolation.OPEN));}
    public static SimulationEquipmentParameterRevision buildRegulator(SimulationRegulatorCharacteristicRevision c,RegulatorConfiguration config){
        var v=fixture("r1");return copy(v,Origin.SYNTHETIC,List.of(new Equipment("regulator","a","b",Kind.REGULATOR,null,null,null,null,null,null,config)),
            List.of(c),List.of(new GovernedLimit("regulator",Quantity.INLET_PRESSURE_PASCALS_ABSOLUTE,d("100000.00"),d("900000.00"),"synthetic regulator range"),
                new GovernedLimit("regulator",Quantity.INLET_TEMPERATURE_KELVIN,d("250.00"),d("350.00"),"synthetic regulator range"),
                new GovernedLimit("regulator",Quantity.MASS_FLOW_KILOGRAMS_PER_SECOND,d("0.00"),d("3.00"),"synthetic regulator range"),
                new GovernedLimit("regulator",Quantity.DOWNSTREAM_SETPOINT_PRESSURE_PASCALS_ABSOLUTE,d("100000.00"),d("800000.00"),"synthetic regulator range")));}
    public static SimulationEquipmentParameterRevision copy(SimulationEquipmentParameterRevision v,Origin origin,List<Equipment> items,
        List<SimulationRegulatorCharacteristicRevision> cs,List<GovernedLimit> limits){return new SimulationEquipmentParameterRevision(v.sourceId(),v.revisionId(),v.recordedAt(),v.effectiveFrom(),v.effectiveUntil(),origin,v.evidenceReference(),
        v.networkSourceId(),v.networkRevisionId(),v.networkSha256(),v.fluidSourceId(),v.fluidRevisionId(),v.fluidSha256(),v.fluidQualificationId(),items,List.of(),List.of(),limits,v.governanceBinding(),cs);}
    @Test void explicitIsolationAndImmutableMetadataArePreserved(){var v=regulatorFixture();assertTrue(v.effectiveAt(AT));assertFalse(v.effectiveAt(AT.minusNanos(1)));
        assertEquals(Isolation.OPEN,v.equipment().getFirst().regulator().isolation());assertEquals(Isolation.CLOSED,buildRegulator(characteristic(),configuration(Isolation.CLOSED)).equipment().getFirst().regulator().isolation());
        assertThrows(UnsupportedOperationException.class,()->v.regulatorCharacteristics().clear());assertTrue(fixture("r1").regulatorCharacteristics().isEmpty());}
    @Test void refusesNonSyntheticMissingOriginFutureRecordedExpiredOrWrongFluid(){for(var origin:Arrays.asList(null,Origin.DECLARED_PARAMETER))
        assertThrows(InvalidSimulationValueException.class,()->characteristic(origin,AT,AT,null,"fluid","f1","b".repeat(64),"0.01","300.00","100000.00","900000.00"));
        var future=characteristic(Origin.SYNTHETIC,AT.plusNanos(1),AT,null,"fluid","f1","b".repeat(64),"0.01","300.00","100000.00","900000.00");
        assertThrows(InvalidSimulationValueException.class,()->buildRegulator(future,configuration(Isolation.OPEN)));
        var exp=characteristic(Origin.SYNTHETIC,AT,AT,AT.plusSeconds(1),"fluid","f1","b".repeat(64),"0.01","300.00","100000.00","900000.00");assertFalse(buildRegulator(exp,configuration(Isolation.OPEN)).effectiveAt(AT.plusSeconds(1)));
        for(var fluid:List.of("wrong","fluid")){var bad=characteristic(Origin.SYNTHETIC,AT,AT,null,fluid,"wrong-revision","c".repeat(64),"0.01","300.00","100000.00","900000.00");
            assertThrows(InvalidSimulationValueException.class,()->buildRegulator(bad,configuration(Isolation.OPEN)));}
        var v=regulatorFixture();assertThrows(InvalidSimulationValueException.class,()->copy(v,Origin.DECLARED_PARAMETER,v.equipment(),v.regulatorCharacteristics(),v.governedLimits()));}
    @Test void refusesNonpositiveUnorderedDomainAndUnknownMethodOrControl(){for(var zero:List.of("0","-1"))assertThrows(InvalidSimulationValueException.class,()->characteristic(Origin.SYNTHETIC,AT,AT,null,"fluid","f1","b".repeat(64),zero,"300","100000","900000"));
        assertThrows(InvalidSimulationValueException.class,()->characteristic(Origin.SYNTHETIC,AT,AT,null,"fluid","f1","b".repeat(64),"0.01","0","100000","900000"));
        assertThrows(InvalidSimulationValueException.class,()->characteristic(Origin.SYNTHETIC,AT,AT,null,"fluid","f1","b".repeat(64),"0.01","300","900000","100000"));
        for(String method:Arrays.asList(null,"VALVE","unknown"))assertThrows(InvalidSimulationValueException.class,()->new RegulatorConfiguration("reg-map","g1",method,ControlMode.DOWNSTREAM_SETPOINT,Isolation.OPEN,d("180000")));
        assertThrows(InvalidSimulationValueException.class,()->new RegulatorConfiguration("reg-map","g1",configuration(Isolation.OPEN).method(),null,Isolation.OPEN,d("180000")));
        assertThrows(InvalidSimulationValueException.class,()->new RegulatorConfiguration("reg-map","g1",configuration(Isolation.OPEN).method(),ControlMode.DOWNSTREAM_SETPOINT,null,d("180000")));}
    @Test void refusesMissingDuplicateUnusedCharacteristicAndKindSpecificFields(){var v=regulatorFixture();var c=characteristic();
        assertThrows(InvalidSimulationValueException.class,()->copy(v,v.origin(),v.equipment(),List.of(),v.governedLimits()));
        assertThrows(InvalidSimulationValueException.class,()->copy(v,v.origin(),v.equipment(),List.of(c,c),v.governedLimits()));
        assertThrows(InvalidSimulationValueException.class,()->copy(v,v.origin(),List.of(),List.of(c),List.of()));
        assertThrows(InvalidSimulationValueException.class,()->new Equipment("e","a","b",Kind.REGULATOR,null,null,null,null,null,null));
        assertThrows(InvalidSimulationValueException.class,()->new Equipment("e","a","b",Kind.VALVE,null,null,null,"v","r",d("0.5"),configuration(Isolation.OPEN)));
        assertThrows(InvalidSimulationValueException.class,()->new Equipment("e","a","b",Kind.REGULATOR,"c","r",d("1"),null,null,null,configuration(Isolation.OPEN)));
        var wrong=new RegulatorConfiguration("wrong","g1",configuration(Isolation.OPEN).method(),ControlMode.DOWNSTREAM_SETPOINT,Isolation.OPEN,d("180000"));
        assertThrows(InvalidSimulationValueException.class,()->buildRegulator(c,wrong));}
    @Test void refusesAbsentLimitsSetpointAndTemperatureOrDomainOutsideSuppliedRanges(){var v=regulatorFixture();
        assertThrows(InvalidSimulationValueException.class,()->copy(v,v.origin(),v.equipment(),v.regulatorCharacteristics(),List.of()));
        var bad=new RegulatorConfiguration("reg-map","g1",configuration(Isolation.OPEN).method(),ControlMode.DOWNSTREAM_SETPOINT,Isolation.OPEN,d("900000"));assertThrows(InvalidSimulationValueException.class,()->buildRegulator(characteristic(),bad));
        var cold=characteristic(Origin.SYNTHETIC,AT,AT,null,"fluid","f1","b".repeat(64),"0.01","200.00","100000.00","900000.00");assertThrows(InvalidSimulationValueException.class,()->buildRegulator(cold,configuration(Isolation.OPEN)));
        var narrow=characteristic(Origin.SYNTHETIC,AT,AT,null,"fluid","f1","b".repeat(64),"0.01","300.00","200000.00","900000.00");assertThrows(InvalidSimulationValueException.class,()->buildRegulator(narrow,configuration(Isolation.OPEN)));}
}
