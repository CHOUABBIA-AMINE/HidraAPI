/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRun
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Execution of a locked simulation scenario.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.value.*;
import java.time.Instant;

    /**
     * Execution of a locked simulation scenario.
     *
         * @param id id
     * @param scenarioId scenarioId
     * @param modelVersionId modelVersionId
     * @param inputSnapshotId inputSnapshotId
     * @param runTypeId runTypeId
     * @param status status
     * @param requestedByActorId requestedByActorId
     * @param requestedByDisplayNameSnapshot requestedByDisplayNameSnapshot
     * @param queuedAt queuedAt
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param durationMillis durationMillis
     * @param solverProfileId solverProfileId
     * @param correlationId correlationId
     * @param failureReason failureReason
     * @param createdAt createdAt
     */
    public record SimulationRun(
            String id,
        String scenarioId,
        String modelVersionId,
        String inputSnapshotId,
        String runTypeId,
        SimulationRunStatus status,
        String requestedByActorId,
        String requestedByDisplayNameSnapshot,
        Instant queuedAt,
        Instant startedAt,
        Instant completedAt,
        Long durationMillis,
        String solverProfileId,
        String correlationId,
        String failureReason,
        Instant createdAt
    ) {

        public SimulationRun {
        id = normalize(id);
        scenarioId = normalize(scenarioId);
        modelVersionId = normalize(modelVersionId);
        inputSnapshotId = normalize(inputSnapshotId);
        runTypeId = normalize(runTypeId);
        requestedByActorId = normalize(requestedByActorId);
        requestedByDisplayNameSnapshot = normalize(requestedByDisplayNameSnapshot);
        solverProfileId = normalize(solverProfileId);
        correlationId = normalize(correlationId);
        failureReason = normalize(failureReason);
        }
        public boolean terminalStatus() {
            return status == SimulationRunStatus.COMPLETED
                    || status == SimulationRunStatus.FAILED
                    || status == SimulationRunStatus.CANCELLED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
