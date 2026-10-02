/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRunResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.api.rest.response
 *
 * @Description : REST response for simulation run.
 *
 */
package dz.sh.hidra.modules.simulation.api.rest.response;

import dz.sh.hidra.modules.simulation.domain.value.SimulationRunStatus;
import java.time.Instant;

/**
 * REST response for simulation run.
 */
public record SimulationRunResponse(
        String id,
        String scenarioId,
        String modelVersionId,
        String inputSnapshotId,
        String runTypeId,
        SimulationRunStatus status,
        String correlationId,
        Instant queuedAt,
        Instant completedAt
) {
}
