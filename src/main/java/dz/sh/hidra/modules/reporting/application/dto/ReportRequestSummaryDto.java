/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportRequestSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.dto
 *
 * @Description : Report request summary DTO.
 *
 */
package dz.sh.hidra.modules.reporting.application.dto;

import dz.sh.hidra.modules.reporting.domain.value.ReportRequestStatus;

import java.time.Instant;

/**
 * Report request summary DTO.
 */
public record ReportRequestSummaryDto(
        String id,
        String reportDefinitionId,
        String requestedByActorId,
        String requestedByDisplayNameSnapshot,
        String organizationUnitId,
        Instant requestedAt,
        ReportRequestStatus status,
        String correlationId,
        String workflowReferenceId
) {
}
