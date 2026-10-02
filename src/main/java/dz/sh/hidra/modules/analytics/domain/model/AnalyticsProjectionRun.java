/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionRun
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsProjectionRun id must not be blank.");
        }
        // HRA-051 required: projectionDefinitionId
        if (projectionDefinitionId == null || projectionDefinitionId.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsProjectionRun projection definition id must not be blank.");
        }
        // HRA-051 required: runStatus
        if (runStatus == null) {
            throw new InvalidAnalyticsValueException("AnalyticsProjectionRun run status must not be null.");
        }
        // HRA-051 required: runMode
        if (runMode == null) {
            throw new InvalidAnalyticsValueException("AnalyticsProjectionRun run mode must not be null.");
        }
        // HRA-051 required: startedAt
        if (startedAt == null) {
            throw new InvalidAnalyticsValueException("AnalyticsProjectionRun started at must not be null.");
        }
        // HRA-051 order: periodStart <= periodEnd
        if (periodStart != null && periodEnd != null && periodEnd.isBefore(periodStart)) {
            throw new InvalidAnalyticsValueException("AnalyticsProjectionRun period end must not be before period start.");
        }

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
