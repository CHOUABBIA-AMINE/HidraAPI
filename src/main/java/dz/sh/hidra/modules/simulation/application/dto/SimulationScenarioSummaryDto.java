/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationScenarioSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.dto
 *
 * @Description : Simulation scenario summary DTO.
 *
 */
package dz.sh.hidra.modules.simulation.application.dto;

import dz.sh.hidra.modules.simulation.domain.value.SimulationScenarioStatus;

import java.time.Instant;

/**
 * Simulation scenario summary DTO.
 */
public record SimulationScenarioSummaryDto(
        String id,
        String code,
        String nameFr,
        String scenarioTypeId,
        String modelId,
        String modelVersionId,
        String topologySnapshotId,
        SimulationScenarioStatus status,
        Instant createdAt
) {
}
