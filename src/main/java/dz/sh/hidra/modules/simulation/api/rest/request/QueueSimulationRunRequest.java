/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QueueSimulationRunRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.api.rest.request
 *
 * @Description : REST request for queue simulation run.
 *
 */
package dz.sh.hidra.modules.simulation.api.rest.request;

/**
 * REST request for queue simulation run.
 */
public record QueueSimulationRunRequest(
        String scenarioId,
        String modelVersionId,
        String inputSnapshotId,
        String runTypeId,
        String requestedByActorId,
        String requestedByDisplayNameSnapshot,
        String solverProfileId,
        String correlationId
) {
}
