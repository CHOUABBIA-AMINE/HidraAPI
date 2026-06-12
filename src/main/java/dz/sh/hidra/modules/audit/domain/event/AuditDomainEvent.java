/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditDomainEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.event
 *
 * @Description : Audit domain event contract.
 *
 */
package dz.sh.hidra.modules.audit.domain.event;

import java.time.Instant;

/**
 * Audit domain event contract.
 */
public interface AuditDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
