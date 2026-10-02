/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateAnalyticsInsightRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.request
 *
 * @Description : REST request to create analytics insight.
 *
 */
package dz.sh.hidra.modules.analytics.api.rest.request;

import java.math.BigDecimal;

/**
 * REST request to create analytics insight.
 */
public record CreateAnalyticsInsightRequest(
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
