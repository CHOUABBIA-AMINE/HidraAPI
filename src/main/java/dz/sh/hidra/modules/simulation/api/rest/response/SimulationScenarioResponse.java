/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationScenarioResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.api.rest.response
 *
 * @Description : REST response for simulation scenario.
 *
 */
package dz.sh.hidra.modules.simulation.api.rest.response;

import dz.sh.hidra.modules.simulation.domain.value.SimulationScenarioStatus;

import java.time.Instant;

/**
 * REST response for simulation scenario.
 */
public record SimulationScenarioResponse(
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
