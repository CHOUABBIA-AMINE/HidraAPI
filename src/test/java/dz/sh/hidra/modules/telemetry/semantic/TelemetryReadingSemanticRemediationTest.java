/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Telemetry Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.semantic
 *
 * @Description : Verifies HMR-033 raw-reading reference, family, and value-shape semantics.
 *
 */
package dz.sh.hidra.modules.telemetry.semantic;

import dz.sh.hidra.modules.telemetry.domain.exception.TelemetryReadingValidationException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.value.ReadingState;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TelemetryReadingSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void acceptedReadingRequiresExactlyOneTypedValue() {
        assertThatThrownBy(() -> reading(
                ReadingState.ACCEPTED,
                null,
                null,
                null
        )).isInstanceOf(TelemetryReadingValidationException.class);

        assertThat(reading(
                ReadingState.ACCEPTED,
                BigDecimal.ONE,
                null,
                null
        ).hasExactlyOneValue()).isTrue();
    }

    @Test
    void rejectedAndQuarantinedReadingsMayPreserveNullValueEvidence() {
        assertThat(reading(
                ReadingState.REJECTED,
                null,
                null,
                null
        ).hasExactlyOneValue()).isFalse();

        assertThat(reading(
                ReadingState.QUARANTINED,
                null,
                null,
                null
        ).hasExactlyOneValue()).isFalse();
    }

    @Test
    void everyReadingRejectsMultipleTypedValues() {
        assertThatThrownBy(() -> reading(
                ReadingState.REJECTED,
                BigDecimal.ONE,
                "one",
                null
        )).isInstanceOf(TelemetryReadingValidationException.class);
    }

    @Test
    void migrationProtectsReferencesQualityFamilyAndValueShape() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_033__hmr_033_telemetry_telemetry_reading.sql"
        ));

        assertThat(sql)
                .contains("REFERENCES hidra_telemetry_ingestion_batch (id)")
                .contains("REFERENCES hidra_telemetry_external_tag_mapping (id)")
                .contains("quality.catalog_name = 'QUALITY_CODE'")
                .contains("ck_hmr033_reading_value_shape")
                .contains("state IN ('REJECTED', 'QUARANTINED')");
    }

    private static TelemetryReading reading(
            ReadingState state,
            BigDecimal numericValue,
            String textValue,
            Boolean booleanValue
    ) {
        return new TelemetryReading(
                "reading-1",
                "point-1",
                numericValue,
                textValue,
                booleanValue,
                "quality-good",
                NOW,
                NOW,
                state,
                null,
                null,
                null,
                null,
                null,
                null,
                NOW
        );
    }
}
