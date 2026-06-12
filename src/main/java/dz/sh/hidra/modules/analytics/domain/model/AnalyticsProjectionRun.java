/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionRun
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Execution of an analytical projection definition.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.value.*;
import java.time.Instant;

    /**
     * Execution of an analytical projection definition.
     *
         * @param id id
     * @param projectionDefinitionId projectionDefinitionId
     * @param runStatus runStatus
     * @param runMode runMode
     * @param periodStart periodStart
     * @param periodEnd periodEnd
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param sourceWatermark sourceWatermark
     * @param recordsRead recordsRead
     * @param recordsWritten recordsWritten
     * @param errorCode errorCode
     * @param errorMessage errorMessage
     * @param correlationId correlationId
     * @param createdAt createdAt
     */
    public record AnalyticsProjectionRun(
            String id,
        String projectionDefinitionId,
        AnalyticsRunStatus runStatus,
        AnalyticsRunMode runMode,
        Instant periodStart,
        Instant periodEnd,
        Instant startedAt,
        Instant completedAt,
        String sourceWatermark,
        Long recordsRead,
        Long recordsWritten,
        String errorCode,
        String errorMessage,
        String correlationId,
        Instant createdAt
    ) {

        public AnalyticsProjectionRun {
        id = normalize(id);
        projectionDefinitionId = normalize(projectionDefinitionId);
        sourceWatermark = normalize(sourceWatermark);
        errorCode = normalize(errorCode);
        errorMessage = normalize(errorMessage);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
