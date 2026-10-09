/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationTrustedReadingEvidencePortTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Application Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 *
 * @Description : Verifies immutable raw evidence identity and replacement semantics.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.out;

import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTrustedReadingEvidencePortTest {

    @Test
    void equalEvidenceHasStableValueIdentityWithoutNormalizingRawFields() {
        var original = fixture(new BigDecimal("0.000"), "  raw text  ");
        var copy = fixture(new BigDecimal("0.000"), "  raw text  ");
        assertEquals(original, copy);
        assertEquals(original.hashCode(), copy.hashCode());
        assertEquals("  raw text  ", original.textValue());
        assertEquals("  raw-unit  ", original.unitId());
        assertEquals(new BigDecimal("0.000"), original.numericValue());
    }

    @Test
    void replacementLeavesOriginalEvidenceAndMissingFieldsIntact() {
        var original = fixture(null, null);
        var replacement = fixture(BigDecimal.TEN, "replacement");
        assertNull(original.numericValue());
        assertNull(original.textValue());
        assertNull(original.topologySnapshotId());
        assertEquals(BigDecimal.TEN, replacement.numericValue());
        assertNotEquals(original, replacement);
    }

    private static SimulationTrustedReadingEvidencePort.ReadingEvidence fixture(BigDecimal value, String text) {
        return new SimulationTrustedReadingEvidencePort.ReadingEvidence(
                "evidence-1", "original-reading", "point-2", value, text, null, "  raw-unit  ",
                "quality-code", "UNTRUSTED", Instant.parse("2026-10-01T10:00:00Z"),
                Instant.parse("2026-10-01T10:00:07Z"), "assessment-3", null, null, null, null, null);
    }
}
