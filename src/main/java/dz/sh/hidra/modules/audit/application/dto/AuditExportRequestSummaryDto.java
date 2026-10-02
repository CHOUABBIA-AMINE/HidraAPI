/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditExportRequestSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.dto
 *
 * @Description : Audit export request summary DTO.
 *
 */
package dz.sh.hidra.modules.audit.application.dto;

import dz.sh.hidra.modules.audit.domain.value.AuditExportStatus;

import java.time.Instant;

/**
 * Audit export request summary DTO.
 */
public record AuditExportRequestSummaryDto(
        String id,
        String requestedByActorId,
        String purposeId,
        String format,
        AuditExportStatus status,
        Integer recordCount,
        Instant requestedAt,
        Instant completedAt
) {
}
