/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditEventSealedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.event
 *
 * @Description : Published when an audit event or batch is sealed.
 *
 */
package dz.sh.hidra.modules.audit.domain.event;

import java.time.Instant;

/**
 * Published when an audit event or batch is sealed.
 */
public record AuditEventSealedEvent(
        String eventId,
    String auditEventId,
    String sealId,
    Instant occurredAt
) implements AuditDomainEvent {

    @Override
    public String eventType() {
        return "AuditEventSealedEvent";
    }
}
