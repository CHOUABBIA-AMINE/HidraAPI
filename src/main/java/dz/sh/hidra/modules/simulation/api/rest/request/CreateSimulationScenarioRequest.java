/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateSimulationScenarioRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.api.rest.request
 *
 * @Description : REST request to create simulation scenario.
 *
 */
package dz.sh.hidra.modules.simulation.api.rest.request;

/**
 * REST request to create simulation scenario.
 */
public record CreateSimulationScenarioRequest(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String scenarioTypeId,
        String modelId,
        String modelVersionId,
        String topologySnapshotId,
        String planningReferenceId,
        String monitoringContextId,
        String createdByActorId,
        String createdByDisplayNameSnapshot
) {
}
