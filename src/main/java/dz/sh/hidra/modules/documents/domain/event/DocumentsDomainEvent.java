/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsDomainEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.event
 *
 * @Description : Documents domain event contract.
 *
 */
package dz.sh.hidra.modules.documents.domain.event;

import java.time.Instant;

/**
 * Documents domain event contract.
 */
public interface DocumentsDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
