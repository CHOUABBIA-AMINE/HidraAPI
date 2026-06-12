/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationModelResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.api.rest.response
 *
 * @Description : REST response for simulation model.
 *
 */
package dz.sh.hidra.modules.simulation.api.rest.response;

import dz.sh.hidra.modules.simulation.domain.value.SimulationModelStatus;

import java.time.Instant;

/**
 * REST response for simulation model.
 */
public record SimulationModelResponse(
        String id,
        String code,
        String nameFr,
        String modelTypeId,
        String topologyScopeType,
        SimulationModelStatus status,
        Instant createdAt
) {
}
