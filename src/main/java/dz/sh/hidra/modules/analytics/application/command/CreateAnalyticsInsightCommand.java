/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateAnalyticsInsightCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.command
 *
 * @Description : Command to create an analytics insight.
 *
 */
package dz.sh.hidra.modules.analytics.application.command;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import java.math.BigDecimal;

public record CreateAnalyticsInsightCommand(
        String insightType,
        String subjectAreaId,
        String scopeType,
        String scopeId,
        String title,
        String summary,
        String severityId,
        BigDecimal confidenceScore,
        String sourceProjectionSnapshotId,
        String sourceTrendAnalysisId,
        String sourceModelRunId
) {

    public CreateAnalyticsInsightCommand {
        if (insightType == null || insightType.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsInsight insight type must not be blank.");
        }
        if (scopeType == null || scopeType.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsInsight scope type must not be blank.");
        }

        insightType = insightType.trim();
        subjectAreaId = normalize(subjectAreaId);
        scopeType = scopeType.trim();
        scopeId = normalize(scopeId);
        title = normalize(title);
        summary = normalize(summary);
        severityId = normalize(severityId);
        sourceProjectionSnapshotId = normalize(sourceProjectionSnapshotId);
        sourceTrendAnalysisId = normalize(sourceTrendAnalysisId);
        sourceModelRunId = normalize(sourceModelRunId);
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
