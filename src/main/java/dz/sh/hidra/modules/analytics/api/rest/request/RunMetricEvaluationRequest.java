/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RunMetricEvaluationRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.request
 *
 * @Description : REST request for run metric evaluation.
 *
 */
package dz.sh.hidra.modules.analytics.api.rest.request;

import java.time.Instant;

/**
 * REST request for run metric evaluation.
 */
public record RunMetricEvaluationRequest(
        String metricDefinitionVersionId,
        Instant periodStart,
        Instant periodEnd,
        String scopeType,
        String scopeId,
        String correlationId
) {
}
