/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionRunSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.dto
 *
 * @Description : Analytics projection run summary DTO.
 *
 */
package dz.sh.hidra.modules.analytics.application.dto;

import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunMode;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunStatus;

import java.time.Instant;

/**
 * Analytics projection run summary DTO.
 */
public record AnalyticsProjectionRunSummaryDto(
        String id,
        String projectionDefinitionId,
        AnalyticsRunStatus runStatus,
        AnalyticsRunMode runMode,
        Instant periodStart,
        Instant periodEnd,
        String correlationId
) {
}
