package dz.sh.hidra.modules.simulation.semantic;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationScenario;
import dz.sh.hidra.modules.simulation.domain.value.SimulationScenarioStatus;
import dz.sh.hidra.modules.simulation.infrastructure.integration.NoopSimulationExternalReferenceResolver;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimulationScenarioSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void requiresFrenchNameAndCreatorDisplaySnapshot() {
        assertThatThrownBy(() -> scenario(null, "Creator"))
                .isInstanceOf(InvalidSimulationValueException.class)
                .hasMessageContaining("French name");
        assertThatThrownBy(() -> scenario("Scenario", " "))
                .isInstanceOf(InvalidSimulationValueException.class)
                .hasMessageContaining("display name snapshot");
    }

    @Test
    void failClosedFallbackNeverClaimsOwnerReferencesAvailable() {
        var resolver = new NoopSimulationExternalReferenceResolver();
        assertThat(resolver.topologySnapshotAvailable("topology-1")).isFalse();
        assertThat(resolver.planningSnapshotAvailable("plan-1")).isFalse();
        assertThat(resolver.monitoringContextAvailable("monitoring-1")).isFalse();
        assertThat(resolver.available("any-reference")).isFalse();
    }

    @Test
    void migrationEnforcesCodeTypeAndModelVersionParentSemantics() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_034__hmr_034_simulation_simulation_scenario.sql"
        ));
        assertThat(sql)
                .contains("uk_hmr034_simulation_scenario_code")
                .contains("SIMULATION_SCENARIO_TYPE")
                .contains("FOREIGN KEY (model_version_id, model_id)")
                .contains("btrim(name_fr) <> ''")
                .contains("btrim(created_by_display_name_snapshot) <> ''");
    }

    private static SimulationScenario scenario(String nameFr, String creatorSnapshot) {
        return new SimulationScenario(
                "scenario-1", "SCENARIO-1", null, nameFr, null,
                "scenario-type-1", "model-1", "model-version-1", "topology-1",
                null, null, SimulationScenarioStatus.DRAFT, "actor-1",
                creatorSnapshot, NOW, NOW
        );
    }
}
