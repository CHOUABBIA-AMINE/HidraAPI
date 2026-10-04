/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionRun
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Execution of an analytical projection definition with durable computation-version lineage.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunMode;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunStatus;
import java.time.Instant;

public record AnalyticsProjectionRun(
        String id,
        String projectionDefinitionId,
        String projectionDefinitionVersion,
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
            throw new InvalidAnalyticsValueException(
                    "AnalyticsProjectionRun projection definition id must not be blank."
            );
        }
        // HRA-051 required: runStatus
        if (runStatus == null) {
            throw new InvalidAnalyticsValueException(
                    "AnalyticsProjectionRun run status must not be null."
            );
        }
        // HRA-051 required: runMode
        if (runMode == null) {
            throw new InvalidAnalyticsValueException(
                    "AnalyticsProjectionRun run mode must not be null."
            );
        }
        // HRA-051 required: startedAt
        if (startedAt == null) {
            throw new InvalidAnalyticsValueException(
                    "AnalyticsProjectionRun started at must not be null."
            );
        }
        // HRA-051 order: periodStart <= periodEnd
        if (periodStart != null && periodEnd != null && periodEnd.isBefore(periodStart)) {
            throw new InvalidAnalyticsValueException(
                    "AnalyticsProjectionRun period end must not be before period start."
            );
        }

        id = normalize(id);
        projectionDefinitionId = normalize(projectionDefinitionId);
        projectionDefinitionVersion = normalize(projectionDefinitionVersion);
        sourceWatermark = normalize(sourceWatermark);
        errorCode = normalize(errorCode);
        errorMessage = normalize(errorMessage);
        correlationId = normalize(correlationId);

        if (runStatus == AnalyticsRunStatus.FAILED
                && errorCode == null
                && errorMessage == null) {
            throw new InvalidAnalyticsValueException(
                    "Failed AnalyticsProjectionRun must retain error code or error message."
            );
        }

        if (successfulStatus(runStatus) && sourceWatermark == null) {
            throw new InvalidAnalyticsValueException(
                    "Successful AnalyticsProjectionRun must retain a source watermark."
            );
        }
    }

    public AnalyticsProjectionRun(
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
        this(
                id,
                projectionDefinitionId,
                null,
                runStatus,
                runMode,
                periodStart,
                periodEnd,
                startedAt,
                completedAt,
                sourceWatermark,
                recordsRead,
                recordsWritten,
                errorCode,
                errorMessage,
                correlationId,
                createdAt
        );
    }

    public AnalyticsProjectionRun withProjectionDefinitionVersion(String value) {
        return new AnalyticsProjectionRun(
                id,
                projectionDefinitionId,
                value,
                runStatus,
                runMode,
                periodStart,
                periodEnd,
                startedAt,
                completedAt,
                sourceWatermark,
                recordsRead,
                recordsWritten,
                errorCode,
                errorMessage,
                correlationId,
                createdAt
        );
    }

    public boolean successfulStatus() {
        return successfulStatus(runStatus);
    }

    private static boolean successfulStatus(AnalyticsRunStatus status) {
        return status == AnalyticsRunStatus.COMPLETED
                || status == AnalyticsRunStatus.COMPLETED_WITH_WARNINGS;
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
