/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRun
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("SimulationRun id must not be blank.");
        }
        // HRA-051 required: scenarioId
        if (scenarioId == null || scenarioId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationRun scenario id must not be blank.");
        }
        // HRA-051 required: modelVersionId
        if (modelVersionId == null || modelVersionId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationRun model version id must not be blank.");
        }
        // HRA-051 required: inputSnapshotId
        if (inputSnapshotId == null || inputSnapshotId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationRun input snapshot id must not be blank.");
        }
        // HRA-051 required: runTypeId
        if (runTypeId == null || runTypeId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationRun run type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidSimulationValueException("SimulationRun status must not be null.");
        }
        // HRA-051 required: requestedByActorId
        if (requestedByActorId == null || requestedByActorId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationRun requested by actor id must not be blank.");
        }
        // HRA-051 required: queuedAt
        if (queuedAt == null) {
            throw new InvalidSimulationValueException("SimulationRun queued at must not be null.");
        }
        // HRA-051 required: solverProfileId
        if (solverProfileId == null || solverProfileId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationRun solver profile id must not be blank.");
        }

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
