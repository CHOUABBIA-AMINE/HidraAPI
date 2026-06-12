/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRunSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.dto
 *
 * @Description : Simulation run summary DTO.
 *
 */
package dz.sh.hidra.modules.simulation.application.dto;

import dz.sh.hidra.modules.simulation.domain.value.SimulationRunStatus;

import java.time.Instant;

/**
 * Simulation run summary DTO.
 */
public record SimulationRunSummaryDto(
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
