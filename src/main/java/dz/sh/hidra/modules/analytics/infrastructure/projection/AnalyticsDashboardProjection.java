/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDashboardProjection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.projection
 *
 * @Description : Analytics dashboard projection.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.projection;

import dz.sh.hidra.modules.analytics.domain.value.AnalyticsInsightStatus;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Analytics dashboard projection.
 */
public record AnalyticsDashboardProjection(
        String insightId,
        String insightType,
        String subjectAreaId,
        String scopeType,
        String scopeId,
        String title,
        BigDecimal confidenceScore,
        AnalyticsInsightStatus status,
        Instant createdAt
) {
}
