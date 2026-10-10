/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyGasFluidRevisionCodecTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Verifies strict gas bytes, independent format/hash fixtures and scale attack rejection.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision.*;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevisionTest;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HexFormat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustodyGasFluidRevisionCodecTest {
    private final CustodyGasFluidRevisionCodec codec = new CustodyGasFluidRevisionCodec();
    @Test void roundTripsCompleteFactsAndKnownIndependentHash() {
        var v = CustodyGasFluidRevisionTest.fixture("r1"); byte[] bytes = codec.encode(v);
        assertEquals(v, codec.decode(bytes)); assertArrayEquals(bytes, codec.encode(codec.decode(bytes)));
        assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", codec.sha256(new byte[0]));
        assertNotEquals(codec.sha256(bytes), codec.sha256(codec.encode(CustodyGasFluidRevisionTest.fixture("r2"))));
        assertEquals(v.method(), codec.decodeMethod(codec.encodeMethod(v.method())));
        assertEquals("4b8d2fa62ceb2f3ced43e1194ba31acfb1e5403601e41218e9d01ae310978a28", codec.sha256(codec.encodeMethod(v.method())));
        // Independently inspect big-endian magic and source identity, rather than trusting decode alone.
        assertEquals(26, java.nio.ByteBuffer.wrap(bytes).getInt());
        assertEquals(CustodyGasFluidRevisionCodec.FORMAT, new String(bytes, 4, 26, StandardCharsets.UTF_8));
    }
    @Test void rejectsEveryTruncationTrailingUnknownMagicAndInvalidUtf8() {
        byte[] bytes = codec.encode(CustodyGasFluidRevisionTest.fixture("r1"));
        for (int i = 0; i < bytes.length; i++) {
            byte[] truncated = Arrays.copyOf(bytes, i); assertThrows(InvalidCustodyValueException.class, () -> codec.decode(truncated));
        }
        assertThrows(InvalidCustodyValueException.class, () -> codec.decode(Arrays.copyOf(bytes, bytes.length + 1)));
        byte[] unknown = bytes.clone(); unknown[4] = 'X'; assertThrows(InvalidCustodyValueException.class, () -> codec.decode(unknown));
        byte[] malformed = bytes.clone(); malformed[4] = (byte) 0xff; assertThrows(InvalidCustodyValueException.class, () -> codec.decode(malformed));
    }
    @Test void retainsNegativeScaleAndRejectsAllocationExpandingScale() {
        var v = CustodyGasFluidRevisionTest.fixture("r1"); var m = v.method();
        var negative = new Method(m.reference(), m.revisionId(), m.recordedAt(), m.effectiveFrom(), null, m.origin(), m.evidenceReference(),
                m.inputRepresentation(), m.allowedComponentReferences(), new BigDecimal("1E+5"), new BigDecimal("9E+5"),
                m.minimumTemperatureKelvin(), m.maximumTemperatureKelvin(), m.supportedUses());
        assertEquals(-5, codec.decodeMethod(codec.encodeMethod(negative)).minimumPressurePascalsAbsolute().scale());
        byte[] bytes = codec.encodeMethod(m);
        byte[] needle = "100000.00".getBytes(StandardCharsets.UTF_8);
        int position = indexOf(bytes, needle); assertTrue(position > 0);
        java.nio.ByteBuffer.wrap(bytes).putInt(position + needle.length, Integer.MAX_VALUE);
        assertThrows(InvalidCustodyValueException.class, () -> codec.decodeMethod(bytes));
        java.nio.ByteBuffer.wrap(bytes).putInt(position + needle.length, Integer.MIN_VALUE);
        assertThrows(InvalidCustodyValueException.class, () -> codec.decodeMethod(bytes));
    }
    private int indexOf(byte[] bytes, byte[] needle) {
        for (int i = 0; i <= bytes.length - needle.length; i++) if (Arrays.equals(Arrays.copyOfRange(bytes, i, i + needle.length), needle)) return i;
        return -1;
    }
}
