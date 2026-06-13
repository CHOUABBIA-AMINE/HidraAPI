/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportRunResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.response
 *
 * @Description : REST response for report run.
 *
 */
package dz.sh.hidra.modules.reporting.api.rest.response;

import dz.sh.hidra.modules.reporting.domain.value.ReportRunMode;
import dz.sh.hidra.modules.reporting.domain.value.ReportRunStatus;
import java.time.Instant;

/**
 * REST response for report run.
 */
public record ReportRunResponse(
        String id,
        String reportRequestId,
        String reportDefinitionId,
        String templateVersionId,
        ReportRunStatus status,
        ReportRunMode runMode,
        Instant queuedAt,
        Instant completedAt,
        String correlationId
) {
}
