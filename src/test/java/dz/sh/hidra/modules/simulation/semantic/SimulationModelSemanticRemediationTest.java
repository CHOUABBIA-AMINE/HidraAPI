/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationModelSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Simulation Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.semantic
 *
 * @Description : Verifies HMR-009 SimulationModel semantic remediation.
 *
 */
package dz.sh.hidra.modules.simulation.semantic;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationModel;
import dz.sh.hidra.modules.simulation.domain.value.SimulationModelStatus;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimulationModelSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void rejectsBlankFrenchName() {
        assertThatThrownBy(() -> model(" ", "PIPELINE", NOW, NOW))
                .isInstanceOf(InvalidSimulationValueException.class)
                .hasMessageContaining("French name");
    }

    @Test
    void rejectsUnsupportedScopeType() {
        assertThatThrownBy(() -> model("Modèle", "FACILITY", NOW, NOW))
                .isInstanceOf(InvalidSimulationValueException.class)
                .hasMessageContaining("Unsupported");
    }

    @Test
    void rejectsMissingTimestamps() {
        assertThatThrownBy(() -> model("Modèle", "PIPELINE", null, NOW))
                .isInstanceOf(InvalidSimulationValueException.class)
                .hasMessageContaining("createdAt");
    }

    @Test
    void migrationEnforcesCodeNameAndScopeRules() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_009__hmr_009_simulation_simulation_model.sql"
        ));

        assertThat(sql).contains("CREATE UNIQUE INDEX uk_hmr009_simulation_model_code");
        assertThat(sql).contains("btrim(name_fr) <> ''");
        assertThat(sql).contains("'SEGMENT_GROUP'");
        assertThat(sql).contains("'FACILITY_NETWORK'");
    }

    private static SimulationModel model(
            String nameFr,
            String scopeType,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new SimulationModel(
                "model-1", "MODEL-1", null, nameFr, null,
                "model-type-1", scopeType, null,
                SimulationModelStatus.DRAFT, null, createdAt, updatedAt
        );
    }
}
