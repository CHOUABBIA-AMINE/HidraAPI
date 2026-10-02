/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricEvaluationRunResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.response
 *
 * @Description : REST response for metric evaluation run.
 *
 */
package dz.sh.hidra.modules.analytics.api.rest.response;

import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunStatus;
import java.time.Instant;

/**
 * REST response for metric evaluation run.
 */
public record MetricEvaluationRunResponse(
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
