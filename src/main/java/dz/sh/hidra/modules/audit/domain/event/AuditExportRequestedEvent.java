/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditExportRequestedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.event
 *
 * @Description : Published when an audit export is requested.
 *
 */
package dz.sh.hidra.modules.audit.domain.event;

import java.time.Instant;

/**
 * Published when an audit export is requested.
 */
public record AuditExportRequestedEvent(
        String eventId,
    String exportRequestId,
    Instant occurredAt
) implements AuditDomainEvent {

    @Override
    public String eventType() {
        return "AuditExportRequestedEvent";
    }
}
