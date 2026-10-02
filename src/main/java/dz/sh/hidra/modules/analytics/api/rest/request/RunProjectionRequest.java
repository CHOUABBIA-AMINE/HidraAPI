/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RunProjectionRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.request
 *
 * @Description : REST request for run projection.
 *
 */
package dz.sh.hidra.modules.analytics.api.rest.request;

import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunMode;
import java.time.Instant;

/**
 * REST request for run projection.
 */
public record RunProjectionRequest(
        String projectionDefinitionId,
        AnalyticsRunMode runMode,
        Instant periodStart,
        Instant periodEnd,
        String correlationId
) {
}
