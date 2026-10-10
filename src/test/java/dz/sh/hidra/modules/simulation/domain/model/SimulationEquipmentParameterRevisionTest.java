/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentParameterRevisionTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Maintains immutable governed equipment source evidence.
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

public class SimulationEquipmentParameterRevisionTest {
    public static final Instant AT=Instant.parse("2020-01-01T00:00:00.123456789Z");
    public static BigDecimal d(String v){return new BigDecimal(v);}
    public static CompressorCurve curve(Origin o,Instant r,Instant from,Instant until){return new CompressorCurve("curve","c1",r,from,until,o,"synthetic supplied map",
            "fluid","f1","b".repeat(64),"synthetic head definition","synthetic efficiency definition","synthetic interpolation declaration",d("200000.00"),d("300.00"),
            List.of(new SpeedLine(d("1000.00"),List.of(new CompressorPoint(d("1.00"),d("100.00"),d("0.80")),new CompressorPoint(d("2.00"),d("80.00"),d("0.75")))),
                    new SpeedLine(d("2000.00"),List.of(new CompressorPoint(d("1.00"),d("200.00"),d("0.80")),new CompressorPoint(d("2.00"),d("160.00"),d("0.75"))))));}
    public static ValveCharacteristic valve(Origin o,Instant r,Instant from,Instant until){return new ValveCharacteristic("valve-map","v1",r,from,until,o,"synthetic supplied valve",
            "fluid","f1","b".repeat(64),d("300.00"),List.of(new OpeningLine(d("0.00"),List.of(new ValvePoint(d("0.00"),d("0.00")),new ValvePoint(d("1000.00"),d("0.00")))),
                    new OpeningLine(d("1.00"),List.of(new ValvePoint(d("0.00"),d("0.00")),new ValvePoint(d("1000.00"),d("2.00"))))));}
    public static SimulationEquipmentParameterRevision fixture(String rev){return build(rev,Origin.SYNTHETIC,curve(Origin.SYNTHETIC,AT,AT,null),valve(Origin.SYNTHETIC,AT,AT,null));}
    public static SimulationEquipmentParameterRevision build(String rev,Origin o,CompressorCurve c,ValveCharacteristic v){
        var items=List.of(new Equipment("compressor","a","b",Kind.COMPRESSOR,c.id(),c.revisionId(),d("1500.00"),null,null,null),
                new Equipment("valve","b","a",Kind.VALVE,null,null,null,v.id(),v.revisionId(),d("0.50")));
        var limits=new ArrayList<GovernedLimit>();for(var e:items){limits.add(new GovernedLimit(e.id(),Quantity.INLET_PRESSURE_PASCALS_ABSOLUTE,d("100000.00"),d("900000.00"),"synthetic range"));
            limits.add(new GovernedLimit(e.id(),Quantity.INLET_TEMPERATURE_KELVIN,d("250.00"),d("350.00"),"synthetic range"));
            limits.add(new GovernedLimit(e.id(),Quantity.MASS_FLOW_KILOGRAMS_PER_SECOND,d("0.00"),d("2.00"),"synthetic range"));
            limits.add(new GovernedLimit(e.id(),e.kind()==Kind.COMPRESSOR?Quantity.ROTATIONAL_SPEED_REVOLUTIONS_PER_MINUTE:Quantity.OPENING_FRACTION,
                    e.kind()==Kind.COMPRESSOR?d("1000.00"):d("0.00"),e.kind()==Kind.COMPRESSOR?d("2000.00"):d("1.00"),"synthetic range"));}
        return new SimulationEquipmentParameterRevision("equipment",rev,AT,AT,null,o,"synthetic development fixture","network","n1","a".repeat(64),
                "fluid","f1","b".repeat(64),"fluid-qualified",items,List.of(c),List.of(v),limits,new GovernanceBinding("definition",1,"type","purpose"));
    }
    public static SimulationEquipmentParameterRevision rebind(SimulationEquipmentParameterRevision v,String source,String network,String networkRev,String networkHash,
            String fluid,String fluidRev,String fluidHash,String fluidQ,GovernanceBinding binding){
        var c=v.compressorCurves().getFirst();var z=v.valveCharacteristics().getFirst();
        c=new CompressorCurve(c.id(),c.revisionId(),c.recordedAt(),c.effectiveFrom(),c.effectiveUntil(),c.origin(),c.evidenceReference(),fluid,fluidRev,fluidHash,c.headDefinitionReference(),c.efficiencyDefinitionReference(),c.interpolationMethodReference(),c.referenceInletPressurePascalsAbsolute(),c.referenceInletTemperatureKelvin(),c.speedLines());
        z=new ValveCharacteristic(z.id(),z.revisionId(),z.recordedAt(),z.effectiveFrom(),z.effectiveUntil(),z.origin(),z.evidenceReference(),fluid,fluidRev,fluidHash,z.referenceTemperatureKelvin(),z.openingLines());
        // Independent characteristics use identities scoped to this synthetic fixture.
        c=new CompressorCurve(source+"-curve",c.revisionId(),c.recordedAt(),c.effectiveFrom(),c.effectiveUntil(),c.origin(),c.evidenceReference(),fluid,fluidRev,fluidHash,c.headDefinitionReference(),c.efficiencyDefinitionReference(),c.interpolationMethodReference(),c.referenceInletPressurePascalsAbsolute(),c.referenceInletTemperatureKelvin(),c.speedLines());
        z=new ValveCharacteristic(source+"-valve",z.revisionId(),z.recordedAt(),z.effectiveFrom(),z.effectiveUntil(),z.origin(),z.evidenceReference(),fluid,fluidRev,fluidHash,z.referenceTemperatureKelvin(),z.openingLines());
        var items=List.of(new Equipment("compressor","a","b",Kind.COMPRESSOR,c.id(),c.revisionId(),d("1500.00"),null,null,null),new Equipment("valve","b","a",Kind.VALVE,null,null,null,z.id(),z.revisionId(),d("0.50")));
        return new SimulationEquipmentParameterRevision(source,v.revisionId(),v.recordedAt(),v.effectiveFrom(),v.effectiveUntil(),v.origin(),v.evidenceReference(),network,networkRev,networkHash,fluid,fluidRev,fluidHash,fluidQ,items,List.of(c),List.of(z),v.governedLimits(),binding);
    }
    @Test void suppliedMapsLimitsOriginsAreImmutable(){var v=fixture("r1");assertEquals(2,v.equipment().size());assertEquals(8,v.governedLimits().size());assertEquals(AT,v.compressorCurves().getFirst().recordedAt());
        assertThrows(UnsupportedOperationException.class,()->v.equipment().clear());assertThrows(UnsupportedOperationException.class,()->v.valveCharacteristics().getFirst().openingLines().clear());}
    @Test void syntheticCurveOrValveCannotBePromoted(){for(var kind:Kind.values()){var c=curve(kind==Kind.COMPRESSOR?Origin.SYNTHETIC:Origin.DECLARED_PARAMETER,AT,AT,null);
        var v=valve(kind==Kind.VALVE?Origin.SYNTHETIC:Origin.DECLARED_PARAMETER,AT,AT,null);assertThrows(InvalidSimulationValueException.class,()->build("r1",Origin.DECLARED_PARAMETER,c,v));}
        assertEquals(Origin.DECLARED_PARAMETER,build("r1",Origin.DECLARED_PARAMETER,curve(Origin.DECLARED_PARAMETER,AT,AT,null),valve(Origin.DECLARED_PARAMETER,AT,AT,null)).origin());}
    @Test void timeBoundariesAndFutureRecordingAreIndependent(){for(var kind:Kind.values()){
        var c=curve(Origin.SYNTHETIC,AT,AT,kind==Kind.COMPRESSOR?AT.plusSeconds(60):null);var v=valve(Origin.SYNTHETIC,AT,AT,kind==Kind.VALVE?AT.plusSeconds(60):null);
        var x=build("r1",Origin.SYNTHETIC,c,v);assertTrue(x.effectiveAt(AT));assertFalse(x.effectiveAt(AT.minusNanos(1)));assertFalse(x.effectiveAt(AT.plusSeconds(60)));}
        assertThrows(InvalidSimulationValueException.class,()->build("r1",Origin.SYNTHETIC,curve(Origin.SYNTHETIC,AT.plusNanos(1),AT,null),valve(Origin.SYNTHETIC,AT,AT,null)));
        assertThrows(InvalidSimulationValueException.class,()->build("r1",Origin.SYNTHETIC,curve(Origin.SYNTHETIC,AT,AT,null),valve(Origin.SYNTHETIC,AT.plusNanos(1),AT,null)));}
    @Test void absentOriginAndInvalidIntervalsReject(){assertThrows(InvalidSimulationValueException.class,()->curve(null,AT,AT,null));assertThrows(InvalidSimulationValueException.class,()->valve(null,AT,AT,null));
        assertThrows(InvalidSimulationValueException.class,()->curve(Origin.SYNTHETIC,null,AT,null));assertThrows(InvalidSimulationValueException.class,()->valve(Origin.SYNTHETIC,AT,AT,AT));}
    @Test void malformedCoordinatesAndFractionsReject(){assertThrows(InvalidSimulationValueException.class,()->new SpeedLine(d("1"),List.of(new CompressorPoint(d("1"),d("1"),d("0.8")),new CompressorPoint(d("1"),d("1"),d("0.8")))));
        assertThrows(InvalidSimulationValueException.class,()->new OpeningLine(d("0.5"),List.of(new ValvePoint(d("1"),d("1")),new ValvePoint(d("0"),d("1")))));
        assertThrows(InvalidSimulationValueException.class,()->new CompressorPoint(d("1"),d("1"),d("1.01")));}
    @Test void missingLimitsUnusedCharacteristicsAndConfiguredRangesReject(){var v=fixture("r1");
        assertThrows(InvalidSimulationValueException.class,()->new SimulationEquipmentParameterRevision(v.sourceId(),v.revisionId(),AT,AT,null,v.origin(),v.evidenceReference(),v.networkSourceId(),v.networkRevisionId(),v.networkSha256(),v.fluidSourceId(),v.fluidRevisionId(),v.fluidSha256(),v.fluidQualificationId(),v.equipment(),v.compressorCurves(),v.valveCharacteristics(),List.of(),v.governanceBinding()));
        assertThrows(InvalidSimulationValueException.class,()->new Equipment("e","a","b",Kind.COMPRESSOR,"c","r",d("1"),"illegal","r",null));}
}
