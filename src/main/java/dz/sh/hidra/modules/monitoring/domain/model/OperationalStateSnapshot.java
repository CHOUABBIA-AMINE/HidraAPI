/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalStateSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.monitoring.domain.exception.InvalidMonitoringValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidMonitoringValueException("OperationalStateSnapshot id must not be blank.");
        }
        // HRA-051 required: operationalStateId
        if (operationalStateId == null || operationalStateId.isBlank()) {
            throw new InvalidMonitoringValueException("OperationalStateSnapshot operational state id must not be blank.");
        }
        // HRA-051 required: topologyAssetId
        if (topologyAssetId == null || topologyAssetId.isBlank()) {
            throw new InvalidMonitoringValueException("OperationalStateSnapshot topology asset id must not be blank.");
        }
        // HRA-051 required: stateValue
        if (stateValue == null) {
            throw new InvalidMonitoringValueException("OperationalStateSnapshot state value must not be null.");
        }
        // HRA-051 required: capturedAt
        if (capturedAt == null) {
            throw new InvalidMonitoringValueException("OperationalStateSnapshot captured at must not be null.");
        }

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
