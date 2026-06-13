/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionRunResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.response
 *
 * @Description : REST response for analytics projection run.
 *
 */
package dz.sh.hidra.modules.analytics.api.rest.response;

import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunMode;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunStatus;
import java.time.Instant;

/**
 * REST response for analytics projection run.
 */
public record AnalyticsProjectionRunResponse(
        String id,
        String projectionDefinitionId,
        AnalyticsRunStatus runStatus,
        AnalyticsRunMode runMode,
        Instant periodStart,
        Instant periodEnd,
        String correlationId
) {
}
