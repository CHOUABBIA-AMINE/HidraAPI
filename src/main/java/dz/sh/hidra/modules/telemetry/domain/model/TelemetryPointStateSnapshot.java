/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointStateSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Latest state/cache for a telemetry point.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import dz.sh.hidra.modules.telemetry.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Latest state/cache for a telemetry point.
     *
         * @param pointId pointId
     * @param lastReadingId lastReadingId
     * @param lastTrustedReadingId lastTrustedReadingId
     * @param lastNumericValue lastNumericValue
     * @param lastTextValue lastTextValue
     * @param lastBooleanValue lastBooleanValue
     * @param lastQualityCodeId lastQualityCodeId
     * @param lastSourceTimestamp lastSourceTimestamp
     * @param lastReceivedAt lastReceivedAt
     * @param communicationState communicationState
     * @param staleSince staleSince
     * @param updatedAt updatedAt
     */
    public record TelemetryPointStateSnapshot(
            String pointId,
        String lastReadingId,
        String lastTrustedReadingId,
        BigDecimal lastNumericValue,
        String lastTextValue,
        Boolean lastBooleanValue,
        String lastQualityCodeId,
        Instant lastSourceTimestamp,
        Instant lastReceivedAt,
        CommunicationState communicationState,
        Instant staleSince,
        Instant updatedAt
    ) {

        public TelemetryPointStateSnapshot {
        pointId = normalize(pointId);
        lastReadingId = normalize(lastReadingId);
        lastTrustedReadingId = normalize(lastTrustedReadingId);
        lastTextValue = normalize(lastTextValue);
        lastQualityCodeId = normalize(lastQualityCodeId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
