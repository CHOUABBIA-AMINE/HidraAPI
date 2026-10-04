/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FinalizeMetricEvaluationRunCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.command
 *
 * @Description : Command for governed terminal finalization of a metric evaluation run.
 *
 */
package dz.sh.hidra.modules.analytics.application.command;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunStatus;

/**
 * Finalizes a metric evaluation run with terminal outcome evidence.
 */
public record FinalizeMetricEvaluationRunCommand(
        String runId,
        AnalyticsRunStatus terminalStatus,
        Long recordsRead,
        Long recordsProduced,
        String errorCode,
        String errorMessage,
        String correlationId
) {

    public FinalizeMetricEvaluationRunCommand {
        if (runId == null || runId.isBlank()) {
            throw new InvalidAnalyticsValueException(
                    "MetricEvaluationRun finalization run id must not be blank."
            );
        }
        if (!terminal(terminalStatus)) {
            throw new InvalidAnalyticsValueException(
                    "MetricEvaluationRun finalization requires a terminal status."
            );
        }
        runId = runId.trim();
        errorCode = normalize(errorCode);
        errorMessage = normalize(errorMessage);
        correlationId = normalize(correlationId);
    }

    private static boolean terminal(AnalyticsRunStatus status) {
        return status == AnalyticsRunStatus.COMPLETED
                || status == AnalyticsRunStatus.COMPLETED_WITH_WARNINGS
                || status == AnalyticsRunStatus.FAILED
                || status == AnalyticsRunStatus.CANCELLED;
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
