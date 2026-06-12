/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditAccessRecordSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.dto
 *
 * @Description : Audit access record summary DTO.
 *
 */
package dz.sh.hidra.modules.audit.application.dto;

import dz.sh.hidra.modules.audit.domain.value.AuditAccessType;

import java.time.Instant;

/**
 * Audit access record summary DTO.
 */
public record AuditAccessRecordSummaryDto(
        String id,
        String actorId,
        AuditAccessType accessType,
        String auditEventId,
        String exportRequestId,
        Integer resultCount,
        Instant accessedAt
) {
}
