/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateSimulationScenarioCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.command
 *
 * @Description : Command to create a simulation scenario.
 *
 */
package dz.sh.hidra.modules.simulation.application.command;

/**
 * Command to create a simulation scenario.
 */
public record CreateSimulationScenarioCommand(
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
