/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryValueObjectTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Domain tests for telemetry value objects.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;

/**
 * Domain tests for telemetry value objects.
 */
class TelemetryValueObjectTest {

    @Test
    void shouldNormalizeTelemetryCodesAndRejectInvalidValues() {
        TelemetryCode code = TelemetryCode.of(" pt-001 ");

        assertEquals("PT-001", code.value());
        assertThrows(InvalidValueObjectException.class, () -> TelemetryCode.of("x"));
        assertThrows(InvalidValueObjectException.class, () -> TelemetryCode.of("bad code"));
    }

    @Test
    void shouldKeepUserFacingNamesMultilingualWithFrenchMandatory() {
        TelemetryLocalizedName name = TelemetryLocalizedName.of(" اسم عربي ", " Nom français ", " ");

        assertEquals("اسم عربي", name.nameAr());
        assertEquals("Nom français", name.nameFr());
        assertNull(name.nameEn());
        assertThrows(InvalidValueObjectException.class, () -> TelemetryLocalizedName.of("اسم", " ", "Name"));
    }

    @Test
    void shouldRequireExactlyOneReadingValueKind() {
        TelemetryReadingValue numeric = TelemetryReadingValue.numeric(BigDecimal.valueOf(42.25));
        TelemetryReadingValue text = TelemetryReadingValue.text(" OPEN ");

        assertTrue(numeric.isNumeric());
        assertEquals("OPEN", text.textValue());
        assertThrows(InvalidValueObjectException.class, () -> new TelemetryReadingValue(BigDecimal.ONE, "OPEN", null));
        assertThrows(InvalidValueObjectException.class, () -> new TelemetryReadingValue(null, " ", null));
    }

    @Test
    void shouldValidateSamplingPeriodBounds() {
        assertEquals(Integer.valueOf(60), TelemetrySamplingPeriodSeconds.of(60).value());
        assertThrows(InvalidValueObjectException.class, () -> TelemetrySamplingPeriodSeconds.of(0));
        assertThrows(InvalidValueObjectException.class, () -> TelemetrySamplingPeriodSeconds.of(86_401));
    }

    @Test
    void shouldRepresentBusinessTypesAsCatalogReferences() {
        TelemetryPointTypeReference pointType = TelemetryPointTypeReference.of("point-type-pressure", "pressure");

        assertEquals("point-type-pressure", pointType.id());
        assertEquals("PRESSURE", pointType.code().value());
        assertEquals("PRESSURE", pointType.name());
        assertTrue(pointType.is("pressure"));
    }

    @Test
    void shouldKeepTopologyReferenceNeutralAndValidated() {
        TopologyAssetReference reference = TopologyAssetReference.of(" pipeline ", " pipeline-001 ", " gpl-001 ", " Main line ");

        assertEquals("PIPELINE", reference.assetTypeCode().value());
        assertEquals("pipeline-001", reference.assetId());
        assertEquals("GPL-001", reference.assetCode().value());
        assertEquals("Main line", reference.assetNameSnapshot());
        assertThrows(InvalidValueObjectException.class, () -> TopologyAssetReference.of("PIPELINE", " ", "GPL-001", null));
    }
}
