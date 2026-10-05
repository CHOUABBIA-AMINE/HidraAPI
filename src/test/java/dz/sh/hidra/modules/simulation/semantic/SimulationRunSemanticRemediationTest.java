/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRunSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Simulation Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.semantic
 *
 * @Description : Verifies HMR-046 queue eligibility, catalog families and completed-run immutability.
 *
 */
package dz.sh.hidra.modules.simulation.semantic;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationRunRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationRun;
import dz.sh.hidra.modules.simulation.domain.value.SimulationRunStatus;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter.JpaSimulationRunRepositoryAdapter;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.entity.SimulationRunJpaEntity;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationRunJpaRepository;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SimulationRunSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-05T00:00:00Z");

    @Test
    void exposesRequiredCatalogFamiliesThroughRunRepositoryBoundary() {
        SimulationRunJpaRepository repository = mock(SimulationRunJpaRepository.class);
        when(repository.isRunType("run-type-1")).thenReturn(true);
        when(repository.isSolverProfile("solver-1")).thenReturn(true);

        SimulationRunRepositoryPort adapter = new JpaSimulationRunRepositoryAdapter(repository);

        assertThat(adapter.isRunType(" run-type-1 ")).isTrue();
        assertThat(adapter.isSolverProfile(" solver-1 ")).isTrue();
        assertThat(adapter.isRunType(" ")).isFalse();
    }

    @Test
    void rejectsMutationOfPersistedCompletedRun() {
        SimulationRunJpaRepository repository = mock(SimulationRunJpaRepository.class);
        SimulationRun completed = run(SimulationRunStatus.COMPLETED, null);
        SimulationRunJpaEntity entity = dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper
                .toEntity(completed);
        when(repository.findById(completed.id())).thenReturn(Optional.of(entity));

        JpaSimulationRunRepositoryAdapter adapter = new JpaSimulationRunRepositoryAdapter(repository);
        SimulationRun changed = run(SimulationRunStatus.COMPLETED, "changed");

        assertThatThrownBy(() -> adapter.save(changed))
                .isInstanceOf(InvalidSimulationValueException.class)
                .hasMessageContaining("immutable");

        verify(repository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    private static SimulationRun run(SimulationRunStatus status, String failureReason) {
        return new SimulationRun(
                "run-1",
                "scenario-1",
                "model-version-1",
                "input-snapshot-1",
                "run-type-1",
                status,
                "actor-1",
                "Actor One",
                NOW,
                status == SimulationRunStatus.QUEUED ? null : NOW.plusSeconds(5),
                status == SimulationRunStatus.COMPLETED ? NOW.plusSeconds(10) : null,
                status == SimulationRunStatus.COMPLETED ? 5000L : null,
                "solver-1",
                "corr-1",
                failureReason,
                NOW
        );
    }
}
