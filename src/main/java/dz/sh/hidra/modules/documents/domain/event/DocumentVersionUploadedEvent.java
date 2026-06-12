/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentVersionUploadedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.event
 *
 * @Description : Published when a document version is uploaded.
 *
 */
package dz.sh.hidra.modules.documents.domain.event;

import java.time.Instant;

/**
 * Published when a document version is uploaded.
 */
public record DocumentVersionUploadedEvent(
        String eventId,
    String documentId,
    String documentVersionId,
    Instant occurredAt
) implements DocumentsDomainEvent {

    @Override
    public String eventType() {
        return "DocumentVersionUploadedEvent";
    }
}
