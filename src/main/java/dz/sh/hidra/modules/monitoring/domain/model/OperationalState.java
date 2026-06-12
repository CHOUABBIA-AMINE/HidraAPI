/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalState
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.model
 *
 * @Description : Current interpreted operational state.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.model;

import dz.sh.hidra.modules.monitoring.domain.value.*;
import java.time.Instant;

    /**
     * Current interpreted operational state.
     *
         * @param id id
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCode topologyAssetCode
     * @param telemetryPointId telemetryPointId
     * @param stateValue stateValue
     * @param severity severity
     * @param reasonCode reasonCode
     * @param reasonMessage reasonMessage
     * @param lastTrustedReadingId lastTrustedReadingId
     * @param lastPlanTargetId lastPlanTargetId
     * @param stateAt stateAt
     * @param updatedAt updatedAt
     */
    public record OperationalState(
            String id,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        String telemetryPointId,
        OperationalStateValue stateValue,
        DeviationSeverity severity,
        String reasonCode,
        String reasonMessage,
        String lastTrustedReadingId,
        String lastPlanTargetId,
        Instant stateAt,
        Instant updatedAt
    ) {

        public OperationalState {
        id = normalize(id);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCode = normalize(topologyAssetCode);
        telemetryPointId = normalize(telemetryPointId);
        reasonCode = normalize(reasonCode);
        reasonMessage = normalize(reasonMessage);
        lastTrustedReadingId = normalize(lastTrustedReadingId);
        lastPlanTargetId = normalize(lastPlanTargetId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
