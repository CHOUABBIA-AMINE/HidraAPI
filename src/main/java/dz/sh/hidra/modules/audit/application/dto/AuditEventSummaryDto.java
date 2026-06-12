/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditEventSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.dto
 *
 * @Description : Audit event summary DTO.
 *
 */
package dz.sh.hidra.modules.audit.application.dto;

import dz.sh.hidra.modules.audit.domain.value.AuditActorType;
import dz.sh.hidra.modules.audit.domain.value.AuditEventStatus;
import dz.sh.hidra.modules.audit.domain.value.AuditOperation;

import java.time.Instant;

/**
 * Audit event summary DTO.
 */
public record AuditEventSummaryDto(
        String id,
        String sourceModule,
        String actionCode,
        AuditEventStatus eventStatus,
        String actorId,
        AuditActorType actorType,
        String targetModule,
        String targetType,
        String targetId,
        AuditOperation operation,
        String correlationId,
        Instant occurredAt,
        Instant recordedAt
) {
}
