/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationInputSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Immutable snapshot reference set used for a run.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.time.Instant;

    /**
     * Immutable snapshot reference set used for a run.
     *
         * @param id id
     * @param scenarioId scenarioId
     * @param topologySnapshotId topologySnapshotId
     * @param telemetrySnapshotReference telemetrySnapshotReference
     * @param planningSnapshotReference planningSnapshotReference
     * @param monitoringSnapshotReference monitoringSnapshotReference
     * @param integritySnapshotReference integritySnapshotReference
     * @param assetAvailabilitySnapshotReference assetAvailabilitySnapshotReference
     * @param capturedAt capturedAt
     * @param captureHash captureHash
     */
    public record SimulationInputSnapshot(
            String id,
        String scenarioId,
        String topologySnapshotId,
        String telemetrySnapshotReference,
        String planningSnapshotReference,
        String monitoringSnapshotReference,
        String integritySnapshotReference,
        String assetAvailabilitySnapshotReference,
        Instant capturedAt,
        String captureHash
    ) {

        public SimulationInputSnapshot {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("SimulationInputSnapshot id must not be blank.");
        }
        // HRA-051 required: scenarioId
        if (scenarioId == null || scenarioId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationInputSnapshot scenario id must not be blank.");
        }
        // HRA-051 required: topologySnapshotId
        if (topologySnapshotId == null || topologySnapshotId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationInputSnapshot topology snapshot id must not be blank.");
        }
        // HRA-051 required: capturedAt
        if (capturedAt == null) {
            throw new InvalidSimulationValueException("SimulationInputSnapshot captured at must not be null.");
        }
        // HRA-051 required: captureHash
        if (captureHash == null || captureHash.isBlank()) {
            throw new InvalidSimulationValueException("SimulationInputSnapshot capture hash must not be blank.");
        }

        id = normalize(id);
        scenarioId = normalize(scenarioId);
        topologySnapshotId = normalize(topologySnapshotId);
        telemetrySnapshotReference = normalize(telemetrySnapshotReference);
        planningSnapshotReference = normalize(planningSnapshotReference);
        monitoringSnapshotReference = normalize(monitoringSnapshotReference);
        integritySnapshotReference = normalize(integritySnapshotReference);
        assetAvailabilitySnapshotReference = normalize(assetAvailabilitySnapshotReference);
        captureHash = normalize(captureHash);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
