/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateAnalyticsInsightCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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

import java.math.BigDecimal;

/**
 * Command to create an analytics insight.
 */
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
}
