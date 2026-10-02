/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditEventResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.api.rest.response
 *
 * @Description : REST response for audit event.
 *
 */
package dz.sh.hidra.modules.audit.api.rest.response;

import dz.sh.hidra.modules.audit.domain.value.AuditActorType;
import dz.sh.hidra.modules.audit.domain.value.AuditEventStatus;
import dz.sh.hidra.modules.audit.domain.value.AuditOperation;

import java.time.Instant;

/**
 * REST response for audit event.
 */
public record AuditEventResponse(
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
