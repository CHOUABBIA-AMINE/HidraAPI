/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QueueSimulationRunCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.command
 *
 * @Description : Command to queue a simulation run.
 *
 */
package dz.sh.hidra.modules.simulation.application.command;

/**
 * Command to queue a simulation run.
 */
public record QueueSimulationRunCommand(
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
