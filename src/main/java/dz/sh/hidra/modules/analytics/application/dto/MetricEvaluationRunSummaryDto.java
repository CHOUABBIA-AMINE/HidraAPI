/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricEvaluationRunSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.dto
 *
 * @Description : Metric evaluation run summary DTO.
 *
 */
package dz.sh.hidra.modules.analytics.application.dto;

import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunStatus;

import java.time.Instant;

/**
 * Metric evaluation run summary DTO.
 */
public record MetricEvaluationRunSummaryDto(
        String id,
        String metricDefinitionVersionId,
        AnalyticsRunStatus runStatus,
        Instant periodStart,
        Instant periodEnd,
        String scopeType,
        String scopeId,
        String correlationId
) {
}
