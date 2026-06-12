/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportRunSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.dto
 *
 * @Description : Report run summary DTO.
 *
 */
package dz.sh.hidra.modules.reporting.application.dto;

import dz.sh.hidra.modules.reporting.domain.value.ReportRunMode;
import dz.sh.hidra.modules.reporting.domain.value.ReportRunStatus;

import java.time.Instant;

/**
 * Report run summary DTO.
 */
public record ReportRunSummaryDto(
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
