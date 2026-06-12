/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsModelRun
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Execution of an analytical model.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.value.*;
import java.time.Instant;

    /**
     * Execution of an analytical model.
     *
         * @param id id
     * @param analyticsModelVersionId analyticsModelVersionId
     * @param runType runType
     * @param runStatus runStatus
     * @param inputDatasetVersionId inputDatasetVersionId
     * @param scopeType scopeType
     * @param scopeId scopeId
     * @param periodStart periodStart
     * @param periodEnd periodEnd
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param outputDatasetVersionId outputDatasetVersionId
     * @param errorCode errorCode
     * @param errorMessage errorMessage
     * @param correlationId correlationId
     * @param createdAt createdAt
     */
    public record AnalyticsModelRun(
            String id,
        String analyticsModelVersionId,
        AnalyticsModelRunType runType,
        AnalyticsRunStatus runStatus,
        String inputDatasetVersionId,
        String scopeType,
        String scopeId,
        Instant periodStart,
        Instant periodEnd,
        Instant startedAt,
        Instant completedAt,
        String outputDatasetVersionId,
        String errorCode,
        String errorMessage,
        String correlationId,
        Instant createdAt
    ) {

        public AnalyticsModelRun {
        id = normalize(id);
        analyticsModelVersionId = normalize(analyticsModelVersionId);
        inputDatasetVersionId = normalize(inputDatasetVersionId);
        scopeType = normalize(scopeType);
        scopeId = normalize(scopeId);
        outputDatasetVersionId = normalize(outputDatasetVersionId);
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
