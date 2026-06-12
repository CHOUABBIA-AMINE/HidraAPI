/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsInsightResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.response
 *
 * @Description : REST response for analytics insight.
 *
 */
package dz.sh.hidra.modules.analytics.api.rest.response;

import dz.sh.hidra.modules.analytics.domain.value.AnalyticsInsightStatus;

import java.math.BigDecimal;

/**
 * REST response for analytics insight.
 */
public record AnalyticsInsightResponse(
        String id,
        String insightType,
        String subjectAreaId,
        String scopeType,
        String scopeId,
        String title,
        String severityId,
        BigDecimal confidenceScore,
        AnalyticsInsightStatus status
) {
}
