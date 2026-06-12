/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentLinkedToTargetEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.event
 *
 * @Description : Published when a document is linked to a business target.
 *
 */
package dz.sh.hidra.modules.documents.domain.event;

import java.time.Instant;

/**
 * Published when a document is linked to a business target.
 */
public record DocumentLinkedToTargetEvent(
        String eventId,
    String documentId,
    String targetModule,
    String targetId,
    Instant occurredAt
) implements DocumentsDomainEvent {

    @Override
    public String eventType() {
        return "DocumentLinkedToTargetEvent";
    }
}
