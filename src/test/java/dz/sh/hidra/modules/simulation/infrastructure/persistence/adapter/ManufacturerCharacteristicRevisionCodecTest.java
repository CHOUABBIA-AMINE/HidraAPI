/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManufacturerCharacteristicRevisionCodecTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Proves canonical V1 bytes, source digest differences and strict invalid payload rejection.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision;
import dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision.*;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevisionTest.*;

public class ManufacturerCharacteristicRevisionCodecTest {
    private final ManufacturerCharacteristicRevisionCodec codec=new ManufacturerCharacteristicRevisionCodec();
    @Test void roundTripsAllFourKindsWithExactBytesAndCanonicalHeader(){
        for(Family family:List.<Family>of(compressor(),valve(),table(),limits())){
            var source=fixture("v1",Origin.SUPPLIER_DECLARED,family);
            byte[] bytes=codec.encode(source);
            assertEquals(source,codec.decode(bytes));assertArrayEquals(bytes,codec.encode(codec.decode(bytes)));
            int n=ByteBuffer.wrap(bytes).getInt();assertEquals(ManufacturerCharacteristicRevisionCodec.FORMAT,
                new String(bytes,4,n,StandardCharsets.UTF_8));
        }
    }
    @Test void exactScaleAndProvenanceChangeDigestAndRevisionIdentity(){
        var a=fixture("v1",Origin.SYNTHETIC,compressor());
        var b=fixture("v2",Origin.SYNTHETIC,compressor());
        assertNotEquals(codec.sha256(a),codec.sha256(b));
        assertEquals(codec.sha256(codec.encode(a)),codec.sha256(a));
        var scale=new ManufacturerCharacteristicRevision(a.sourceId(),a.revisionId(),a.recordedAt(),a.effectiveFrom(),
            a.effectiveUntil(),a.origin(),a.manufacturerPartyId(),a.manufacturerModelReference(),a.documentReference(),
            a.documentSha256(),new ReferenceConditions(d("180000.0"),d("298.15"),a.referenceConditions().methodReference()),null,a.family());
        assertNotEquals(codec.sha256(a),codec.sha256(scale));
        assertEquals(scale,codec.decode(codec.encode(scale)));
    }
    @Test void certificateEvidenceIsRoundTrippedWithoutCertificationPromotion(){
        var src=fixture("v1",Origin.SUPPLIER_DECLARED,table());
        var cert=new CertificateEvidence("OEM","OEM-CERT","b".repeat(64),src.sourceId(),src.revisionId(),
            src.documentSha256(),src.manufacturerPartyId(),src.manufacturerModelReference(),Kind.PARAMETER_TABLE,AT,null);
        var with=new ManufacturerCharacteristicRevision(src.sourceId(),src.revisionId(),src.recordedAt(),src.effectiveFrom(),
            src.effectiveUntil(),src.origin(),src.manufacturerPartyId(),src.manufacturerModelReference(),src.documentReference(),
            src.documentSha256(),src.referenceConditions(),cert,src.family());
        assertEquals(with,codec.decode(codec.encode(with)));
        assertEquals(Origin.SUPPLIER_DECLARED,codec.decode(codec.encode(with)).origin());
        assertNotEquals(codec.sha256(src),codec.sha256(with));
    }
    @Test void truncatedTrailingAndOversizedBytesFailClosed(){
        byte[] bytes=codec.encode(fixture("v1",Origin.SYNTHETIC,compressor()));
        for(int i=0;i<bytes.length;i++){
            final int end=i;
            assertThrows(InvalidSimulationValueException.class,()->codec.decode(Arrays.copyOf(bytes,end)));
        }
        assertThrows(InvalidSimulationValueException.class,()->codec.decode(Arrays.copyOf(bytes,bytes.length+1)));
        assertThrows(InvalidSimulationValueException.class,()->codec.decode(new byte[ManufacturerCharacteristicRevisionCodec.MAX_BYTES+1]));
        assertThrows(InvalidSimulationValueException.class,()->codec.decode(null));
    }
    @Test void invalidHeaderMarkersLengthsAndUtf8AreRejected(){
        byte[] bytes=codec.encode(fixture("v1",Origin.SUPPLIER_DECLARED,valve()));
        byte[] bad=bytes.clone();ByteBuffer.wrap(bad).putInt(Integer.MAX_VALUE);final byte[] hugeLength=bad;
        assertThrows(InvalidSimulationValueException.class,()->codec.decode(hugeLength));
        bad=bytes.clone();bad[4]=(byte)0xff;final byte[] utf=bad;
        assertThrows(InvalidSimulationValueException.class,()->codec.decode(utf));
        bad=bytes.clone();bad[4]='X';final byte[] format=bad;
        assertThrows(InvalidSimulationValueException.class,()->codec.decode(format));
        bad=bytes.clone();bad[3]=(byte)0xff;final byte[] negativeCount=bad;
        assertThrows(InvalidSimulationValueException.class,()->codec.decode(negativeCount));
    }
    @Test void formatAndSourceEnvelopeRefuseInconsistentData(){
        var src=fixture("v1",Origin.SYNTHETIC,limits());
        assertThrows(InvalidSimulationValueException.class,()->codec.encode(null));
        assertEquals(64,codec.sha256(src).length());
        assertThrows(InvalidSimulationValueException.class,()->codec.sha256(new byte[ManufacturerCharacteristicRevisionCodec.MAX_BYTES+1]));
        var earlier=fixture("v1",Origin.SUPPLIER_DECLARED,limits());
        assertNotEquals(codec.sha256(src),codec.sha256(earlier));
    }
}
