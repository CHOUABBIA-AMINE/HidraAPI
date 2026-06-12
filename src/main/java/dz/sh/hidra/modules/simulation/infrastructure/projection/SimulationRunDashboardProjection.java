/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRunDashboardProjection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.projection
 *
 * @Description : Simulation run dashboard projection.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.projection;

import dz.sh.hidra.modules.simulation.domain.value.SimulationRunStatus;

import java.time.Instant;

/**
 * Simulation run dashboard projection.
 */
public record SimulationRunDashboardProjection(
        String runId,
        String scenarioId,
        String modelVersionId,
        String runTypeId,
        SimulationRunStatus status,
        boolean feasible,
        int constraintViolationCount,
        Instant queuedAt,
        Instant completedAt
) {
}
