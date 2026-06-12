/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditAccessRecordedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.event
 *
 * @Description : Published when access to audit evidence is recorded.
 *
 */
package dz.sh.hidra.modules.audit.domain.event;

import java.time.Instant;

/**
 * Published when access to audit evidence is recorded.
 */
public record AuditAccessRecordedEvent(
        String eventId,
    String accessRecordId,
    Instant occurredAt
) implements AuditDomainEvent {

    @Override
    public String eventType() {
        return "AuditAccessRecordedEvent";
    }
}
