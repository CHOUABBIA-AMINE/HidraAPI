/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionRunSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.dto
 *
 * @Description : Analytics projection run summary DTO with immutable computation-version lineage.
 *
 */
package dz.sh.hidra.modules.analytics.application.dto;

import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunMode;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunStatus;
import java.time.Instant;

public record AnalyticsProjectionRunSummaryDto(
        String id,
        String projectionDefinitionId,
        String projectionDefinitionVersion,
        AnalyticsRunStatus runStatus,
        AnalyticsRunMode runMode,
        Instant periodStart,
        Instant periodEnd,
        String correlationId
) {
    public AnalyticsProjectionRunSummaryDto(
            String id,
            String projectionDefinitionId,
            AnalyticsRunStatus runStatus,
            AnalyticsRunMode runMode,
            Instant periodStart,
            Instant periodEnd,
            String correlationId
    ) {
        this(
                id,
                projectionDefinitionId,
                null,
                runStatus,
                runMode,
                periodStart,
                periodEnd,
                correlationId
        );
    }
}
