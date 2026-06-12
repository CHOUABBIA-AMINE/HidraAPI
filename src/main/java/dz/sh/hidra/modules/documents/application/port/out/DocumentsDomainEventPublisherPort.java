/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsDomainEventPublisherPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.out
 *
 * @Description : Publisher port for documents domain events.
 *
 */
package dz.sh.hidra.modules.documents.application.port.out;

import dz.sh.hidra.modules.documents.domain.event.DocumentsDomainEvent;

/**
 * Publisher port for documents domain events.
 */
public interface DocumentsDomainEventPublisherPort {

    void publish(DocumentsDomainEvent event);
}
