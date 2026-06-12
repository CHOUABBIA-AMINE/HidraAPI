/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationInputSnapshot
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
