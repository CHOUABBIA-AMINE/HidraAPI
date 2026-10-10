/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentParameterRevisionCodecTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Maintains immutable governed equipment source evidence.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevisionTest;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision.*;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.util.*;
import java.nio.*;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevisionTest.*;
class SimulationEquipmentParameterRevisionCodecTest {
    final SimulationEquipmentParameterRevisionCodec codec=new SimulationEquipmentParameterRevisionCodec();
    @Test void completeAggregateAndBothCharacteristicsRoundTrip(){var v=fixture("r1");assertEquals(v,codec.decode(codec.encode(v)));
        assertEquals(v.compressorCurves().getFirst(),codec.decodeCompressorCurve(codec.encodeCompressorCurve(v.compressorCurves().getFirst())));
        assertEquals(v.valveCharacteristics().getFirst(),codec.decodeValveCharacteristic(codec.encodeValveCharacteristic(v.valveCharacteristics().getFirst())));}
    @Test void independentCanonicalPrefixAndProvenanceDigestSensitivity(){var c=curve(Origin.SYNTHETIC,AT,AT,null);byte[] b=codec.encodeCompressorCurve(c);var in=ByteBuffer.wrap(b);
        byte[] magic=new byte[in.getInt()];in.get(magic);assertEquals("HIDRA_SIMULATION_COMPRESSOR_CURVE_V1",new String(magic,StandardCharsets.UTF_8));
        assertNotEquals(codec.sha256(b),codec.sha256(codec.encodeCompressorCurve(curve(Origin.DECLARED_PARAMETER,AT,AT,null))));
        assertNotEquals(codec.sha256(b),codec.sha256(codec.encodeCompressorCurve(curve(Origin.SYNTHETIC,AT,AT.plusNanos(1),null))));
        assertNotEquals(codec.sha256(codec.encodeValveCharacteristic(valve(Origin.SYNTHETIC,AT,AT,null))),codec.sha256(codec.encodeValveCharacteristic(valve(Origin.SYNTHETIC,AT,AT,AT.plusSeconds(1)))));}
    @Test void strictTruncatedTrailingMalformedUtf8AndLengthReject(){byte[] bytes=codec.encode(fixture("r1"));for(int i=0;i<bytes.length;i++){byte[] cut=Arrays.copyOf(bytes,i);assertThrows(InvalidSimulationValueException.class,()->codec.decode(cut));}
        assertThrows(InvalidSimulationValueException.class,()->codec.decode(Arrays.copyOf(bytes,bytes.length+1)));
        byte[] bad=bytes.clone();ByteBuffer.wrap(bad).putInt(Integer.MAX_VALUE);assertThrows(InvalidSimulationValueException.class,()->codec.decode(bad));byte[] utf=bytes.clone();utf[4]=(byte)0xff;assertThrows(InvalidSimulationValueException.class,()->codec.decode(utf));}
    @Test void independentCurveDigestFixtureAndNegativeScaleZeroArePreserved(){
        assertEquals("c06a3e17d5f04e572ef169fdcb204d38cc52edbe7ed2160152b2b6d6d4309fa6",codec.sha256(codec.encodeCompressorCurve(curve(Origin.SYNTHETIC,AT,AT,null))));
        var z=valve(Origin.SYNTHETIC,AT,AT,null);var zero=new java.math.BigDecimal(java.math.BigInteger.ZERO,Integer.MIN_VALUE);
        var line=new OpeningLine(z.openingLines().getFirst().openingFraction(),List.of(new ValvePoint(zero,zero),new ValvePoint(d("1000.00"),zero)));
        var v=new ValveCharacteristic(z.id(),z.revisionId(),z.recordedAt(),z.effectiveFrom(),z.effectiveUntil(),z.origin(),z.evidenceReference(),z.fluidSourceId(),z.fluidRevisionId(),z.fluidSha256(),new java.math.BigDecimal("3E+2"),List.of(line,z.openingLines().getLast()));
        var decoded=codec.decodeValveCharacteristic(codec.encodeValveCharacteristic(v));assertEquals(v,decoded);assertEquals(Integer.MIN_VALUE,decoded.openingLines().getFirst().points().getFirst().differentialPressurePascals().scale());
        byte[] bytes=codec.encodeCompressorCurve(curve(Origin.SYNTHETIC,AT,AT,null));byte[] text="200000.00".getBytes(StandardCharsets.UTF_8);
        int found=-1;for(int n=0;n<=bytes.length-text.length;n++)if(Arrays.equals(Arrays.copyOfRange(bytes,n,n+text.length),text)){found=n;break;}
        assertTrue(found>0);ByteBuffer.wrap(bytes,found+text.length,4).putInt(Integer.MIN_VALUE);assertThrows(InvalidSimulationValueException.class,()->codec.decodeCompressorCurve(bytes));
    }
}
