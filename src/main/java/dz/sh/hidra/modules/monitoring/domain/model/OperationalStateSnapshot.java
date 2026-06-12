/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalStateSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.model
 *
 * @Description : Historical operational state snapshot.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.model;

import dz.sh.hidra.modules.monitoring.domain.value.*;
import java.time.Instant;

    /**
     * Historical operational state snapshot.
     *
         * @param id id
     * @param operationalStateId operationalStateId
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param telemetryPointId telemetryPointId
     * @param stateValue stateValue
     * @param severity severity
     * @param snapshotPayload snapshotPayload
     * @param capturedAt capturedAt
     * @param correlationId correlationId
     */
    public record OperationalStateSnapshot(
            String id,
        String operationalStateId,
        String topologyAssetType,
        String topologyAssetId,
        String telemetryPointId,
        OperationalStateValue stateValue,
        DeviationSeverity severity,
        String snapshotPayload,
        Instant capturedAt,
        String correlationId
    ) {

        public OperationalStateSnapshot {
        id = normalize(id);
        operationalStateId = normalize(operationalStateId);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        telemetryPointId = normalize(telemetryPointId);
        snapshotPayload = normalize(snapshotPayload);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
