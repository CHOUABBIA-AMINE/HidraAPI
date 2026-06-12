/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditSearchResultProjection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.projection
 *
 * @Description : Audit search result projection.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.projection;

import java.time.Instant;

/**
 * Audit search result projection.
 */
public record AuditSearchResultProjection(
        String auditEventId,
        String sourceModule,
        String eventCategoryCode,
        String eventTypeCode,
        String actionCode,
        String actorId,
        String targetModule,
        String targetType,
        String targetId,
        String correlationId,
        Instant occurredAt
) {
}
