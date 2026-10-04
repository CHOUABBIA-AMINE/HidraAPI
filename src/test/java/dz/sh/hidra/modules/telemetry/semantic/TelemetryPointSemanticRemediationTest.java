/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Telemetry Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.semantic
 *
 * @Description : Verifies HMR-005 TelemetryPoint semantic remediation.
 *
 */
package dz.sh.hidra.modules.telemetry.semantic;

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLifecycleStatus;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TelemetryPointSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void rejectsBlankFrenchName() {
        assertThatThrownBy(() -> new TelemetryPoint(
                "point-1", "device-1", "PT-1", null, " ", null,
                "point-type", "signal-type", null, null, null, null,
                null, null, null, TelemetryLifecycleStatus.PLANNED, NOW, NOW
        ))
                .isInstanceOf(InvalidTelemetryValueException.class)
                .hasMessageContaining("French name");
    }

    @Test
    void migrationDefinesCompatibilityMetadataAndReferenceConstraints() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_005__hmr_005_telemetry_telemetry_point.sql"
        ));

        assertThat(sql).contains("value_shape varchar(20)");
        assertThat(sql).contains("numeric_unit_exempt boolean NOT NULL DEFAULT false");
        assertThat(sql).contains("value_shape IN ('NUMERIC', 'TEXT', 'BOOLEAN')");
        assertThat(sql).contains("ON hidra_telemetry_point (device_id, code)");
        assertThat(sql).contains("REFERENCES hidra_telemetry_unit (id)");
    }
}
