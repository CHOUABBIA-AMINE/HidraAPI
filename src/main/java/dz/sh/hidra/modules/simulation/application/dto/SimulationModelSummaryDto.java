/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationModelSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.dto
 *
 * @Description : Simulation model summary DTO.
 *
 */
package dz.sh.hidra.modules.simulation.application.dto;

import dz.sh.hidra.modules.simulation.domain.value.SimulationModelStatus;

import java.time.Instant;

/**
 * Simulation model summary DTO.
 */
public record SimulationModelSummaryDto(
        String id,
        String code,
        String nameFr,
        String modelTypeId,
        String topologyScopeType,
        SimulationModelStatus status,
        Instant createdAt
) {
}
