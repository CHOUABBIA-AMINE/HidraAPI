/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManufacturerCharacteristicRevisionTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Proves independent immutable supplier facts and negative documentary assertions.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ManufacturerCharacteristicRevisionTest {
    public static final Instant AT=Instant.parse("2026-10-01T10:00:00Z");
    public static BigDecimal d(String s){return new BigDecimal(s);}
    public static ManufacturerCharacteristicRevision fixture(String revision,Origin origin,Family family){
        return new ManufacturerCharacteristicRevision("m-source",revision,AT,AT,null,origin,
            "party-1","model-x","supplier-catalogue-v3","a".repeat(64),
            new ReferenceConditions(d("180000.00"),d("298.15"),"documented supplier reference"),null,family);
    }
    public static CompressorMap compressor(){return new CompressorMap(List.of(
        new SpeedLine(d("1000.00"),List.of(new CompressorPoint(d("1.00"),d("400.00"),d("0.80")),new CompressorPoint(d("2.00"),d("350.00"),d("0.77")))),
        new SpeedLine(d("2000.00"),List.of(new CompressorPoint(d("1.00"),d("700.00"),d("0.82")),new CompressorPoint(d("2.00"),d("650.00"),d("0.78"))))));}
    public static ValveMap valve(){return new ValveMap(List.of(new OpeningLine(d("0.00"),List.of(
        new ValvePoint(d("0.00"),d("0.00")),new ValvePoint(d("1000.00"),d("0.00")))),
        new OpeningLine(d("1.00"),List.of(new ValvePoint(d("0.00"),d("0.00")),new ValvePoint(d("1000.00"),d("2.00"))))));}
    public static ParameterTable table(){return new ParameterTable(
        List.of(new Axis(Quantity.ROTATIONAL_SPEED,Unit.REVOLUTION_PER_MINUTE)),
        List.of(new Axis(Quantity.MASS_FLOW,Unit.KILOGRAM_PER_SECOND)),
        List.of(new TableRow(List.of(d("1000.00")),List.of(d("1.00"))),
                new TableRow(List.of(d("2000.00")),List.of(d("2.00")))));}
    public static OperatingLimits limits(){return new OperatingLimits(List.of(
        new Limit(new Axis(Quantity.MASS_FLOW,Unit.KILOGRAM_PER_SECOND),d("0.00"),d("2.00"),"supplier bounds"),
        new Limit(new Axis(Quantity.ABSOLUTE_PRESSURE,Unit.PASCAL_ABSOLUTE),d("100000.00"),d("900000.00"),"supplier rating")));}
    @Test void acceptsFourSuppliedFamiliesAndIndependentRevisions(){
        var families=List.<Family>of(compressor(),valve(),table(),limits());
        assertEquals(4,families.stream().map(Family::kind).distinct().count());
        for(Family family:families){var a=fixture("v1",Origin.SUPPLIER_DECLARED,family);
            var b=fixture("v2",Origin.SUPPLIER_DECLARED,family);
            assertNotEquals(a,b);assertNull(a.certificate());assertTrue(a.effectiveAt(AT));
            assertFalse(a.effectiveAt(AT.minusNanos(1)));}
    }
    @Test void refusesMissingIdentityEvidenceAndReferenceConditions(){
        var source=fixture("v1",Origin.SUPPLIER_DECLARED,compressor());
        assertThrows(InvalidSimulationValueException.class,()->new ManufacturerCharacteristicRevision(" ","v1",AT,AT,null,source.origin(),
            source.manufacturerPartyId(),source.manufacturerModelReference(),source.documentReference(),source.documentSha256(),source.referenceConditions(),null,source.family()));
        assertThrows(InvalidSimulationValueException.class,()->new ManufacturerCharacteristicRevision("m-source","v1",AT,AT,null,source.origin(),
            source.manufacturerPartyId(),source.manufacturerModelReference(),source.documentReference(),"BAD",source.referenceConditions(),null,source.family()));
        assertThrows(InvalidSimulationValueException.class,()->new ManufacturerCharacteristicRevision("m-source","v1",AT,AT,null,source.origin(),
            source.manufacturerPartyId(),source.manufacturerModelReference(),source.documentReference(),source.documentSha256(),null,null,source.family()));
        assertThrows(InvalidSimulationValueException.class,()->new ReferenceConditions(d("0"),d("298.15"),"method"));
    }
    @Test void certificateIsOnlySourceMetadataAndCannotCoverOtherRevisionOrSynthetic(){
        var original=fixture("v1",Origin.SUPPLIER_DECLARED,compressor());
        var certificate=new CertificateEvidence("OEM-certificate-issuer","OEM-2026-17","b".repeat(64),
            original.sourceId(),original.revisionId(),original.documentSha256(),
            original.manufacturerPartyId(),original.manufacturerModelReference(),Kind.COMPRESSOR_MAP,AT,null);
        var with=new ManufacturerCharacteristicRevision(original.sourceId(),original.revisionId(),AT,AT,null,
            original.origin(),original.manufacturerPartyId(),original.manufacturerModelReference(),
            original.documentReference(),original.documentSha256(),original.referenceConditions(),certificate,original.family());
        assertEquals(Origin.SUPPLIER_DECLARED,with.origin());assertEquals(certificate,with.certificate());
        assertThrows(InvalidSimulationValueException.class,()->new ManufacturerCharacteristicRevision(original.sourceId(),"different",AT,AT,null,
            original.origin(),original.manufacturerPartyId(),original.manufacturerModelReference(),
            original.documentReference(),original.documentSha256(),original.referenceConditions(),certificate,original.family()));
        assertThrows(InvalidSimulationValueException.class,()->new ManufacturerCharacteristicRevision(original.sourceId(),original.revisionId(),AT,AT,null,
            Origin.SYNTHETIC,original.manufacturerPartyId(),original.manufacturerModelReference(),
            original.documentReference(),original.documentSha256(),original.referenceConditions(),certificate,original.family()));
        assertThrows(InvalidSimulationValueException.class,()->new CertificateEvidence("issuer","ref","b".repeat(64),
            "m-source","v1","a".repeat(64),"party-1","model-x",Kind.COMPRESSOR_MAP,AT,AT));
    }
    @Test void rejectsUnknownUnitsDuplicateTableColumnsAndRows(){
        assertThrows(InvalidSimulationValueException.class,()->new Axis(Quantity.TEMPERATURE,Unit.PASCAL_ABSOLUTE));
        var speed=new Axis(Quantity.ROTATIONAL_SPEED,Unit.REVOLUTION_PER_MINUTE);
        var flow=new Axis(Quantity.MASS_FLOW,Unit.KILOGRAM_PER_SECOND);
        assertThrows(InvalidSimulationValueException.class,()->new ParameterTable(List.of(speed),List.of(speed),
            List.of(new TableRow(List.of(d("1000")),List.of(d("1"))))));
        assertThrows(InvalidSimulationValueException.class,()->new ParameterTable(List.of(speed),List.of(flow),
            List.of(new TableRow(List.of(d("1000")),List.of(d("1"))),new TableRow(List.of(d("1000.00")),List.of(d("2"))))));
        assertThrows(InvalidSimulationValueException.class,()->new ParameterTable(List.of(speed),List.of(flow),
            List.of(new TableRow(List.of(d("1000"),d("2000")),List.of(d("1"))))));
    }
    @Test void rejectsUnorderedCompressorAndValveSamplesAndInvalidFractions(){
        assertThrows(InvalidSimulationValueException.class,()->new SpeedLine(d("1000"),List.of(
            new CompressorPoint(d("1"),d("2"),d("0.8")),new CompressorPoint(d("1.00"),d("3"),d("0.9")))));
        assertThrows(InvalidSimulationValueException.class,()->new OpeningLine(d("0.5"),List.of(
            new ValvePoint(d("100"),d("2")),new ValvePoint(d("0"),d("0")))));
        assertThrows(InvalidSimulationValueException.class,()->new CompressorPoint(d("1"),d("2"),d("0")));
        assertThrows(InvalidSimulationValueException.class,()->new ValvePoint(d("-1"),d("1")));
        assertThrows(InvalidSimulationValueException.class,()->new OperatingLimits(List.of(
            new Limit(new Axis(Quantity.ABSOLUTE_PRESSURE,Unit.PASCAL_ABSOLUTE),d("10"),d("9"),"supplier"))));
    }
    @Test void sourceTemporalAndTextBoundsAreExplicit(){
        var a=fixture("v1",Origin.SYNTHETIC,table());
        assertThrows(InvalidSimulationValueException.class,()->new ManufacturerCharacteristicRevision(a.sourceId(),a.revisionId(),AT,AT,AT,
            a.origin(),a.manufacturerPartyId(),a.manufacturerModelReference(),a.documentReference(),a.documentSha256(),a.referenceConditions(),null,a.family()));
        assertThrows(InvalidSimulationValueException.class,()->new ManufacturerCharacteristicRevision("e\u0301",a.revisionId(),AT,AT,null,
            a.origin(),a.manufacturerPartyId(),a.manufacturerModelReference(),a.documentReference(),a.documentSha256(),a.referenceConditions(),null,a.family()));
        assertThrows(InvalidSimulationValueException.class,()->new ManufacturerCharacteristicRevision("x".repeat(81),a.revisionId(),AT,AT,null,
            a.origin(),a.manufacturerPartyId(),a.manufacturerModelReference(),a.documentReference(),a.documentSha256(),a.referenceConditions(),null,a.family()));
        assertThrows(InvalidSimulationValueException.class,()->new CompressorPoint(d("1"),d("2"),d("1.0000000000000")));
        assertFalse(a.effectiveAt(null));
    }
    @Test void nestedCollectionsAreDefensivelyImmutable(){
        var rows=new ArrayList<TableRow>();rows.add(new TableRow(List.of(d("1000")),List.of(d("1"))));
        var t=new ParameterTable(List.of(new Axis(Quantity.ROTATIONAL_SPEED,Unit.REVOLUTION_PER_MINUTE)),
            List.of(new Axis(Quantity.MASS_FLOW,Unit.KILOGRAM_PER_SECOND)),rows);
        rows.clear();assertEquals(1,t.rows().size());
        assertThrows(UnsupportedOperationException.class,()->t.rows().clear());
        assertThrows(UnsupportedOperationException.class,()->t.rows().getFirst().coordinates().clear());
    }
}
