/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentRegisteredEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.event
 *
 * @Description : Published when a document is registered.
 *
 */
package dz.sh.hidra.modules.documents.domain.event;

import java.time.Instant;

/**
 * Published when a document is registered.
 */
public record DocumentRegisteredEvent(
        String eventId,
    String documentId,
    Instant occurredAt
) implements DocumentsDomainEvent {

    @Override
    public String eventType() {
        return "DocumentRegisteredEvent";
    }
}
